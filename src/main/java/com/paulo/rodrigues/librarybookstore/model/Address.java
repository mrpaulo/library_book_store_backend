/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import com.paulo.rodrigues.librarybookstore.enums.ETypePublicPlace;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
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
    @Index(name = "idx_name_address", columnList = "name"),})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Address implements Serializable {

    @SequenceGenerator(name = "SEQ_ADDRESS", allocationSize = 1, sequenceName = "address_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADDRESS")
    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    private ETypePublicPlace logradouro;

    @NotNull
    @OneToOne
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID")
    private City city;

    @NotNull
    @Column(length = 100)
    private String name;

    @Column(length = 9)
    private String number;

    @Column(length = 8)
    private String cep;
    
    @Column(length = 12)
    private String zipCode;
    
    @Column(length = 100)
    private String neighborhood;
    
    @Column(length = 20)
    private String coordination;
    
    @Column(length = 200)
    private String referencialPoint;
    
    @Transient
    private String fmtAddress;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createAt;
    private String createBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateAt;
    private String updateBy;
    
    public String formatAddress() {
        return "";
    }

    public void addressValidation() throws LibraryStoreBooksException{
        if (FormatUtils.isEmpty(name)) {
            throw new LibraryStoreBooksException("Logradouro deve ser informado!");
        }
        if (name.length() > 100) {
            throw new LibraryStoreBooksException("Logradouro com tamanho maior que 100 caracteres!");
        }
        if (!FormatUtils.isEmptyOrNull(number) && number.length() > 9) {
            throw new LibraryStoreBooksException("Subtítulo com tamanho maior que 9 caracteres!");
        }
        if (!FormatUtils.isEmptyOrNull(cep) && cep.length() > 8) {
            throw new LibraryStoreBooksException("Subtítulo com tamanho maior que 8 caracteres!");
        }
        if (!FormatUtils.isEmptyOrNull(zipCode) && zipCode.length() > 9) {
            throw new LibraryStoreBooksException("Subtítulo com tamanho maior que 9 caracteres!");
        }
        if (!FormatUtils.isEmptyOrNull(neighborhood) && neighborhood.length() > 100) {
            throw new LibraryStoreBooksException("Subtítulo com tamanho maior que 100 caracteres!");
        }
        if (!FormatUtils.isEmptyOrNull(coordination) && coordination.length() > 20) {
            throw new LibraryStoreBooksException("Subtítulo com tamanho maior que 20 caracteres!");
        }
        if (!FormatUtils.isEmptyOrNull(referencialPoint) && referencialPoint.length() > 200) {
            throw new LibraryStoreBooksException("Subtítulo com tamanho maior que 200 caracteres!");
        }
    }

    public void persistAt() {
        if (updateAt == null) {
            setCreateAt(new Date());
            setCreateBy(FormatUtils.getCdUserLogged());
        } else {
            setUpdateAt(new Date());
            setUpdateBy(FormatUtils.getCdUserLogged());
        }
    }

    public Address orElse(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
