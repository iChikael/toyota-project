package com.cg.api.v1;

import com.cg.domain.dto.jwt.JwtResponseCustomer;
import com.cg.domain.dto.jwt.JwtResponseStaff;
import com.cg.domain.dto.user.UserLoginReqDTO;
import com.cg.domain.dto.user.UserRegisterReqDTO;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.staff.Staff;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserRole;
import com.cg.domain.enums.EUserRole;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.exception.UnauthorizedException;
import com.cg.service.customer.ICustomerService;
import com.cg.service.jwt.JwtService;
import com.cg.service.staff.IStaffService;
import com.cg.service.user.IUserRoleService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private ICustomerService customerService;


    @PostMapping("/staff/login")
    public ResponseEntity<?> loginStaff(@Valid @RequestBody UserLoginReqDTO userLoginReqDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginReqDTO.getUsername(), userLoginReqDTO.getPassword()));
        }
        catch (Exception ex) {
            throw new UnauthorizedException("Tài khoản hoặc mật khẩu không đúng");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User currentUser = userService.getByUsername(userLoginReqDTO.getUsername());

        if (currentUser.getDeleted()) {
            throw new UnauthorizedException("Tài khoản của bạn đã bị khóa!");
        }

        Staff staff = staffService.findByUser(currentUser).orElseThrow(() -> {
            throw new UnauthorizedException("Tài khoản chưa xác thực");
        });

        UserRole userRole = currentUser.getUserRole();
        EUserRole eUserRole = userRole.getName();

        if (eUserRole == EUserRole.ROLE_CUSTOMER) {
            throw new UnauthorizedException("Bạn không đủ quyền để thực hiện xác thực đăng nhập");
        }

        try {
            JwtResponseStaff jwtResponseStaff = new JwtResponseStaff(
                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    staff.getFullName(),
                    userDetails.getAuthorities()
            );

            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(appUtils.TOKEN_MAX_AGE)
                    .domain(appUtils.DOMAIN_SERVER)
                    .build();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponseStaff);

        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException("Dữ liệu không đúng! Vui lòng kiểm tra lại!");
        }
    }

    @PostMapping("/staff/register")
    public ResponseEntity<?> registerStaff(@Valid @ModelAttribute UserRegisterReqDTO userRegisterReqDTO,
                                           MultipartFile avatar,
                                           MultipartFile identificationImageBefore,
                                           MultipartFile identificationImageAfter,
                                           BindingResult bindingResult) {

            if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existsByUsername = userService.existsByUsername(userRegisterReqDTO.getUsername());

        if (existsByUsername) {
            throw new EmailExistsException("Tài khoản đã tồn tại!");
        }

//        if (!validateUtils.isNumberValid(userRegisterReqDTO.getIdUserRole()) || userRegisterReqDTO.getIdUserRole() == null) {
//            throw new DataInputException("Mã bộ phận không hợp lệ!");
//        }
//        Long idUserRole = Long.parseLong(userRegisterReqDTO.getIdUserRole());

        Optional<UserRole> userRoleOptional = userRoleService.findByCode(userRegisterReqDTO.getUserRoleCode());

        if (userRoleOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy chức vụ hiện có!");
        }

        try {

            User user = userService.createStaff(userRegisterReqDTO.toUser(null, userRoleOptional.get()), userRegisterReqDTO, avatar, identificationImageBefore, identificationImageAfter);

            return new ResponseEntity<>(user.toUserDTO() ,HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Thông tin tài khoản không hợp lệ, vui lòng kiểm tra lại!");
        }
    }


    @PostMapping("/customer/login")
    public ResponseEntity<?> loginCustomer(@Valid @RequestBody UserLoginReqDTO userLoginReqDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginReqDTO.getUsername(), userLoginReqDTO.getPassword()));
        }
        catch (Exception ex) {
            throw new UnauthorizedException("Tài khoản hoặc mật khẩu không đúng");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User currentUser = userService.getByUsername(userLoginReqDTO.getUsername());

        if (currentUser.getDeleted()) {
            throw new UnauthorizedException("Tài khoản của bạn đã bị khóa!");
        }

        Customer customer = customerService.findByUser(currentUser).orElseThrow(() -> {
            throw new UnauthorizedException("Tài khoản chưa xác thực");
        });

        try {
            JwtResponseCustomer jwtResponseCustomer = new JwtResponseCustomer(
                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    customer.getFullName(),
                    userDetails.getAuthorities()
            );

            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(appUtils.TOKEN_MAX_AGE)
                    .domain(appUtils.DOMAIN_SERVER)
                    .build();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponseCustomer);

        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException("Dữ liệu không đúng! Vui lòng kiểm tra lại!");
        }
    }


    @PostMapping("/customer/register")
    public ResponseEntity<?> registerCustomer(
            @Valid @ModelAttribute UserRegisterReqDTO userRegisterReqDTO,
            MultipartFile avatar,
            MultipartFile identificationImageBefore,
            MultipartFile identificationImageAfter,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existsByUsername = userService.existsByUsername(userRegisterReqDTO.getUsername());

        if (existsByUsername) {
            throw new EmailExistsException("Tài khoản đã tồn tại");
        }

        Optional<UserRole> userRoleOptional = userRoleService.findById(7L);

        try {
            User user = userService.createCustomer(userRegisterReqDTO.toUser(null, userRoleOptional.get()), userRegisterReqDTO, avatar, identificationImageBefore, identificationImageAfter);

            return new ResponseEntity<>(user.toUserDTO(), HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Thông tin tài khoản không hợp lệ, vui lòng kiểm tra lại!");
        }
    }

}

