package com.cg.service.storage;

import com.cg.domain.dto.accessory.AccessoryRoleResDTO;
import com.cg.domain.entity.storage.AccessoryRole;
import com.cg.repository.storage.IAccessoryRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AccessoryRoleServiceImpl implements IAccessoryRoleService{

    @Autowired
    private IAccessoryRoleRepository accessoryRoleRepository;

    @Override
    public List<AccessoryRole> findAll() {
        return accessoryRoleRepository.findAll();
    }


    @Override
    public Optional<AccessoryRole> findById(Long id) {
        return accessoryRoleRepository.findById(id);
    }

    @Override
    public AccessoryRole save(AccessoryRole accessoryRole) {
        return accessoryRoleRepository.save(accessoryRole);
    }

    @Override
    public void delete(AccessoryRole accessoryRole) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<AccessoryRoleResDTO> getAllAccessoryResDTO() {
        return accessoryRoleRepository.findAllAccessoryRole();
    }
}
