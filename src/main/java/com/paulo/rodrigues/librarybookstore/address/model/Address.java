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
import com.paulo.rodrigues.librarybookstore.utils.InvalidRequestException;
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.paulo.rodrigues.librarybookstore.utils.FormatUtils.printUpdateControl;
import static com.paulo.rodrigues.librarybookstore.utils.FormatUtils.removeLastComma;

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
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "CITY_ADDRESS"))
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
    private String referentialPoint;

    @Transient
    private String fmtAddress;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createAt;
    private String createBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateAt;
    private String updateBy;

    public String formatAddress() {
        String formattedAddress = "";
        if (city != null) {
            formattedAddress = city.getName();
            if (city.getState() != null) {
                formattedAddress = formattedAddress + " - " + city.getState().getName();

                if (city.getState().getCountry() != null) {
                    formattedAddress = formattedAddress + " - " + city.getState().getCountry().getName();
                }
            }
        }
        if(!FormatUtils.isEmpty(number)){
            formattedAddress = number + ". " + formattedAddress;
        }
        if(!FormatUtils.isEmpty(name)){
            formattedAddress = name + ", " + formattedAddress;
        }
        if(logradouro != null && !FormatUtils.isEmpty(logradouro.getDescription())){
            formattedAddress = logradouro.getDescription() + " " + formattedAddress;
        }
        return formattedAddress;
    }

    public void addressValidation() throws InvalidRequestException {
        if (FormatUtils.isEmpty(name)) {
            throw new InvalidRequestException(MessageUtil.getMessage("ADDRESS_NAME_NOT_INFORMED"));
        }
        if (name.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new InvalidRequestException(MessageUtil.getMessage("ADDRESS_NAME_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }
        if (!FormatUtils.isEmptyOrNull(number) && number.length() > ConstantsUtil.MAX_SIZE_ADDRESS_NUMBER) {
            throw new InvalidRequestException(MessageUtil.getMessage("ADDRESS_NUMBER_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_NUMBER + ""));
        }
        if (!FormatUtils.isEmptyOrNull(cep) && cep.length() > ConstantsUtil.MAX_SIZE_ADDRESS_CEP) {
            throw new InvalidRequestException(MessageUtil.getMessage("ADDRESS_CEP_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_CEP + ""));
        }
        if (!FormatUtils.isEmptyOrNull(zipCode) && zipCode.length() > ConstantsUtil.MAX_SIZE_ADDRESS_ZIPCODE) {
            throw new InvalidRequestException(MessageUtil.getMessage("ADDRESS_ZIPCODE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_ZIPCODE + ""));
        }
        if (!FormatUtils.isEmptyOrNull(neighborhood) && neighborhood.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new InvalidRequestException(MessageUtil.getMessage("ADDRESS_NEIGHBORHOOD_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }
        if (!FormatUtils.isEmptyOrNull(coordination) && coordination.length() > ConstantsUtil.MAX_SIZE_ADDRESS_COORDINATION) {
            throw new InvalidRequestException(MessageUtil.getMessage("ADDRESS_COORDINATION_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_ADDRESS_COORDINATION + ""));
        }
        if (!FormatUtils.isEmptyOrNull(referentialPoint) && referentialPoint.length() > ConstantsUtil.MAX_SIZE_SHORT_TEXT) {
            throw new InvalidRequestException(MessageUtil.getMessage("ADDRESS_REFERENTIAL_POINT_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_SHORT_TEXT + ""));
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Address{");
        sb.append("id='").append(id).append('\'').append(", ");
        if (logradouro != null) {
            sb.append("logradouro='").append(logradouro).append('\'').append(", ");
        }
        if (city != null) {
            sb.append("city={id:'").append(city.getId()).append('\'')
                    .append(", name:'").append(city.getName()).append('\'')
                    .append("}, ");
        }
        if (name != null && !name.isEmpty()) {
            sb.append("name='").append(name).append('\'').append(", ");
        }
        if (number != null && !number.isEmpty()) {
            sb.append("number='").append(number).append('\'').append(", ");
        }
        if (cep != null && !cep.isEmpty()) {
            sb.append("cep='").append(cep).append('\'').append(", ");
        }
        if (zipCode != null && !zipCode.isEmpty()) {
            sb.append("zipCode='").append(zipCode).append('\'').append(", ");
        }
        if (neighborhood != null && !neighborhood.isEmpty()) {
            sb.append("neighborhood='").append(neighborhood).append('\'').append(", ");
        }
        if (coordination != null && !coordination.isEmpty()) {
            sb.append("coordination='").append(coordination).append('\'').append(", ");
        }
        if (referentialPoint != null && !referentialPoint.isEmpty()) {
            sb.append("referentialPoint='").append(referentialPoint).append('\'').append(", ");
        }
        if (fmtAddress != null && !fmtAddress.isEmpty()) {
            sb.append("fmtAddress='").append(fmtAddress).append('\'').append(", ");
        }
        sb = printUpdateControl(sb, createAt, createBy, updateAt, updateBy);
        sb = removeLastComma(sb);
        sb.append('}');
        return sb.toString();
    }
}
