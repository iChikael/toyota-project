package com.cg.repository.car;

import com.cg.domain.entity.car.CarAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarAvatarRepository extends JpaRepository<CarAvatar, Long> {

}
