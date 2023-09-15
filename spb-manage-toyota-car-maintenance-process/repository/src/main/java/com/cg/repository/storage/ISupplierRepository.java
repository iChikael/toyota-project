package com.cg.repository.storage;

import com.cg.domain.entity.storage.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupplierRepository extends JpaRepository<Supplier, Long> {
}
