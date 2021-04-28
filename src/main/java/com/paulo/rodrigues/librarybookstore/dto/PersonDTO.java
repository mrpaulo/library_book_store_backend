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
