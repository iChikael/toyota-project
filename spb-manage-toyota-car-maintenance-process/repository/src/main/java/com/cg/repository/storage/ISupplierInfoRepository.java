package com.cg.repository.storage;

import com.cg.domain.entity.storage.SupplierInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierInfoRepository extends JpaRepository <SupplierInfo, Long> {
}
