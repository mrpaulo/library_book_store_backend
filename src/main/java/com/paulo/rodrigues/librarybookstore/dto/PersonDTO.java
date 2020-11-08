/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.dto;

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
    private String adress;  
    private String description;
}
