/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.CompanyFilter;
import com.paulo.rodrigues.librarybookstore.model.Company;
import com.paulo.rodrigues.librarybookstore.repository.CompanyRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
/**
 *
 * @author paulo.rodrigues
 */
@Service
@Transactional
public class CompanyService {
    
    @Autowired
    CompanyRepository companyRepository;
    
    private ModelMapper modelMapper;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Page<Company> findPageble(CompanyFilter filter, Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Company findById(Long companyId) throws LibraryStoreBooksException {
        Company company = companyRepository.findById(companyId).orElse(null);
        
        if(company == null) {
            throw new LibraryStoreBooksException("Empresa não localizada");
        }
        
        return company;
    }

    public Company save(Company company) throws LibraryStoreBooksException {
        company.companyValidation();
        company.persistAt();
        
        return companyRepository.saveAndFlush(company);        
    }

    public Company edit(Long companyId, Company companyDetail) throws LibraryStoreBooksException {
        Company companyToEdit = findById(companyId);
        
        companyToEdit = modelMapper.map(companyDetail, Company.class);
        
        return save(companyToEdit);
    }

    public void erase(Long companyId) throws LibraryStoreBooksException {
        Company companyToDelete = findById(companyId);
        
        
        
        companyRepository.delete(companyToDelete);
    }
        
}
