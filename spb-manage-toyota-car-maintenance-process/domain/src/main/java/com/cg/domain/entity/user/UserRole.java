package com.cg.domain.entity.user;

import com.cg.domain.dto.role.UserRoleDTO;
import com.cg.domain.enums.EUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EUserRole name;

    @OneToMany(mappedBy = "userRole")
    private List<User> users;

    public UserRoleDTO toRoleDTO() {
        return new UserRoleDTO()
                .setId(id)
                .setCode(code)
                .setName(name)
                ;
    }
}
