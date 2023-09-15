package com.cg.service.service.maintenance;

import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemCreateReqDTO;
import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemResDTO;
import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemUpdateReqDTO;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.repository.service.maintenance.IMaintenanceItemRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MaintenanceItemServiceImpl implements IMaintenanceItemService {

    @Autowired
    private IMaintenanceItemRepository maintenanceItemRepository;

    @Autowired
    private IServiceAreaService serviceAreaService;


    @Override
    public List<MaintenanceItem> findAll() {
        return maintenanceItemRepository.findAll();
    }

    @Override
    public Optional<MaintenanceItem> findById(Long id) {
        return maintenanceItemRepository.findById(id);
    }

    @Override
    public Optional<MaintenanceItem> findByIdAndDeletedIsFalse(Long id) {
        return maintenanceItemRepository.findByIdAndDeletedIsFalse(id);
    }

    @Override
    public List<MaintenanceItemResDTO> findAllMaintenanceItemResDTO() {
        return maintenanceItemRepository.findAllMaintenanceItemResDTO();
    }

    @Override
    public MaintenanceItem create(MaintenanceItemCreateReqDTO maintenanceItemCreateReqDTO, ServiceArea serviceArea) {

        MaintenanceItem maintenanceItem = new MaintenanceItem();
        maintenanceItem.setId(null);
        maintenanceItem.setTitle(maintenanceItemCreateReqDTO.getTitle());
        maintenanceItem.setServiceArea(serviceArea);

        maintenanceItemRepository.save(maintenanceItem);

        return maintenanceItem;
    }

    @Override
    public MaintenanceItem update(MaintenanceItem maintenanceItem, MaintenanceItemUpdateReqDTO maintenanceItemUpdateReqDTO, ServiceArea serviceArea) {

        maintenanceItem.setDeleted(true);
        maintenanceItemRepository.save(maintenanceItem);

        MaintenanceItem maintenanceItemUpdate = new MaintenanceItem();
        maintenanceItem.setId(maintenanceItem.getId());
        maintenanceItem.setTitle(maintenanceItemUpdateReqDTO.getTitle());
        maintenanceItem.setServiceArea(serviceArea);

        maintenanceItemRepository.save(maintenanceItemUpdate);

        return maintenanceItemUpdate;
    }

    @Override
    public MaintenanceItem save(MaintenanceItem maintenanceItem) {
        return maintenanceItemRepository.save(maintenanceItem);
    }

    @Override
    public void delete(MaintenanceItem maintenanceItem) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void importToDb(MultipartFile multipartfile) {

        if (!multipartfile.isEmpty()) {
            List<MaintenanceItem> maintenanceItems = new ArrayList<>();

            try {
                XSSFWorkbook workBook = new XSSFWorkbook(multipartfile.getInputStream());

                XSSFSheet sheet = workBook.getSheetAt(0);

                for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet, 0); rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);

                    if (rowIndex == 0) {
                        continue;
                    }

                    String title = String.valueOf(getValue(row.getCell(0)));
                    Long serviceArea = Long.valueOf(String.valueOf(getValue(row.getCell(1))));

                    Optional<ServiceArea> serviceAreaOptional = serviceAreaService.findById(serviceArea);

                    MaintenanceItem maintenanceItem = MaintenanceItem.builder()
                            .title(title)
                            .serviceArea(serviceAreaOptional.get())
                            .build();

                    maintenanceItems.add(maintenanceItem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!maintenanceItems.isEmpty()) {
                maintenanceItemRepository.saveAll(maintenanceItems);
            }
        }
    }

    @Override
    public StreamingResponseBody exportToExcel(HttpServletResponse response) {

        List<MaintenanceItem> maintenanceItems = maintenanceItemRepository.findAll();

        if (maintenanceItems.isEmpty()) {
            throw new NullPointerException("No data found in database");
        }

        return outputStream -> {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE)) {

                String sheetName = "Maintenance Item";
                Sheet sheet = workbook.createSheet(sheetName);

                Font headerFont = workbook.createFont();
                headerFont.setColor(IndexedColors.BLACK.getIndex());

                CellStyle headerColumnStyle = workbook.createCellStyle();
                headerColumnStyle.setFont(headerFont);

                Row headerRow = sheet.createRow(0);

                String[] columns = new String[]{"Id", "Title","Service Area"};

                for (int i = 0; i < columns.length; i++) {
                    Cell headerColumn = headerRow.createCell(i);
                    headerColumn.setCellValue(columns[i]);
                    headerColumn.setCellStyle(headerColumnStyle);
                }

                CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy h:mm;@"));

                int rowIndex = 1;

                for (MaintenanceItem maintenanceItem : maintenanceItems) {

                    Row dataRow = sheet.createRow(rowIndex);

                    Cell columnId = dataRow.createCell(0);
                    columnId.setCellValue(String.valueOf(maintenanceItem.getId() != null ? maintenanceItem.getId() : -1));

                    Cell columnName = dataRow.createCell(1);
                    columnName.setCellValue(maintenanceItem.getTitle() != null ? maintenanceItem.getTitle() : "");

                    Cell columnArea = dataRow.createCell(2);
                    columnArea.setCellValue(maintenanceItem.getServiceArea().getName() != null ? maintenanceItem.getServiceArea().getName() : "");

                    rowIndex++;
                }

                workbook.write(out);
                workbook.dispose();

                String filename = "Export-Maintenance-Item-data" + ".xlsx";
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

}
