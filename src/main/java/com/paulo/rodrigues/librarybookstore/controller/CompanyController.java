/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.controller;


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
@RequestMapping("/api/v1/company")
public class CompanyController {
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping("/all")
    public List<Company> getAll() {
        List <Company> companysList = companyService.findAll();
        
        return companysList.stream().sorted(Comparator.comparing(Company::getName)).collect(Collectors.toList());
    }
    @GetMapping()
    public List<Company> getAllPageble(@RequestBody CompanyFilter filter, HttpServletRequest req, HttpServletResponse res) {
        Pageable pageable = FormatUtils.getPageRequest(filter);
        
        Page <Company> result = companyService.findPageble(filter, pageable);
        
        res.addHeader("Total-Count", String.valueOf(result.getTotalElements()));
        
        return result.getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable(value = "id") Long companyId) throws LibraryStoreBooksException {
        Company company = companyService.findById(companyId);
        
        return ResponseEntity.ok().body(company);
    }

    @PostMapping()
    public Company create(@RequestBody Company company) throws LibraryStoreBooksException {
        Company saveCompany = companyService.save(company);
        return companyService.findById(saveCompany.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable(value = "id") Long companyId, @RequestBody Company companyDetalhes) throws LibraryStoreBooksException {

        final Company updateCompany = companyService.edit(companyId, companyDetalhes);
        return ResponseEntity.ok(updateCompany);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long companyId) throws LibraryStoreBooksException {                
        companyService.erase(companyId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
