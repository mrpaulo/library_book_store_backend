/*
 * Copyright (C) 2021 paulo.rodrigues
 * Profile: <https://github.com/mrpaulo>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.paulo.rodrigues.librarybookstore.address.service;

import com.paulo.rodrigues.librarybookstore.address.dto.AddressDTO;
import com.paulo.rodrigues.librarybookstore.address.enums.ETypePublicPlace;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.address.model.Address;
import com.paulo.rodrigues.librarybookstore.address.model.City;
import com.paulo.rodrigues.librarybookstore.address.model.Country;
import com.paulo.rodrigues.librarybookstore.address.model.StateCountry;
import com.paulo.rodrigues.librarybookstore.address.repository.AddressRepository;
import com.paulo.rodrigues.librarybookstore.address.repository.CityRepository;
import com.paulo.rodrigues.librarybookstore.address.repository.CountryRepository;
import com.paulo.rodrigues.librarybookstore.address.repository.StateCountryRepository;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paulo.rodrigues.librarybookstore.author.repository.AuthorRepository;
import com.paulo.rodrigues.librarybookstore.publisher.repository.PublisherRepository;

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
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private StateCountryRepository stateCountryRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private AuthorRepository personRepository;
    
    @Autowired
    private PublisherRepository companyRepository;

    public Address findById(Long addressId) throws LibraryStoreBooksException {
        Address address = addressRepository.findById(addressId).orElse(null);

        if (address == null) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("ADDRESS_NOT_FOUND") + " ID: " + addressId);
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
        
        personRepository.deleteAddressReference(addressId);
        companyRepository.deleteAddressReference(addressId);
        addressRepository.delete(addressToDelete);
    }

    public AddressDTO toDTO(Address address) {
        if (address == null) {
            return null;
        }

        return AddressDTO.builder()
                .id(address.getId())
                .fmtAddress(address.formatAddress())
                .build();
    }
    
    public List<Map<String, String>> getETypePublicPlace() {
        return Stream.of(ETypePublicPlace.values()).map(temp -> {
            Map<String, String> obj = new HashMap<>();
            obj.put("value", temp.getName());
            obj.put("label", temp.getDescription());
            return obj;
        }).collect(Collectors.toList());
    }

     public List<Country> getAllCountries(){
        return countryRepository.findAll();
    }
     
    public List<StateCountry> getAllStates(Long coutryId){
        Country country = countryRepository.findById(coutryId).orElse(null);
        if(country == null){
            return null;
        }
        return stateCountryRepository.findByCountry(country);
    }
    
    public List<City> getAllCities(Long coutryId, Long stateId){
        Country country = countryRepository.findById(coutryId).orElse(null);
        StateCountry state = stateCountryRepository.findById(stateId).orElse(null);
        if(country == null || state == null){
            return null;
        }
        return cityRepository.findByCountryAndState(country, state);
    }
}
