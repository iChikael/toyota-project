package com.cg.service.car;

import com.cg.cloudinary.CloudinaryUploadUtil;
import com.cg.domain.dto.car.CarCreateReqDTO;
import com.cg.domain.dto.car.CarResDTO;
import com.cg.domain.dto.car.CarUpdateReqDTO;
import com.cg.domain.entity.car.Car;
import com.cg.domain.entity.car.CarAvatar;
import com.cg.domain.entity.car.Vehicle;
import com.cg.domain.enums.EFuel;
import com.cg.domain.enums.EOrigin;
import com.cg.domain.enums.ESeat;
import com.cg.exception.DataInputException;
import com.cg.repository.car.ICarAvatarRepository;
import com.cg.repository.car.ICarRepository;
import com.cg.repository.car.IVehicleRepository;
import com.cg.service.upload.IUploadService;
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
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class CarServiceImpl implements ICarService {

    @Autowired
    private ICarRepository carRepository;

    @Autowired
    private ICarAvatarRepository carAvatarRepository;

    @Autowired
    private
    IUploadService uploadService;

    @Autowired
    IVehicleRepository vehicleRepository;

    @Autowired
    ICarAvatarService carAvatarService;

    @Autowired
    private CloudinaryUploadUtil cloudinaryUploadUtil;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void delete(Car car) {
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public List<CarResDTO> findAllCarResDTO() {
        return carRepository.findAllCarResDTO();
    }

    @Override
    public List<CarResDTO> findAllCarResDTOSByKeyWord(String name) {
        return carRepository.findAllCarResDTOSByKeyWord(name);
    }

    @Override
    public Optional<CarResDTO> findCarResDTOById(Long carId) {
        return carRepository.findCarResDTOById(carId);
    }

    @Override
    public Car createCar(CarCreateReqDTO carCreateReqDTO, Vehicle vehicle) {
        CarAvatar carAvatar = new CarAvatar();
        carAvatarRepository.save(carAvatar);
        uploadAndSaveCarAvatar(carCreateReqDTO.getCarAvatar(), carAvatar);
        Car car = carCreateReqDTO.toCar(vehicle);
        car.setQuantity(0L);
        car.setCarAvatar(carAvatar);
        carRepository.save(car);
        return car;
    }

    @Override
    public Car updateCar(Car car, CarUpdateReqDTO carUpdateReqDTO, Vehicle vehicle) {
        car.setDeleted(true);
        carRepository.save(car);
        Car updateCar = carUpdateReqDTO.toCar(vehicle);

        if (carUpdateReqDTO.getCarAvatar() == null && carUpdateReqDTO.getCarAvatar().getSize() != 0) {
            CarAvatar carAvatar = new CarAvatar();
            carAvatarRepository.save(carAvatar);
            uploadAndSaveCarAvatar(carUpdateReqDTO.getCarAvatar(), carAvatar);
            updateCar.setCarAvatar(carAvatar);
        }
        else {
            updateCar.setCarAvatar(car.getCarAvatar());
        }

        updateCar.setId(null);
        updateCar.setCode(car.getCode());
        updateCar.setQuantity(car.getQuantity());
        carRepository.save(updateCar);

        return updateCar;
    }

    private void uploadAndSaveCarAvatar(MultipartFile avatar, CarAvatar carAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(
                    avatar,
                    cloudinaryUploadUtil.buildImageUploadParams(
                            carAvatar.getId().toString(),
                            cloudinaryUploadUtil.CAR_IMAGE_UPLOAD_FOLDER,
                            cloudinaryUploadUtil.ERROR_IMAGE_UPLOAD
                    )
            );

            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");
            carAvatar.setFileName(carAvatar.getId() + "." + fileFormat);
            carAvatar.setFileUrl(fileUrl);
            carAvatar.setFileFolder(cloudinaryUploadUtil.CAR_IMAGE_UPLOAD_FOLDER);
            carAvatar.setCloudId(carAvatar.getFileFolder() + "/" + carAvatar.getId());
            carAvatarRepository.save(carAvatar);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public void importToDb(MultipartFile multipartfile) {

        if (!multipartfile.isEmpty()) {
            List<Car> cars = new ArrayList<>();

            try {
                XSSFWorkbook workBook = new XSSFWorkbook(multipartfile.getInputStream());

                XSSFSheet sheet = workBook.getSheetAt(0);
                for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet, 0); rowIndex++) {
                    XSSFRow row = sheet.getRow(rowIndex);
                    if (rowIndex == 0) {
                        continue;
                    }

                    String code = String.valueOf(getValue(row.getCell(1)));
                    String title = String.valueOf(getValue(row.getCell(2)));
                    Long quantity = Long.parseLong(String.valueOf(getValue(row.getCell(3))));
                    BigDecimal price = BigDecimal.valueOf(Long.parseLong(String.valueOf(getValue(row.getCell(4)))));
                    String description = (String.valueOf(getValue(row.getCell(5))));
                    Long vehicleId = Long.parseLong(String.valueOf(getValue(row.getCell(6))));
                    String seat = String.valueOf(getValue(row.getCell(7)));
                    String fuel = String.valueOf(getValue(row.getCell(8)));
                    String origin = String.valueOf(getValue(row.getCell(9)));
                    Long avatarId = Long.parseLong(String.valueOf(row.getCell(10)));


                    Vehicle vehicle = vehicleRepository.getById(vehicleId);
                    CarAvatar carAvatar = carAvatarRepository.getById(avatarId);

                    List<Car> existingCars = carRepository.getAllByTitle(title);

                    if (!existingCars.isEmpty()) {
                        for (Car existingCar : existingCars) {
                            Long existingQuantity = existingCar.getQuantity();
                            existingCar.setQuantity(existingQuantity + quantity);
                            existingCars.add(existingCar);
                        }
                    } else {
                        Car car = Car.builder()
                                .code(code)
                                .title(title)
                                .quantity(quantity)
                                .price(price)
                                .description(description)
                                .vehicle(vehicle)
                                .seatCode(ESeat.getESeatByName(seat))
                                .fuelCode(EFuel.getEFuelByName(fuel))
                                .originCode(EOrigin.getEOriginByName(origin))
                                .carAvatar(carAvatar)
                                .build();

                        cars.add(car);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!cars.isEmpty()) {
                carRepository.saveAll(cars);
            }
        }
    }

    @Override
    public StreamingResponseBody exportToExcel(HttpServletResponse response) {

        List<Car> cars = carRepository.findAll();

        if (cars.isEmpty()) {
            throw new NullPointerException("No data found in database");
        }

        return outputStream -> {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE)) {

                String sheetName = "Accessories";
                Sheet sheet = workbook.createSheet(sheetName);

                Font headerFont = workbook.createFont();
                headerFont.setColor(IndexedColors.BLACK.getIndex());

                CellStyle headerColumnStyle = workbook.createCellStyle();
                headerColumnStyle.setFont(headerFont);

                Row headerRow = sheet.createRow(0);

                String[] columns = new String[]{"Id", "Code", "Title", "Quantity", "Price", "Description", "Vehicle", "Seat", "Fuel", "Origin", "Avatar"};

                for (int i = 0; i < columns.length; i++) {
                    Cell headerColumn = headerRow.createCell(i);
                    headerColumn.setCellValue(columns[i]);
                    headerColumn.setCellStyle(headerColumnStyle);
                }

                CellStyle dataColumnDateFormatStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dataColumnDateFormatStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yy h:mm;@"));

                int rowIndex = 1;
                for (Car car : cars) {

                    Row dataRow = sheet.createRow(rowIndex);

                    Cell columnId = dataRow.createCell(0);
                    columnId.setCellValue(car.getId() != null ? car.getId() : -1);

                    Cell columnCode = dataRow.createCell(1);
                    columnCode.setCellValue(car.getCode() != null ? car.getCode() : "");

                    Cell columnTitle = dataRow.createCell(2);
                    columnTitle.setCellValue(car.getTitle() != null ? car.getTitle() : "");

                    Cell columnQuantity = dataRow.createCell(3);
                    columnQuantity.setCellValue(String.valueOf(car.getQuantity() != null ? car.getQuantity() : ""));

                    Cell columnPrice = dataRow.createCell(4);
                    columnPrice.setCellValue(String.valueOf(car.getPrice() != null ? car.getPrice() : ""));

                    Cell columnDescription = dataRow.createCell(5);
                    columnDescription.setCellValue((car.getDescription() != null) ? car.getDescription() : "");

                    Cell columnVehicle = dataRow.createCell(6);
                    columnVehicle.setCellValue((car.getVehicle().getName() != null ? car.getVehicle().getName() : ""));

                    Cell columnSeat = dataRow.createCell(7);
                    columnSeat.setCellValue(car.getSeatCode().getValue() != null ? car.getSeatCode().getValue() : "");

                    Cell columnFuel = dataRow.createCell(8);
                    columnFuel.setCellValue(car.getFuelCode().getValue() != null ? car.getFuelCode().getValue() : "");

                    Cell columnOrigin = dataRow.createCell(8);
                    columnOrigin.setCellValue(car.getOriginCode().getValue() != null ? car.getOriginCode().getValue() : "");

                    Cell columnAvatar = dataRow.createCell(10);
                    columnAvatar.setCellValue(car.getCarAvatar().getFileUrl() != null ? car.getCarAvatar().getFileUrl() : "");

                    rowIndex++;
                }

                workbook.write(out);
                workbook.dispose();

                String filename = "Export-car-data" + ".xlsx";
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
    public Boolean isExistCar(String title) {
        return carRepository.existsCarByTitle(title);
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
