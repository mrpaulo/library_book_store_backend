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
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.dto.AddressDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.model.Address;
import com.paulo.rodrigues.librarybookstore.repository.AddressRepository;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
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

}
