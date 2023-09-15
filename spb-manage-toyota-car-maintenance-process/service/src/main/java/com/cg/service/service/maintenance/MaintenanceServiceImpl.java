package com.cg.service.service.maintenance;

import com.cg.domain.dto.service.maintaince.MaintenanceResDTO;
import com.cg.domain.dto.service.maintaince.MaintenanceUpdateReqDTO;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.repository.service.maintenance.IMaintenanceRepository;
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
public class MaintenanceServiceImpl implements IMaintenanceService {

    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }


    @Override
    public List<MaintenanceResDTO> findAllMaintenanceResDTO() {
        return maintenanceRepository.findAllMaintenanceResDTO();
    }

    @Override
    public Optional<Maintenance> findByIdAndDeletedIsFalse(Long id) {
        return maintenanceRepository.findByIdAndDeletedIsFalse(id);
    }

    @Override
    public Maintenance update(Maintenance maintenance, MaintenanceUpdateReqDTO maintenanceUpdateReqDTO) {
        maintenance.setDeleted(true);
        maintenanceRepository.save(maintenance);
        Maintenance maintenanceUpdate = maintenanceUpdateReqDTO.toMaintenance();
        maintenanceUpdate.setId(null);
        maintenanceRepository.save(maintenanceUpdate);
        return maintenanceUpdate;
    }

    @Override
    public Optional<Maintenance> findById(Long id) {
        return maintenanceRepository.findById(id);
    }

    @Override
    public Maintenance save(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public void delete(Maintenance maintenance) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void importToDb(MultipartFile multipartfile) {

        if (!multipartfile.isEmpty()) {
            List<Maintenance> maintenances = new ArrayList<>();

            try {
                XSSFWorkbook workBook = new XSSFWorkbook(multipartfile.getInputStream());

                XSSFSheet sheet = workBook.getSheetAt(0);
                for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet, 0); rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);
                    if (rowIndex == 0) {
                        continue;
                    }

                    String title = String.valueOf(getValue(row.getCell(0)));
                    String unitWage = String.valueOf(getValue(row.getCell(1)));
                    BigDecimal priceWage = (BigDecimal) getValue(row.getCell(2));

                    Maintenance maintenance = Maintenance.builder()
                            .title(title)
                            .unitWage(unitWage)
                            .priceWage(priceWage)
                            .build();

                    maintenances.add(maintenance);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!maintenances.isEmpty()) {
                maintenanceRepository.saveAll(maintenances);
            }
        }
    }

    @Override
    public StreamingResponseBody exportToExcel(HttpServletResponse response) {

        List<Maintenance> maintenances = maintenanceRepository.findAll();

        if (maintenances.isEmpty()) {
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
                String[] columns = new String[]{"Id", "Title", "Wage","Price"};

                // Creating header column at the first row
                for (int i = 0; i < columns.length; i++) {
                    Cell headerColumn = headerRow.createCell(i);
                    headerColumn.setCellValue(columns[i]);
                    headerColumn.setCellStyle(headerColumnStyle);
                }

                // Date formatting
                CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy h:mm;@"));

                // Adding data to sheet from the second row
                int rowIndex = 1;
                for (Maintenance maintenance : maintenances) {
                    // Creating row for writing data
                    Row dataRow = sheet.createRow(rowIndex);

//                    private String phone;

                    Cell columnId = dataRow.createCell(0);
                    columnId.setCellValue(String.valueOf(maintenance.getId() != null ? maintenance.getId() : -1));

                    Cell columnName = dataRow.createCell(1);
                    columnName.setCellValue(maintenance.getTitle() != null ? maintenance.getTitle() : "");

                    Cell columnDes = dataRow.createCell(2);
                    columnDes.setCellValue(maintenance.getUnitWage() != null ? maintenance.getUnitWage() : "");

                    Cell columnDob = dataRow.createCell(3);
                    columnDob.setCellValue(String.valueOf(maintenance.getPriceWage() != null ? maintenance.getPriceWage() : ""));

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
