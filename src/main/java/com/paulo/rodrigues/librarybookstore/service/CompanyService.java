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

import com.paulo.rodrigues.librarybookstore.dto.CompanyDTO;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.filter.CompanyFilter;
import com.paulo.rodrigues.librarybookstore.model.Company;
import com.paulo.rodrigues.librarybookstore.model.Publisher;
import com.paulo.rodrigues.librarybookstore.repository.CompanyRepository;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
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
            throw new LibraryStoreBooksException(MessageUtil.getMessage("COMPANY_NOT_FOUND") + " ID: " + companyId);
        }

        return company;
    }
    
    public List<CompanyDTO> findByName(String name) {
        return toListDTO(companyRepository.findByName(name));
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
                .id(company.getId())
                .name(company.getName())
                .cnpj(company.getCnpj())
                .createDate(company.getCreateDate())
                .address(addressService.toDTO(company.getAddress()))
                .build();
                
    }

    public Company fromDTO(CompanyDTO dto) throws LibraryStoreBooksException {
        return Company.builder()
                .name(dto.getName())
                .cnpj(dto.getCnpj())
                .createDate(dto.getCreateDate())
                .address(dto.getAddress() != null ? addressService.findById(dto.getAddress().getId()) : null)
                .build();
    }

    public List<CompanyDTO> toListDTO(List<Company> companies) {
        return companies.stream().map(b -> toDTO(b)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public CompanyDTO getCompanyDTOFromPublisher(Publisher publisher) {
        return toDTO((Company) publisher);
    }

    public Publisher getPublisherFromDTO(CompanyDTO dto) throws LibraryStoreBooksException {
        Publisher result = new Publisher();
        
        result.setId(dto.getId());
        result.setName(dto.getName());
        result.setCnpj(dto.getCnpj());
        result.setDescription(dto.getDescription());
        result.setCreateDate(dto.getCreateDate());
        result.setAddress(dto.getAddress() != null ? addressService.findById(dto.getAddress().getId()) : null);
        
        return result;
    }
}
