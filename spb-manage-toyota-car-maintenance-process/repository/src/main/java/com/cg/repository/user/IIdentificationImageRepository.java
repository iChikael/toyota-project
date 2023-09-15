package com.cg.repository.user;

import com.cg.domain.entity.IdentificationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIdentificationImageRepository extends JpaRepository<IdentificationImage, String> {
}
