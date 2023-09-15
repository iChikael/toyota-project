package com.cg.repository.storage;

import com.cg.domain.dto.accessory.AccessoryRoleResDTO;
import com.cg.domain.entity.storage.AccessoryRole;
import com.cg.domain.enums.EAccessoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccessoryRoleRepository extends JpaRepository<AccessoryRole, Long> {

    AccessoryRole getByName(EAccessoryType name);

    AccessoryRole getByCode(String code);

    AccessoryRole getFirstByCode(String code);


    @Query("SELECT NEW com.cg.domain.dto.accessory.AccessoryRoleResDTO (" +
                "accR.id, " +
                "accR.code, " +
                "accR.name " +
            ") " +
            "FROM AccessoryRole AS accR "
    )
    List<AccessoryRoleResDTO> findAllAccessoryRole();

}
