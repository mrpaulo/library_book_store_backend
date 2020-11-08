/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.service;

import com.paulo.rodrigues.librarybookstore.dto.AddressDTO;
import com.paulo.rodrigues.librarybookstore.dto.CompanyDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.CompanyFilter;
import com.paulo.rodrigues.librarybookstore.model.Company;
import com.paulo.rodrigues.librarybookstore.repository.AddressRepository;
import com.paulo.rodrigues.librarybookstore.repository.CompanyRepository;
import java.util.ArrayList;
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

    private ModelMapper modelMapper;    
    
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    public List<CompanyDTO> findAll() {
        return toListDTO(companyRepository.findAll());
    }

    public Page<Company> findPageble(CompanyFilter filter, Pageable pageable) {
        return companyRepository.findPageble(
                filter.getId(),
                filter.getName(),
                filter.getCnpj(),
                filter.getStartDate(),
                filter.getFinalDate(),
                pageable);
    }

    public Company findById(Long companyId) throws LibraryStoreBooksException {
        Company company = companyRepository.findById(companyId).orElse(null);

        if (company == null) {
            throw new LibraryStoreBooksException("Empresa não localizada para o id: " + companyId);
        }

        return company;
    }

    public CompanyDTO create(CompanyDTO dto) throws LibraryStoreBooksException {
        Company company = fromDTO(dto);
        return toDTO(save(company));
    }

    public Company save(Company company) throws LibraryStoreBooksException {
        company.companyValidation();
        company.persistAt();

        return companyRepository.saveAndFlush(company);
    }

    public CompanyDTO edit(Long companyId, CompanyDTO companyDetail) throws LibraryStoreBooksException {
        Company companyToEdit = findById(companyId);

        companyToEdit = modelMapper.map(companyDetail, Company.class);

        return toDTO(save(companyToEdit));
    }

    public void erase(Long companyId) throws LibraryStoreBooksException {
        Company companyToDelete = findById(companyId);

        companyRepository.delete(companyToDelete);
    }

    public CompanyDTO toDTO(Company company) {
        
        return CompanyDTO.builder()
                .id(0)
                .name(company.getName())
                .cnpj(company.getCnpj())
                .createDate(company.getCreateDate())
                .adress(addressService.toDTO(company.getAdress()))
                .build();
                
    }

    public Company fromDTO(CompanyDTO dto) throws LibraryStoreBooksException {
        return Company.builder()
                .name(dto.getName())
                .cnpj(dto.getCnpj())
                .adress(dto.getAdress() != null ? addressService.findById(dto.getAdress().getId()) : null)
                .build();
    }

    public List<CompanyDTO> toListDTO(List<Company> companies) {
        return companies.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
