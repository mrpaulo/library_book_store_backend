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
package com.paulo.rodrigues.librarybookstore.address.model;

import com.paulo.rodrigues.librarybookstore.address.enums.ETypePublicPlace;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
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

    private static final long serialVersionUID = 1L;

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
    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String name;

    @Column(length = ConstantsUtil.MAX_SIZE_ADDRESS_NUMBER)
    private String number;

    @Column(length = ConstantsUtil.MAX_SIZE_ADDRESS_CEP)
    private String cep;

    @Column(length = ConstantsUtil.MAX_SIZE_ADDRESS_ZIPCODE)
    private String zipCode;

    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String neighborhood;

    @Column(length = ConstantsUtil.MAX_SIZE_ADDRESS_COORDINATION)
    private String coordination;

    @Column(length = ConstantsUtil.MAX_SIZE_SHORT_TEXT)
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

    public void addressValidation() throws LibraryStoreBooksException {
        if (FormatUtils.isEmpty(name)) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("ADDRESS_NAME_NOT_INFORMED"));
        }
        if (name.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("ADDRESS_NAME_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }
        if (!FormatUtils.isEmptyOrNull(number) && number.length() > ConstantsUtil.MAX_SIZE_ADDRESS_NUMBER) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("ADDRESS_NUMBER_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_NUMBER + ""));
        }
        if (!FormatUtils.isEmptyOrNull(cep) && cep.length() > ConstantsUtil.MAX_SIZE_ADDRESS_CEP) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("ADDRESS_CEP_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_CEP + ""));
        }
        if (!FormatUtils.isEmptyOrNull(zipCode) && zipCode.length() > ConstantsUtil.MAX_SIZE_ADDRESS_ZIPCODE) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("ADDRESS_ZIPCODE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_ZIPCODE + ""));
        }
        if (!FormatUtils.isEmptyOrNull(neighborhood) && neighborhood.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("ADDRESS_NEIGHBORHOOD_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }
        if (!FormatUtils.isEmptyOrNull(coordination) && coordination.length() > ConstantsUtil.MAX_SIZE_ADDRESS_COORDINATION) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("ADDRESS_COORDINATION_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_COORDINATION + ""));
        }
        if (!FormatUtils.isEmptyOrNull(referencialPoint) && referencialPoint.length() > ConstantsUtil.MAX_SIZE_SHORT_TEXT) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("ADDRESS_REFERENCIALPOINT_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_SHORT_TEXT + ""));
        }
    }

    public void persistAt() {
        if (createBy == null) {
            setCreateAt(new Date());
            setCreateBy(FormatUtils.getUsernameLogged());
        } else {
            setUpdateAt(new Date());
            setUpdateBy(FormatUtils.getUsernameLogged());
        }
    }

    public Address orElse(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
