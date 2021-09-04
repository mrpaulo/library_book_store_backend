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
    
    @GetMapping("/logradouros")
    public ResponseEntity<List<Map<String, String>>> getETypePublicPlace() throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(addressService.getETypePublicPlace());
    }
    
    @GetMapping("/{country}/{state}/cities")
    public ResponseEntity<List<City>> getAllCities(@PathVariable(value = "country") Long country, @PathVariable(value = "state") Long state) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(addressService.getAllCities(country, state));
    }
    
    @GetMapping("/{country}/states")
    public ResponseEntity<List<StateCountry>> getAllStates(@PathVariable(value = "country") Long country) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(addressService.getAllStates(country));
    }
    
    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(addressService.getAllCountries());
    }
}
