package com.cg.service.customer;

import com.cg.domain.dto.customer.CustomerResDTO;
import com.cg.domain.dto.customer.CustomerUpdateInfoReqDTO;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.customer.CustomerAvatar;
import com.cg.domain.entity.customer.CustomerLocation;
import com.cg.domain.entity.user.User;
import com.cg.repository.customer.ICustomerAvatarRepository;
import com.cg.repository.customer.ICustomerLocationRepository;
import com.cg.repository.customer.ICustomerRepository;
import com.cg.repository.user.IUserRepository;
import com.cg.utils.AppUtils;
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
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICustomerLocationService customerLocationService;

    @Autowired
    private ICustomerAvatarRepository customerAvatarRepository;

    @Autowired
    private ICustomerLocationRepository customerLocationRepository;


    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        Optional<User> userOptional = userRepository.findByUsername(customer.getUser().getUsername());
        User user = userOptional.get();
        user.setDeleted(true);
        userRepository.save(user);

        Optional<CustomerAvatar> customerAvatarOptional = customerAvatarRepository.findCustomerAvatarByCustomerIdAndDeletedIsFalse(customer.getId());
        CustomerAvatar customerAvatar = customerAvatarOptional.get();
        customerAvatar.setDeleted(true);
        customerAvatarRepository.save(customerAvatar);

        customer.setDeleted(true);
        customerRepository.save(customer);
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public List<CustomerResDTO> findAllCustomerResDTO() {
        return customerRepository.findAllCustomerResDTO();
    }

    @Override
    public List<CustomerResDTO> findAllCustomerResDTOByKeyWord(String phone) {
        return customerRepository.findAllCustomerResDTOByKeyWord(phone);
    }

    @Override
    public Customer updateCustomer(Customer customer, CustomerUpdateInfoReqDTO customerUpdateInfoReqDTO) {

        customer.setFullName(customerUpdateInfoReqDTO.getFullName());

        customer.setDob(appUtils.convertStringToLocalDate(customerUpdateInfoReqDTO.getDob()));

        customer.setEmail(customerUpdateInfoReqDTO.getEmail());

        Optional<CustomerLocation> customerLocationOptional = customerLocationRepository.findById(customer.getCustomerLocation().getId());

        CustomerLocation customerLocation = customerUpdateInfoReqDTO.toCustomerLocation(customerLocationOptional.get().getId());

        customerLocationRepository.save(customerLocation);

        customer.setCustomerLocation(customerLocation);

        customerRepository.save(customer);

        return customer;
    }

    @Override
    public Optional<Customer> findByUser(User currentUser) {
        return customerRepository.findByUser(currentUser);
    }

    @Override
    public Optional<CustomerResDTO> findCustomerByIdResDTO(String username) {
        return customerRepository.findCustomerByIdResDTO(username);
    }

    @Override
    public void importToDb(MultipartFile multipartfile) {

        if (!multipartfile.isEmpty()) {
            List<Customer> customers = new ArrayList<>();

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
                    Long customerLocation = Long.parseLong(String.valueOf(row.getCell(6)));

                    Optional<CustomerLocation> customerLocationOptional = customerLocationService.findById(customerLocation);

                    Customer customer = Customer.builder()
                            .fullName(fullName)
                            .identificationNumber(identificationNumber)
                            .email(email)
                            .dob(LocalDate.parse(dob))
                            .phone(phone)
                            .customerLocation(customerLocationOptional.get())
                            .build();

                    customers.add(customer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!customers.isEmpty()) {
                customerRepository.saveAll(customers);
            }
        }
    }

    @Override
    public StreamingResponseBody exportToExcel(HttpServletResponse response) {

        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
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

                String[] columns = new String[]{"Id", "FullName", "Identification Number","Email", "Date of birth", "Phone","Address","City"};

                for (int i = 0; i < columns.length; i++) {
                    Cell headerColumn = headerRow.createCell(i);
                    headerColumn.setCellValue(columns[i]);
                    headerColumn.setCellStyle(headerColumnStyle);
                }

                CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy h:mm;@"));

                int rowIndex = 1;
                for (Customer customer : customers) {

                    Row dataRow = sheet.createRow(rowIndex);

                    Cell columnId = dataRow.createCell(0);
                    columnId.setCellValue(String.valueOf(customer.getId() != null ? customer.getId() : -1));

                    Cell columnName = dataRow.createCell(1);
                    columnName.setCellValue(customer.getFullName() != null ? customer.getFullName() : "");

                    Cell columnDes = dataRow.createCell(2);
                    columnDes.setCellValue(customer.getIdentificationNumber() != null ? customer.getIdentificationNumber() : "");

                    Cell columnDob = dataRow.createCell(3);
                    columnDob.setCellValue(String.valueOf(customer.getDob() != null ? customer.getDob() : ""));

                    Cell columnPhone = dataRow.createCell(4);
                    columnPhone.setCellValue(customer.getPhone() != null ? customer.getPhone() : "");

                    Cell columnAddress = dataRow.createCell(5);
                    columnAddress.setCellValue(customer.getCustomerLocation().getAddress() != null ? customer.getCustomerLocation().getAddress() : "");

                    rowIndex++;
                }

                workbook.write(out);
                workbook.dispose();

                String filename = "Export-customer-data" + ".xlsx";
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
