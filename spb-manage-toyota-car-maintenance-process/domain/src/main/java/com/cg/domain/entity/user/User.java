package com.cg.domain.entity.user;

import com.cg.domain.dto.user.UserDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.IdentificationImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private UserRole userRole;

    @OneToMany(mappedBy = "user")
    private List<IdentificationImage> identificationImages;

    public UserDTO toUserDTO() {
        return new UserDTO()
                .setUsername(username)
                .setPassword(password);
    }

}
