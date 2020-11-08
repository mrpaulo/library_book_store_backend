/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.dto.AddressDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.model.Address;
import com.paulo.rodrigues.librarybookstore.repository.AddressRepository;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author paulo.rodrigues
 */
@Service
@Transactional
public class AddressService {

    private ModelMapper modelMapper;
    
    @Autowired
    private AddressRepository addressRepository;
    
    public Address findById(Long addressId) throws LibraryStoreBooksException {
        Address address = addressRepository.findById(addressId).orElse(null);

        if (address == null) {
            throw new LibraryStoreBooksException("Endereço não localizado para o id: " + addressId);
        }

        return address;
    }

    public AddressDTO create(Address address) throws LibraryStoreBooksException {       
        return toDTO(save(address));
    }

    public Address save(Address address) throws LibraryStoreBooksException {
        address.addressValidation();
        address.persistAt();

        return addressRepository.saveAndFlush(address);
    }

    public AddressDTO edit(Long addressId, AddressDTO addressDetail) throws LibraryStoreBooksException {
        Address addressToEdit = findById(addressId);

        addressToEdit = modelMapper.map(addressDetail, Address.class);

        return toDTO(save(addressToEdit));
    }

    public void erase(Long addressId) throws LibraryStoreBooksException {
        Address addressToDelete = findById(addressId);

        addressRepository.delete(addressToDelete);
    }
    
    public AddressDTO toDTO (Address address) {
        if(address == null){
            return null;
        }
        
        return AddressDTO.builder()
                .id(address.getId())
                .fmtAddress(address.formatAddress())
                .build();
    }    
    
}
