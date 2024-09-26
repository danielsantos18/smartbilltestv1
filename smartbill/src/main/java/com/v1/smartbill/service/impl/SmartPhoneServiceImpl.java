package com.v1.smartbill.service.impl;

import com.v1.smartbill.model.SmartPhone;
import com.v1.smartbill.repository.ISmartphoneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SmartPhoneServiceImpl {
    private ISmartphoneRepository smartPhoneRepo;

    @Autowired
    public SmartPhoneServiceImpl(ISmartphoneRepository smartPhoneRepo) {
        this.smartPhoneRepo = smartPhoneRepo;
    }

    //Creamos un celular
    public void create(SmartPhone smartPhone) {
        smartPhoneRepo.save(smartPhone);
    }

    //Obtenemos toda una lista de celulares
    public List<SmartPhone> readAll() {
        return smartPhoneRepo.findAll();
    }

    //Obtenemos un celular por su id
    public Optional<SmartPhone> readOne(Long id) {
        return smartPhoneRepo.findById(id);
    }

    //Actualizamos un celular
    public SmartPhone update(Long id, SmartPhone smartPhone) {
            return smartPhoneRepo.findById(id)
                    .map(existingSmartPhone -> {
                        existingSmartPhone.setMark(smartPhone.getMark());
                        existingSmartPhone.setPrice(smartPhone.getPrice());
                        return smartPhoneRepo.save(existingSmartPhone);
                    })
                    .orElseThrow(() -> new EntityNotFoundException("SmartPhone not found with id: " + id));
        }

    //Eliminamos un celular
    public void delete(Long id) {
        smartPhoneRepo.deleteById(id);
    }
}
