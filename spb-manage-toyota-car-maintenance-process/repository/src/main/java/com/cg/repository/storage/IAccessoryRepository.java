package com.cg.repository.storage;

import com.cg.domain.dto.accessory.AccessoryReceptionResDTO;
import com.cg.domain.dto.accessory.AccessoryResDTO;
import com.cg.domain.entity.storage.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface IAccessoryRepository extends JpaRepository<Accessory, Long> {

    @Query("SELECT NEW com.cg.domain.dto.accessory.AccessoryResDTO (" +
                "acc.id, " +
                "acc.name, " +
                "acc.code, " +
                "acc.price," +
                "acc.quantity," +
                "acc.unit," +
                "acc.accessoryRole " +
            ") " +
            "FROM Accessory AS acc " +
            "WHERE acc.deleted = false"
    )
    List<AccessoryResDTO> findAllAccessories();

    @Query("SELECT NEW com.cg.domain.dto.accessory.AccessoryReceptionResDTO (" +
                "acc.id, " +
                "acc.name, " +
                "acc.code, " +
                "acc.price," +
                "acc.unit," +
                "acc.accessoryRole " +
            ") " +
            "FROM Accessory AS acc " +
            "WHERE acc.deleted = false"
    )
    List<AccessoryReceptionResDTO> findAllAccessoryMaintenanceItems();

    Optional<Accessory> findByIdAndDeletedIsFalse (Long id);

    Accessory getByCodeAndDeletedIsFalse(String code);

    Optional<Accessory> getByCode( String code);

    Optional<Accessory> findById(Long accessoryId);

    Boolean existsAccessoriesByCode(String code);

}
