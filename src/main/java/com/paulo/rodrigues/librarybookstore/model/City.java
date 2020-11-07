/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author paulo.rodrigues
 */
@Entity
@Table(indexes = {
    @Index(name = "idx_name_city", columnList = "name"),
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class City implements Serializable{
    @SequenceGenerator(name = "SEQ_CITY", allocationSize = 1, sequenceName = "city_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CITY")
    @Id
    private long id;
    
    @NotNull
    @Column(length = 100)
    private String name;
        
    @NotNull
    @OneToOne
    @JoinColumn(name = "STATE_ID", referencedColumnName = "ID")
    private StateCountry state;
    
    @NotNull
    @OneToOne
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")    
    private Country country;
    
    @Column(length = 10)
    private String ibgeCode;
}
