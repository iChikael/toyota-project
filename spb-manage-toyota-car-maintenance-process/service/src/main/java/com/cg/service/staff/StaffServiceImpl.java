package com.cg.service.staff;


import com.cg.domain.dto.DashboardDTO;
import com.cg.domain.dto.staff.StaffResDTO;
import com.cg.domain.dto.staff.StaffUpdateInfoReqDTO;
import com.cg.domain.entity.staff.Staff;
import com.cg.domain.entity.staff.StaffAvatar;
import com.cg.domain.entity.staff.StaffLocation;
import com.cg.domain.entity.user.User;
import com.cg.repository.staff.IStaffAvatarRepository;
import com.cg.repository.staff.IStaffLocationRepository;
import com.cg.repository.staff.IStaffRepository;
import com.cg.repository.user.IUserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

    @Autowired
    private IStaffRepository staffRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IStaffLocationService staffLocationService;

    @Autowired
    private IStaffAvatarRepository staffAvatarRepository;

    @Autowired
    private IStaffLocationRepository staffLocationRepository;

    @Autowired
    private IStaffAvatarService staffAvatarService;


    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Optional<Staff> findById(String id) {
        return staffRepository.findById(id);
    }

    @Override
    public Optional<Staff> findByUser(User user) {
        return staffRepository.findByUser(user);
    }

    @Override
    public List<StaffResDTO> findAllStaffResDTO() {
        return staffRepository.findAllStaffResDTO();
    }

    @Override
    public Staff updateStaff(Staff staff, StaffUpdateInfoReqDTO staffUpdateInfoReqDTO) {

        staff.setFullName(staffUpdateInfoReqDTO.getFullName());

        staff.setDob(convertStringToLocalDate(staffUpdateInfoReqDTO.getDob()));

        staff.setEmail(staffUpdateInfoReqDTO.getEmail());

        Optional<StaffLocation> staffLocationOptional = staffLocationRepository.findById(staff.getStaffLocation().getId());

        StaffLocation staffLocation = staffUpdateInfoReqDTO.toStaffLocation(staffLocationOptional.get().getId());

        staffLocationRepository.save(staffLocation);

        staff.setStaffLocation(staffLocation);

        staffRepository.save(staff);

        return staff;
    }

    @Override
    public Optional<StaffResDTO> findStaffByIdResDTO(String username) {
        return staffRepository.findStaffByIdResDTO(username);
    }

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public void delete(Staff staff) {
        Optional<User> userOptional = userRepository.findByUsername(staff.getUser().getUsername());
        User user = userOptional.get();
        user.setDeleted(true);
        userRepository.save(user);

        Optional<StaffAvatar> staffAvatarOptional = staffAvatarRepository.findStaffAvatarByStaffIdAndDeletedIsFalse(staff.getId());
        StaffAvatar staffAvatar = staffAvatarOptional.get();
        staffAvatar.setDeleted(true);
        staffAvatarRepository.save(staffAvatar);

        staff.setDeleted(true);
        staffRepository.save(staff);
    }

    @Override
    public void deleteById(String id) {

    }

    public static LocalDate convertStringToLocalDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, formatter);
    }

    @Override
    public void importToDb(MultipartFile multipartfile) {

        if (!multipartfile.isEmpty()) {
            List<Staff> staffs = new ArrayList<>();

            try {
                XSSFWorkbook workBook = new XSSFWorkbook(multipartfile.getInputStream());

                XSSFSheet sheet = workBook.getSheetAt(0);
                for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet, 0); rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);
                    if (rowIndex == 0) {
                        continue;
                    }

                    String fullName = String.valueOf(getValue(row.getCell(1)));
                    String identificationNumber = String.valueOf(getValue(row.getCell(2)));
                    String email = String.valueOf(getValue(row.getCell(3)));
                    String dob = String.valueOf(getValue(row.getCell(4)));
                    String phone = String.valueOf(getValue(row.getCell(5)));
                    Long staffLocation = Long.parseLong(String.valueOf(row.getCell(6)));

                    Optional<StaffLocation> staffLocationOptional = staffLocationService.findById(staffLocation);

                    Staff staff = Staff.builder()
                            .fullName(fullName)
                            .identificationNumber(identificationNumber)
                            .email(email)
                            .dob(LocalDate.parse(dob))
                            .phone(phone)
                            .staffLocation(staffLocationOptional.get())
                            .build();

                    staffs.add(staff);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!staffs.isEmpty()) {
                staffRepository.saveAll(staffs);
            }
        }
    }

    @Override
    public StreamingResponseBody exportToExcel(HttpServletResponse response) {

        List<Staff> staffs = staffRepository.findAll();

        if (staffs.isEmpty()) {
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

                String[] columns = new String[]{"Id", "FullName", "Identification Number","Email", "Date of birth", "Phone","Address"};

                for (int i = 0; i < columns.length; i++) {
                    Cell headerColumn = headerRow.createCell(i);
                    headerColumn.setCellValue(columns[i]);
                    headerColumn.setCellStyle(headerColumnStyle);
                }

                CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy h:mm;@"));

                int rowIndex = 1;
                for (Staff staff : staffs) {

                    Row dataRow = sheet.createRow(rowIndex);

                    Cell columnId = dataRow.createCell(0);
                    columnId.setCellValue(String.valueOf(staff.getId() != null ? staff.getId() : -1));

                    Cell columnName = dataRow.createCell(1);
                    columnName.setCellValue(staff.getFullName() != null ? staff.getFullName() : "");

                    Cell columnDes = dataRow.createCell(2);
                    columnDes.setCellValue(staff.getIdentificationNumber() != null ? staff.getIdentificationNumber() : "");

                    Cell columnDob = dataRow.createCell(3);
                    columnDob.setCellValue(String.valueOf(staff.getDob() != null ? staff.getDob() : ""));

                    Cell columnPhone = dataRow.createCell(4);
                    columnPhone.setCellValue(staff.getPhone() != null ? staff.getPhone() : "");

                    Cell columnAddress = dataRow.createCell(5);
                    columnAddress.setCellValue(staff.getStaffLocation().getAddress() != null ? staff.getStaffLocation().getAddress() : "");

                    rowIndex++;
                }

                workbook.write(out);
                workbook.dispose();

                String filename = "Export-staff-data" + ".xlsx";
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

    @Override
    public DashboardDTO getDashboardInfo() {
        return staffRepository.getDashboardInfo();
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
