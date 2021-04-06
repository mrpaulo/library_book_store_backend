/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
    
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "SEQ_COMPANY", allocationSize = 1, sequenceName = "company_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMPANY")
    @Id
    private long id;

    @NotNull
    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String name;

    @NotNull
    @Column(unique = true, length = ConstantsUtil.MAX_SIZE_CNPJ)
    private String cnpj;
    
    @OneToOne
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private Address address;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createDate;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createAt;
    private String createBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateAt;
    private String updateBy;

    public void companyValidation() throws LibraryStoreBooksException {
        if (FormatUtils.isEmpty(name)) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("COMPANY_NAME_NOT_INFORMED"));
        }
        if (name.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("COMPANY_NAME_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }        
        String nuCnpj = FormatUtils.desformatCnpj(cnpj);
        if (nuCnpj.isEmpty()) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("COMPANY_CNPJ_NOT_INFORMED"));
        }
        if (!FormatUtils.isCNPJ(nuCnpj)) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("COMPANY_CNPJ_INVALID"));
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
