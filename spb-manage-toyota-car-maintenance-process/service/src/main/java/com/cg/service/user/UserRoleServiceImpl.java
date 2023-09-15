package com.cg.service.user;

import com.cg.domain.entity.user.UserRole;
import com.cg.repository.user.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private IUserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public Optional<UserRole> findById(Long id) {
        return userRoleRepository.findById(id);
    }

    @Override
    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public void delete(UserRole userRole) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<UserRole> findByCode(String code) {
        return userRoleRepository.findByCode(code);
    }
}
