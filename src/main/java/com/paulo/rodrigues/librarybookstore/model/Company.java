/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
    @Index(name = "idx_name_company", columnList = "name"),
    @Index(name = "idx_cnpj", columnList = "cnpj")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Inheritance(
        strategy = InheritanceType.JOINED
)
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
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createAt;
    private String createBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateAt;
    private String updateBy;

    public void companyValidation() throws LibraryStoreBooksException {
        if (name.isEmpty()) {
            throw new LibraryStoreBooksException("Nome deve ser informado!");
        }
        String nuCnpj = FormatUtils.desformatCnpj(cnpj);
        if (nuCnpj.isEmpty()) {
            throw new LibraryStoreBooksException("CNPJ deve ser informado!");
        }
        if (!FormatUtils.isCNPJ(nuCnpj)) {
            throw new LibraryStoreBooksException("CNPJ inválido!");
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
}
