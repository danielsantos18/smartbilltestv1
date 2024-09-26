package com.v1.smartbill.controller;


import com.v1.smartbill.model.SmartPhone;
import com.v1.smartbill.service.impl.SmartPhoneServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/smartphone/")
//@CrossOrigin(origins = "http://localhost:4200")
public class SmartPhoneController {

    private SmartPhoneServiceImpl phoneService;

    @Autowired
    public SmartPhoneController(SmartPhoneServiceImpl phoneService) {
        this.phoneService = phoneService;
    }

    //Petición para crear
    @PostMapping(value = "save", headers = "Accept=application/json")
    public void crearCelular(@RequestBody SmartPhone smartPhone) {
        phoneService.create(smartPhone);
    }

    //Petición para obtener todos los celulares en la BD
    @GetMapping(value = "list", headers = "Accept=application/json")
    public List<SmartPhone> listarCelulares() {
        return phoneService.readAll();
    }

    //Petición para obtener mediante "ID"
    @GetMapping(value = "listId/{id}", headers = "Accept=application/json")
    public Optional<SmartPhone> obtenerCelularPorId(@PathVariable Long id) {
        return phoneService.readOne(id);
    }

    //Petición para actualizar
    @PutMapping(value = "update/{id}", headers = "Accept=application/json")
    public void actualizarCelular(@PathVariable Long id , @Valid @RequestBody SmartPhone smartPhone) {
        phoneService.update(id,smartPhone);
    }

    //Petición para eliminar por "Id"
    @DeleteMapping(value = "delete/{id}", headers = "Accept=application/json")
    public void eliminarCelular(@PathVariable Long id) {
        phoneService.delete(id);
    }
}
