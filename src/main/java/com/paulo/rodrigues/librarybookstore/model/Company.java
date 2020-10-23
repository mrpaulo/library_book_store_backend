/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import javax.persistence.Column;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author paulo.rodrigues
 */
    @Entity
    @Table(indexes = {
        @Index(name = "idx_name", columnList = "name"),
        @Index(name = "idx_cnpj", columnList = "cnpj")
    })
    @Getter
    @Setter
    @Builder
public class Company implements Serializable {
    
    @SequenceGenerator(name = "SEQ_COMPANY", allocationSize = 1, sequenceName = "company_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMPANY")
    @Id
    private long id;
    
    @Column(length = 100)
    @NotNull
    private String name;
        
    @Column(unique = true, length = 14)
    @NotNull    
    private String cnpj;
    private Address address;
    
}
