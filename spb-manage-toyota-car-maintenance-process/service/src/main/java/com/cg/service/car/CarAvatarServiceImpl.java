package com.cg.service.car;

import com.cg.domain.entity.car.CarAvatar;
import com.cg.repository.car.ICarAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarAvatarServiceImpl implements ICarAvatarService {

    @Autowired
    private ICarAvatarRepository carAvatarRepository;

    @Override
    public List<CarAvatar> findAll() {
        return carAvatarRepository.findAll();
    }

    @Override
    public Optional<CarAvatar> findById(Long id) {
        return carAvatarRepository.findById(id);
    }

    @Override
    public CarAvatar save(CarAvatar carAvatar) {
        return carAvatarRepository.save(carAvatar);
    }

    @Override
    public void delete(CarAvatar carAvatar) {

    }

    @Override
    public void deleteById(Long id) {

    }

}
