package com.cg.service.storage;

import com.cg.domain.dto.accessory.AccessoryCreateReqDTO;
import com.cg.domain.dto.accessory.AccessoryReceptionResDTO;
import com.cg.domain.dto.accessory.AccessoryResDTO;
import com.cg.domain.dto.accessory.AccessoryUpdateReqDTO;
import com.cg.domain.entity.storage.Accessory;
import com.cg.domain.entity.storage.AccessoryRole;
import com.cg.domain.enums.EAccessoryType;
import com.cg.domain.enums.EUnit;
import com.cg.repository.storage.IAccessoryRepository;
import com.cg.repository.storage.IAccessoryRoleRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AccessoryServiceImpl implements IAccessoryService {

    @Autowired
    private IAccessoryRepository accessoryRepository;

    @Autowired
    private IAccessoryRoleRepository accessoryRoleRepository;

    @Override
    public List<Accessory> findAll() {
        return null;
    }

    @Override
    public Optional<Accessory> findById(Long id) {
        return accessoryRepository.findById(id);
    }

    @Override
    public Accessory save(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    @Override
    public void delete(Accessory accessory) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<AccessoryResDTO> getAllAccessoryResDTO() {
        return accessoryRepository.findAllAccessories();
    }

    @Override
    public List<AccessoryReceptionResDTO> findAllAccessoryMaintenanceItems() {
        return accessoryRepository.findAllAccessoryMaintenanceItems();
    }

    @Override
    public Optional<Accessory> findByIdAndDeletedIsFalse(Long id) {
        return accessoryRepository.findByIdAndDeletedIsFalse(id);
    }

    @Override
    public Accessory createAccessory(AccessoryCreateReqDTO accessoryCreateReqDTO, AccessoryRole accessoryRole) {
        Accessory accessory = accessoryCreateReqDTO.toAccessory(accessoryRole);
        accessoryRepository.save(accessory);
        return accessory;
    }

    @Override
    public Accessory updateAccessory(Accessory accessory, AccessoryRole accessoryRole, AccessoryUpdateReqDTO accessoryUpdateReqDTO) {

        Accessory updateAccessory = accessoryUpdateReqDTO.toAccessory(accessoryRole);

        if (accessoryUpdateReqDTO.getPrice() != null && !accessoryUpdateReqDTO.getPrice().equals(accessory.getPrice())
                || accessoryUpdateReqDTO.getName() != null && !accessoryUpdateReqDTO.getName().equals(accessory.getName())) {

            Accessory oldAccessoryRecord = new Accessory();
            oldAccessoryRecord.setId(accessory.getId());
            oldAccessoryRecord.setCode(accessory.getCode());
            oldAccessoryRecord.setName(accessory.getName());
            oldAccessoryRecord.setPrice(accessory.getPrice());
            oldAccessoryRecord.setUnit(accessory.getUnit());
            oldAccessoryRecord.setAccessoryRole(accessoryRole);
            oldAccessoryRecord.setQuantity(accessory.getQuantity());
            oldAccessoryRecord.setDeleted(true);

            accessoryRepository.save(oldAccessoryRecord);

            updateAccessory.setId(null);
            updateAccessory.setDeleted(false);
            accessoryRepository.save(updateAccessory);

            return updateAccessory;
        }

        accessoryRepository.save(updateAccessory);

        return updateAccessory;
    }


    @Override
    public void importToDb(MultipartFile multipartfile) {
        if (!multipartfile.isEmpty()) {
            List<Accessory> accessories = new ArrayList<>();

            try {
                XSSFWorkbook workBook = new XSSFWorkbook(multipartfile.getInputStream());
                XSSFSheet sheet = workBook.getSheetAt(0);

                for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet, 0); rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);

                    if (rowIndex == 0) {
                        continue;
                    }

                    String code = String.valueOf(getValue(row.getCell(0)));
                    String name = String.valueOf(getValue(row.getCell(2)));
                    BigDecimal quantity = BigDecimal.valueOf(Long.parseLong(String.valueOf(getValue(row.getCell(3)))));
                    String unit = (String.valueOf(getValue(row.getCell(4))));
                    BigDecimal price = BigDecimal.valueOf(Long.parseLong(String.valueOf(getValue(row.getCell(5)))));
                    String accessoryRoleName = String.valueOf(getValue(row.getCell(7)));

                    Accessory existingAccessories = accessoryRepository.getByCodeAndDeletedIsFalse(code);

                    if (existingAccessories !=null) {

                        BigDecimal existingQuantity = existingAccessories.getQuantity();
                        existingAccessories.setQuantity(quantity.add(existingQuantity));
                        accessories.add(existingAccessories);

                    } else {
                        Optional<AccessoryRole> accessoryRoleOptional = Optional.ofNullable(accessoryRoleRepository.getByName(EAccessoryType.valueOf(accessoryRoleName)));
                        Accessory accessory = Accessory.builder()
                                .code(code)
                                .name(name)
                                .price(price)
                                .quantity(quantity)
                                .unit(EUnit.getEUnitByName(unit))
                                .accessoryRole(accessoryRoleOptional.get())
                                .build();
                        accessories.add(accessory);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!accessories.isEmpty()) {
                accessoryRepository.saveAll(accessories);
            }
        }
    }


    @Override
    public StreamingResponseBody exportToExcel(HttpServletResponse response) {

        List<Accessory> accessories = accessoryRepository.findAll();

        if (accessories.isEmpty()) {
            throw new NullPointerException("No data found in database");
        }

        return outputStream -> {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE)) {
                // Creating excel sheet
                String sheetName = "Accessories";
                Sheet sheet = workbook.createSheet(sheetName);

                // Creating font style for excel sheet
                Font headerFont = workbook.createFont();
                headerFont.setColor(IndexedColors.BLACK.getIndex());

                CellStyle headerColumnStyle = workbook.createCellStyle();
                headerColumnStyle.setFont(headerFont);

                // Row for header at 0 index
                Row headerRow = sheet.createRow(0);

                // Name of the columns to be added in the sheet
                String[] columns = new String[]{"Id", "Code","Name", "Price", "Quantity", "Unit", "Role"};

                // Creating header column at the first row
                for (int i = 0; i < columns.length; i++) {
                    Cell headerColumn = headerRow.createCell(i);
                    headerColumn.setCellValue(columns[i]);
                    headerColumnStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                    headerColumnStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    headerColumn.setCellStyle(headerColumnStyle);
                }

                // Date formatting
                CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy h:mm;@"));

                // Adding data to sheet from the second row
                int rowIndex = 1;
                for (Accessory accessory : accessories) {
                    // Creating row for writing data
                    Row dataRow = sheet.createRow(rowIndex);

                    Cell columnId = dataRow.createCell(0);
                    columnId.setCellValue(accessory.getId() != null ? accessory.getId() : -1);

                    Cell columnCode = dataRow.createCell(1);
                    columnCode.setCellValue(accessory.getCode() != null ? accessory.getCode() : "");

                    Cell columnFullName = dataRow.createCell(2);
                    columnFullName.setCellValue(accessory.getName() != null ? accessory.getName() : "");

                    Cell columnPrice = dataRow.createCell(3);
                    columnPrice.setCellValue(String.valueOf(accessory.getPrice() != null ? accessory.getPrice() : ""));

                    Cell columnQuantity = dataRow.createCell(4);
                    columnQuantity.setCellValue(String.valueOf(accessory.getQuantity() != null ? accessory.getQuantity() : ""));

                    Cell columnUnit = dataRow.createCell(5);
                    columnUnit.setCellValue((accessory.getUnit().getValue() != null) ? accessory.getUnit().getValue() : "");

                    Cell columnRole = dataRow.createCell(6);
                    columnRole.setCellValue(String.valueOf((accessory.getAccessoryRole().getName() != null ? accessory.getAccessoryRole().getName() : "")));

                    rowIndex++;
                }

                workbook.write(out);
                workbook.dispose();

                String filename = "Export-accessory-data" + ".xlsx";
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                response.setContentLength(out.size());

                InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
                int BUFFER_SIZE = 1024;
                int bytesRead;
                byte[] buffer = new byte[BUFFER_SIZE];

                // Writing to output stream
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            }
        };
    }

    @Override
    public Optional<Accessory> getByCode(String code) {
        return accessoryRepository.getByCode(code);
    }

    @Override
    public Boolean existsByCode(String code) {
        return accessoryRepository.existsAccessoriesByCode(code);
    }


    private Object getValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case ERROR:
                return cell.getErrorCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return null;
            case _NONE:
                return null;
            default:
                break;
        }
        return null;
    }

    public static int getNumberOfNonEmptyCells(XSSFSheet sheet, int columnIndex) {
        int numOfNonEmptyCells = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                XSSFCell cell = row.getCell(columnIndex);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    numOfNonEmptyCells++;
                }
            }
        }
        return numOfNonEmptyCells;
    }


}
