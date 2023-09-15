package com.cg.service.user;

import com.cg.domain.dto.user.UserInfoDTO;
import com.cg.domain.dto.user.UserRegisterReqDTO;
import com.cg.domain.entity.user.User;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService extends IGeneralService<User, String>, UserDetailsService {

    UserInfoDTO getInfoCustomerByUsername(String username);

    UserInfoDTO getInfoStaffByUsername(String username);

    User getByUsername(String username);

    Boolean existsByUsername(String username);

    User createStaff(User user, UserRegisterReqDTO userRegisterReqDTO, MultipartFile avatar, MultipartFile identificationImageBefore, MultipartFile identificationImageAfter);

    User createCustomer(User user, UserRegisterReqDTO userRegisterReqDTO, MultipartFile avatar, MultipartFile identificationImageBefore, MultipartFile identificationImageAfter);
}
