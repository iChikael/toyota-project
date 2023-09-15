package com.cg.domain.dto.user;

import com.cg.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserLoginReqDTO {

    @Pattern(regexp = "^(\\\\+?84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$", message = "Username hoặc số điện thoại không hợp lệ!")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu!")
    @Size(min = 3, max = 50, message = "Độ dài mật khẩu nằm trong khoảng 3-50 ký tự!")
    private String password;


    public User toUser() {
        return new User()
                .setUsername(username)
                .setPassword(password);
    }
}
