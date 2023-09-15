package com.cg.repository.user;

import com.cg.domain.dto.user.UserInfoDTO;
import com.cg.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("SELECT NEW com.cg.domain.dto.user.UserInfoDTO (" +
                "us.username, " +
                "sf.fullName, " +
                "us.userRole " +
            ") " +
            "FROM User AS us " +
            "JOIN Staff AS sf " +
            "ON sf.user.id = us.id " +
            "AND us.username = :username "
    )
    UserInfoDTO getInfoStaffByUsername(@Param("username") String username);

    @Query("SELECT NEW com.cg.domain.dto.user.UserInfoDTO (" +
                "us.username, " +
                "cs.fullName, " +
                "us.userRole " +
            ") " +
            "FROM User AS us " +
            "JOIN Customer AS cs " +
            "ON cs.user.id = us.id " +
            "AND us.username = :username "
    )
    UserInfoDTO getInfoCustomerByUsername(@Param("username") String username);

    User getByUsername(String username);

    Boolean existsByUsername(String username);
}
