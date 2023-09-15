package com.cg.service.user;

import com.cg.domain.dto.user.UserInfoDTO;
import com.cg.domain.dto.user.UserRegisterReqDTO;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.customer.CustomerAvatar;
import com.cg.domain.entity.customer.CustomerLocation;
import com.cg.domain.entity.staff.Staff;
import com.cg.domain.entity.staff.StaffAvatar;
import com.cg.domain.entity.staff.StaffLocation;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserPrinciple;
import com.cg.exception.DataInputException;
import com.cg.repository.customer.ICustomerLocationRepository;
import com.cg.repository.customer.ICustomerRepository;
import com.cg.repository.staff.IStaffLocationRepository;
import com.cg.repository.staff.IStaffRepository;
import com.cg.repository.user.IUserRepository;
import com.cg.service.customer.ICustomerAvatarService;
import com.cg.service.staff.IStaffAvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IStaffRepository staffRepository;

    @Autowired
    private IStaffLocationRepository staffLocationRepository;

    @Autowired
    private IStaffAvatarService staffAvatarService;

    @Autowired
    private IIdentificationImageService iIdentificationImageService;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private ICustomerAvatarService customerAvatarService;

    @Autowired
    private ICustomerLocationRepository customerLocationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        if (userOptional.get().getUserRole().getCode().equals("CUSTOMER")) {

            UserInfoDTO userInfoDTO = getInfoCustomerByUsername(username);

            Optional<Customer> customerOptional = customerRepository.findByUser(userOptional.get());

            if (customerOptional.isPresent()) {
                userInfoDTO.setFullName(customerOptional.get().getFullName());
            }

            return UserPrinciple.build(userOptional.get(), userInfoDTO.getFullName());
        }
        else {
            UserInfoDTO userInfoDTO = getInfoStaffByUsername(username);

            Optional<Staff> staffOptional = staffRepository.findByUser(userOptional.get());

            if (staffOptional.isPresent()) {
                userInfoDTO.setFullName(staffOptional.get().getFullName());
            }

            return UserPrinciple.build(userOptional.get(), userInfoDTO.getFullName());
        }
    }

    @Override
    public UserInfoDTO getInfoCustomerByUsername(String username) {
        return userRepository.getInfoCustomerByUsername(username);
    }

    @Override
    public UserInfoDTO getInfoStaffByUsername(String username) {
        return userRepository.getInfoStaffByUsername(username);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User createStaff(User user, UserRegisterReqDTO userRegisterReqDTO, MultipartFile avatar, MultipartFile identificationImageBefore, MultipartFile identificationImageAfter) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userNew = userRepository.save(user);

        StaffLocation staffLocation = staffLocationRepository.save(userRegisterReqDTO.toStaffLocation(null));

        Staff staff = userRegisterReqDTO.toStaff(null);
        staff.setUser(user);
        staff.setStaffLocation(staffLocation);
        Staff newStaff = staffRepository.save(staff);

        if (avatar != null && avatar.getSize() != 0) {

            StaffAvatar staffAvatar = staffAvatarService.createStaffAvatar(avatar, newStaff);
            staffAvatar.setStaff(staff);
            staffAvatarService.save(staffAvatar);
        } else {
            StaffAvatar staffAvatar = new StaffAvatar();
            staffAvatar.setStaff(staff);
            staffAvatarService.save(staffAvatar);
        }

        if (identificationImageBefore == null || identificationImageBefore.getSize() == 0 || identificationImageAfter == null || identificationImageAfter.getSize() == 0) {
            throw new DataInputException("Ảnh CCCD không được để trống!");
        }

        iIdentificationImageService.createImage(identificationImageBefore, user);
        iIdentificationImageService.createImage(identificationImageAfter, user);

        return userNew;
    }

    @Override
    public User createCustomer(User user, UserRegisterReqDTO userRegisterReqDTO, MultipartFile avatar, MultipartFile identificationImageBefore, MultipartFile identificationImageAfter) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userNew = userRepository.save(user);

        CustomerLocation customerLocation = customerLocationRepository.save(userRegisterReqDTO.toCustomerLocation(null));

        Customer customer = userRegisterReqDTO.toCustomer(null);
        customer.setUser(user);
        customer.setCustomerLocation(customerLocation);
        Customer newCustomer = customerRepository.save(customer);

        if (avatar != null && avatar.getSize() != 0) {
            CustomerAvatar customerAvatar = customerAvatarService.createCustomerAvatar(avatar, newCustomer);
            customerAvatar.setCustomer(customer);
            customerAvatarService.save(customerAvatar);
        } else {
            CustomerAvatar customerAvatar = new CustomerAvatar();
            customerAvatar.setCustomer(customer);
            customerAvatarService.save(customerAvatar);
        }

        if (identificationImageBefore == null || identificationImageBefore.getSize() == 0 || identificationImageAfter == null || identificationImageAfter.getSize() == 0) {
            throw new DataInputException("Ảnh CCCD không được để trống!");
        }

        iIdentificationImageService.createImage(identificationImageBefore, user);
        iIdentificationImageService.createImage(identificationImageAfter, user);

        return userNew;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteById(String id) {

    }
}
