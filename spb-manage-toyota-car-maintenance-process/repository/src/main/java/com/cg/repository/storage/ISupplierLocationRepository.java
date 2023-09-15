package com.cg.repository.storage;

import com.cg.domain.entity.storage.SupplierLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierLocationRepository extends JpaRepository<SupplierLocation, Long> {
}
