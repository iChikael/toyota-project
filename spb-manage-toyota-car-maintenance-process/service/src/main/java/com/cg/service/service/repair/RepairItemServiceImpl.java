package com.cg.service.service.repair;

import com.cg.domain.dto.service.repairItem.RepairItemCreateReqDTO;
import com.cg.domain.dto.service.repairItem.RepairItemResDTO;
import com.cg.domain.dto.service.repairItem.RepairItemUpdateReqDTO;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.repository.service.repair.IRepairItemRepository;
import com.cg.service.service.IServiceAreaService;
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
public class RepairItemServiceImpl implements IRepairItemService {

    @Autowired
    private IRepairItemRepository repairItemRepository;

    @Autowired
    private IServiceAreaService serviceAreaService;

    @Override
    public List<RepairItem> findAll() {
        return repairItemRepository.findAll();
    }

    @Override
    public Optional<RepairItem> findById(Long id) {
        return repairItemRepository.findById(id);
    }

    @Override
    public RepairItem save(RepairItem repairItem) {
        return repairItemRepository.save(repairItem);
    }

    @Override
    public void delete(RepairItem repairItem) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public RepairItem createRepairItem(RepairItemCreateReqDTO repairItemCreateReqDTO, ServiceArea serviceArea) {

        RepairItem repairItem = repairItemCreateReqDTO.toRepairItem(serviceArea);
        repairItemRepository.save(repairItem);

        return repairItem;
    }

    @Override
    public RepairItem updateRepairItem(RepairItem repairItem, RepairItemUpdateReqDTO repairItemUpdateReqDTO, ServiceArea serviceArea) {

        repairItem.setDeleted(true);
        repairItemRepository.save(repairItem);

        RepairItem repairItemUpdate = repairItemUpdateReqDTO.toRepairItem(serviceArea);
        repairItemUpdate.setId(null);
        repairItemUpdate.setTitle(repairItemUpdateReqDTO.getTitle());
        repairItemUpdate.setCode(repairItem.getCode());
        repairItemRepository.save(repairItemUpdate);

        return repairItemUpdate;
    }


    @Override
    public void importToDb(MultipartFile multipartfile) {

        if (!multipartfile.isEmpty()) {
            List<RepairItem> repairItems = new ArrayList<>();

            try {
                XSSFWorkbook workBook = new XSSFWorkbook(multipartfile.getInputStream());

                XSSFSheet sheet = workBook.getSheetAt(0);
                for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet, 0); rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);
                    if (rowIndex == 0) {
                        continue;
                    }

                    String title = String.valueOf(getValue(row.getCell(0)));
                    BigDecimal priceWage = BigDecimal.valueOf(Long.parseLong(String.valueOf(getValue(row.getCell(1)))));
                    String unitWage = String.valueOf(getValue(row.getCell(2)));
                    BigDecimal amount = BigDecimal.valueOf(Long.parseLong(String.valueOf(getValue(row.getCell(3)))));
                    Long serviceArea = Long.valueOf(String.valueOf(getValue(row.getCell(4))));

                    Optional<ServiceArea> serviceAreaOptional = serviceAreaService.findById(serviceArea);

                    RepairItem repairItem = RepairItem.builder()
                            .title(title)
                            .serviceArea(serviceAreaOptional.get())
                            .build();

                    repairItems.add(repairItem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!repairItems.isEmpty()) {
                repairItemRepository.saveAll(repairItems);
            }
        }
    }

    @Override
    public StreamingResponseBody exportToExcel(HttpServletResponse response) {

        List<RepairItem> repairItems = repairItemRepository.findAll();

        if (repairItems.isEmpty()) {
            throw new NullPointerException("No data found in database");
        }

        return outputStream -> {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE)) {

                String sheetName = "Customer";
                Sheet sheet = workbook.createSheet(sheetName);

                Font headerFont = workbook.createFont();
                headerFont.setColor(IndexedColors.BLACK.getIndex());

                CellStyle headerColumnStyle = workbook.createCellStyle();
                headerColumnStyle.setFont(headerFont);

                Row headerRow = sheet.createRow(0);

                String[] columns = new String[]{"Id", "Title", "Price Wage", "Unit Wage", "Amount", "Service Area"};

                for (int i = 0; i < columns.length; i++) {
                    Cell headerColumn = headerRow.createCell(i);
                    headerColumn.setCellValue(columns[i]);
                    headerColumn.setCellStyle(headerColumnStyle);
                }

                CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy h:mm;@"));

                int rowIndex = 1;
                for (RepairItem repairItem : repairItems) {

                    Row dataRow = sheet.createRow(rowIndex);

                    Cell columnId = dataRow.createCell(0);
                    columnId.setCellValue(String.valueOf(repairItem.getId() != null ? repairItem.getId() : -1));

                    Cell columnName = dataRow.createCell(1);
                    columnName.setCellValue(repairItem.getTitle() != null ? repairItem.getTitle() : "");

                    Cell columnArea = dataRow.createCell(5);
                    columnArea.setCellValue(repairItem.getServiceArea().getName() != null ? repairItem.getServiceArea().getName() : "");

                    rowIndex++;
                }

                workbook.write(out);
                workbook.dispose();

                String filename = "Export-repair-item-data" + ".xlsx";
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                response.setContentLength(out.size());

                InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
                int BUFFER_SIZE = 1024;
                int bytesRead;
                byte[] buffer = new byte[BUFFER_SIZE];

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            }
        };
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

    public Optional<RepairItem> findByIdAndDeletedIsFalse(Long id) {
        return repairItemRepository.findByIdAndDeletedIsFalse(id);
    }

    @Override
    public List<RepairItemResDTO> findAllRepairItemsDTO() {
        return repairItemRepository.findAllRepairItemResDTO();
    }
}
