/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.dto;

import com.paulo.rodrigues.librarybookstore.model.Person;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author paulo.rodrigues
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {
    private long id;
    private String name;
    private String cpf;
    private Date birthdate;
    private String sex;
    private String email;
    private String birthCity;
    private String birthCountry;
    private AddressDTO address;  
    private String description;

    public PersonDTO(long id, String name, String cpf, Date birthdate, String sex, String email, String description) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.birthdate = birthdate;
        this.sex = sex;
        this.email = email;
        this.description = description;
    }
    
    public PersonDTO(Person p) {
        this.id = p.getId();
        this.name = p.getName();
        this.cpf = p.getCpf();
        this.birthdate = p.getBirthdate();
        this.sex = p.getSex();
        this.email = p.getEmail();
        
    }
    
    
}
