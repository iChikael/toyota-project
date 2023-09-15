package com.cg.service.service.maintenance;

import com.cg.domain.dto.service.maintenanceItemAccessory.MaintenanceItemAccessoryResDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.entity.service.maintenance.MaintenanceItemAccessory;
import com.cg.domain.entity.storage.Accessory;
import com.cg.repository.service.maintenance.IMaintenanceItemAccessoryRepository;
import com.cg.repository.service.maintenance.IMaintenanceItemRepository;
import com.cg.repository.storage.IAccessoryRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MaintenanceItemAccessoryServiceImpl implements IMaintenanceItemAccessoryService {

    @Autowired
    private IMaintenanceItemAccessoryRepository maintenanceItemAccessoryRepository;

    @Autowired
    private IAccessoryRepository accessoryRepository;

    @Autowired
    private IMaintenanceItemRepository maintenanceItemRepository;

    @Override
    public List<MaintenanceItemAccessory> findAll() {
        return maintenanceItemAccessoryRepository.findAll();
    }

    @Override
    public Optional<MaintenanceItemAccessory> findById(Long id) {
        return maintenanceItemAccessoryRepository.findById(id);
    }

    @Override
    public MaintenanceItemAccessory save(MaintenanceItemAccessory maintenanceItemAccessory) {
        return maintenanceItemAccessoryRepository.save(maintenanceItemAccessory);
    }

    @Override
    public void delete(MaintenanceItemAccessory maintenanceItemAccessory) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<MaintenanceItemAccessoryResDTO> findAllByMaintenanceItem_Id(Long maintenanceItemId) {
        return maintenanceItemAccessoryRepository.findAllByMaintenanceItem_Id(maintenanceItemId);
    }

    @Override
    public void importToDb(MultipartFile multipartfile) {
        if (!multipartfile.isEmpty()) {
            List<MaintenanceItemAccessory> maintenanceItemAccessories = new ArrayList<>();

            try {
                XSSFWorkbook workBook = new XSSFWorkbook(multipartfile.getInputStream());
                XSSFSheet sheet = workBook.getSheetAt(0);

                for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet, 0); rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);

                    if (rowIndex == 0) {
                        continue;
                    }

                    Long accessoryId = Long.parseLong(String.valueOf(getValue(row.getCell(1))));
                    Long maintenanceItemId = Long.parseLong(String.valueOf(getValue(row.getCell(0))));

                    Optional<MaintenanceItem> maintenanceItemOptional = maintenanceItemRepository.findById(maintenanceItemId);
                    Optional<Accessory> accessory = accessoryRepository.findById(accessoryId);

                    MaintenanceItemAccessory maintenanceItemAccessory = MaintenanceItemAccessory.builder()
                            .accessory(accessory.get())
                            .maintenanceItem(maintenanceItemOptional.get())
                            .build();
                    maintenanceItemAccessories.add(maintenanceItemAccessory);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!maintenanceItemAccessories.isEmpty()) {
                maintenanceItemAccessoryRepository.saveAll(maintenanceItemAccessories);
            }
        }
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
