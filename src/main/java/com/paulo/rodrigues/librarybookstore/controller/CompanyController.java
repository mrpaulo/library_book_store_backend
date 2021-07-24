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
package com.paulo.rodrigues.librarybookstore.controller;

import com.paulo.rodrigues.librarybookstore.dto.CompanyDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.CompanyFilter;
import com.paulo.rodrigues.librarybookstore.model.Company;
import com.paulo.rodrigues.librarybookstore.service.CompanyService;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/all")
    public List<CompanyDTO> getAll() {
        List<CompanyDTO> companysList = companyService.findAll();

        return companysList.stream().sorted(Comparator.comparing(CompanyDTO::getName)).collect(Collectors.toList());
    }

    @GetMapping()
    public List<CompanyDTO> getAllPageble(@RequestBody CompanyFilter filter, HttpServletRequest req, HttpServletResponse res) {
        Pageable pageable = FormatUtils.getPageRequest(filter);
        Page<Company> result = companyService.findPageble(filter, pageable);
        res.addHeader("Total-Count", String.valueOf(result.getTotalElements()));

        return companyService.toListDTO(result.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable(value = "id") Long companyId) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(companyService.findById(companyId));
    }
    
    @GetMapping("/fetch/{name}")
    public ResponseEntity<List<CompanyDTO>> fetchByName(@PathVariable(value = "name") String name) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(companyService.findByName(name));
    }

    @PostMapping()
    public CompanyDTO create(@RequestBody CompanyDTO company) throws LibraryStoreBooksException {
        return companyService.create(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> update(@PathVariable(value = "id") Long companyId, @RequestBody CompanyDTO companyDetalhes) throws LibraryStoreBooksException {
        return ResponseEntity.ok(companyService.edit(companyId, companyDetalhes));
    }

    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable(value = "id") Long companyId) throws LibraryStoreBooksException {
        companyService.erase(companyId);
        Map<String, Long> response = new HashMap<>();
        response.put("id", companyId);

        return response;
    }
}
