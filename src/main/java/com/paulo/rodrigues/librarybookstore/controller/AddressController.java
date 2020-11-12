/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.controller;

import com.paulo.rodrigues.librarybookstore.dto.AddressDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.model.Address;
import com.paulo.rodrigues.librarybookstore.service.AddressService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author paulo.rodrigues
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/addresses")
public class AddressController {
    
    @Autowired
    private AddressService addressService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Address> getById(@PathVariable(value = "id") Long addressId) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(addressService.findById(addressId));
    }

    @PostMapping()
    public AddressDTO create(@RequestBody Address address) throws LibraryStoreBooksException {
        return addressService.create(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable(value = "id") Long addressId, @RequestBody AddressDTO addressDetalhes) throws LibraryStoreBooksException {
        return ResponseEntity.ok(addressService.edit(addressId, addressDetalhes));
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long addressId) throws LibraryStoreBooksException {
        addressService.erase(addressId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
