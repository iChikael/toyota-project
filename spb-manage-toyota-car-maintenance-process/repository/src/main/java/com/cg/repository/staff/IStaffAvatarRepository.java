package com.cg.repository.staff;

import com.cg.domain.entity.staff.StaffAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStaffAvatarRepository extends JpaRepository<StaffAvatar, String> {
    Optional<StaffAvatar> findStaffAvatarByStaffIdAndDeletedIsFalse(String id);

}
