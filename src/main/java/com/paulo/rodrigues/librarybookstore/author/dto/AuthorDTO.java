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
package com.paulo.rodrigues.librarybookstore.author.dto;

import com.paulo.rodrigues.librarybookstore.address.dto.CountryDTO;
import com.paulo.rodrigues.librarybookstore.address.dto.CityDTO;
import com.paulo.rodrigues.librarybookstore.address.dto.AddressDTO;
import com.paulo.rodrigues.librarybookstore.address.dto.StateDTO;
import com.paulo.rodrigues.librarybookstore.author.model.Author;

import java.time.LocalDate;
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
public class AuthorDTO {
    private long id;
    private String name;    
    private LocalDate birthdate;
    private String sex;
    private String email;
    private CityDTO birthCity;
    private StateDTO birthState;
    private CountryDTO birthCountry;
    private AddressDTO address;  
    private String description;

}
