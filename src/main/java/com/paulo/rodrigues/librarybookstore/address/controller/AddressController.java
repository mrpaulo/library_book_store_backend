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
package com.paulo.rodrigues.librarybookstore.address.controller;

import com.paulo.rodrigues.librarybookstore.address.dto.AddressDTO;
import com.paulo.rodrigues.librarybookstore.address.service.AddressService;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.address.model.Address;
import com.paulo.rodrigues.librarybookstore.address.model.City;
import com.paulo.rodrigues.librarybookstore.address.model.Country;
import com.paulo.rodrigues.librarybookstore.address.model.StateCountry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paulo.rodrigues.librarybookstore.utils.NotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import static com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil.*;
/**
 *
 * @author paulo.rodrigues
 */
@Log4j2
@RestController
@CrossOrigin(origins = {"*"})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok success"),
        @ApiResponse(code = 201, message = "Address created"),
        @ApiResponse(code = 400, message = "Validation Error Response", response = LibraryStoreBooksException.class),
        @ApiResponse(code = 401, message = "Full Authentication Required or Invalid access token"),
        @ApiResponse(code = 403, message = "Insufficient scope"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Something Unexpected Happened"),})
@RequestMapping(ADDRESSES_V1_BASE_API)
public class AddressController {
    
    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "Get the address by id",
            notes = "It returns the address given an Id")
    @GetMapping(GET_BY_ID_PATH)
    public ResponseEntity<Address> getById(@PathVariable(value = "id") Long addressId) throws LibraryStoreBooksException, NotFoundException {
        try {
            return ResponseEntity.ok().body(addressService.findById(addressId));
        } catch (Exception e) {
            log.error("Exception on getById addressId={}, message={}", addressId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @GetMapping(GET_BY_NAME_PATH)
    public ResponseEntity<List<Address>> getByName(@PathVariable(value = "name") String addressName) {
        try {
            return ResponseEntity.ok().body(addressService.findByName(addressName));
        } catch (Exception e) {
            log.error("Exception on getByName addressName={}, message={}", addressName, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PostMapping()
    public ResponseEntity<AddressDTO> create(@RequestBody Address address) throws LibraryStoreBooksException {
        try {
            return new ResponseEntity<>(addressService.create(address), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception on create address={}, message={}", address, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PutMapping(UPDATE_PATH)
    public ResponseEntity<AddressDTO> update(@PathVariable(value = "id") Long addressId, @RequestBody AddressDTO addressDTO) throws LibraryStoreBooksException, NotFoundException {
        try {
            return ResponseEntity.ok(addressService.edit(addressId, addressDTO));
        } catch (Exception e) {
            log.error("Exception on update addressId={}, message={}", addressId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @DeleteMapping(DELETE_PATH)
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long addressId) throws LibraryStoreBooksException, NotFoundException {
        try {
            addressService.delete(addressId);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            log.error("Exception on delete addressId={}, message={}", addressId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping(GET_TYPE_PUBLIC_PLACE_PATH)
    public ResponseEntity<List<Map<String, String>>> getETypePublicPlace() {
        try {
            return ResponseEntity.ok().body(addressService.getETypePublicPlace());
        } catch (Exception e) {
            log.error("Exception on getETypePublicPlace message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping(GET_CITIES_PATH)
    public ResponseEntity<List<City>> getAllCities(@PathVariable(value = "country") Long countryId, @PathVariable(value = "state") Long stateId) {
        try {
            return ResponseEntity.ok().body(addressService.getAllCities(countryId, stateId));
        } catch (Exception e) {
            log.error("Exception on getAllCities countryId={}, stateId={}, message={}", countryId, stateId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping(GET_STATES_PATH)
    public ResponseEntity<List<StateCountry>> getAllStates(@PathVariable(value = "country") Long countryId) {
        try {
            return ResponseEntity.ok().body(addressService.getAllStates(countryId));
        } catch (Exception e) {
            log.error("Exception on getAllStates countryId={}, message={}", countryId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping(GET_COUNTRIES_PATH)
    public ResponseEntity<List<Country>> getAllCountries() {
        try {
            return ResponseEntity.ok().body(addressService.getAllCountries());
        } catch (Exception e) {
            log.error("Exception on getAllCountries message={}", e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
}
