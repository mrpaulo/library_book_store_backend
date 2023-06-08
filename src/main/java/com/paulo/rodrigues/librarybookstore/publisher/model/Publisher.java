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
package com.paulo.rodrigues.librarybookstore.publisher.model;

import com.paulo.rodrigues.librarybookstore.address.model.Address;
import com.paulo.rodrigues.librarybookstore.utils.InvalidRequestException;
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
    @Index(name = "idx_name_publisher", columnList = "name"),
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
public class Publisher implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "SEQ_PUBLISHER", allocationSize = 1, sequenceName = "publisher_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PUBLISHER")
    @Id
    private long id;

    @NotNull
    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String name;

    @NotNull
    @Column(unique = true, length = ConstantsUtil.MAX_SIZE_CNPJ)
    private String cnpj;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "ADDRESS_PUBLISHER"))
    private Address address;
    
    private LocalDate foundationDate;
    
    @Column(length = ConstantsUtil.MAX_SIZE_LONG_TEXT)
    private String description;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createAt;
    private String createBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateAt;
    private String updateBy;

    public void validation() throws InvalidRequestException {
        if (FormatUtils.isEmpty(name)) {
            throw new InvalidRequestException(MessageUtil.getMessage("PUBLISHER_NAME_NOT_INFORMED"));
        }
        if (name.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new InvalidRequestException(MessageUtil.getMessage("PUBLISHER_NAME_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }        
        String nuCnpj = FormatUtils.desformatCnpj(cnpj);
        if (nuCnpj != null && nuCnpj.isEmpty()) {
            throw new InvalidRequestException(MessageUtil.getMessage("PUBLISHER_CNPJ_NOT_INFORMED"));
        }
        if (!FormatUtils.isCNPJ(nuCnpj)) {
            throw new InvalidRequestException(MessageUtil.getMessage("PUBLISHER_CNPJ_INVALID"));
        }
         if (!FormatUtils.isEmptyOrNull(description) && description.length() > ConstantsUtil.MAX_SIZE_LONG_TEXT) {
            throw new InvalidRequestException(MessageUtil.getMessage("PUBLISHER_DESCRIPTION_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_LONG_TEXT + ""));
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
        StringBuilder sb = new StringBuilder("Publisher{");
        sb.append("id='").append(id).append('\'').append(", ");
        if (name != null && !name.isEmpty()) {
            sb.append("name='").append(name).append('\'').append(", ");
        }
        if (cnpj != null && !cnpj.isEmpty()) {
            sb.append("cnpj='").append(cnpj).append('\'').append(", ");
        }
        if (address != null) {
            sb.append("address='").append(address).append('\'').append(", ");
        }
        if (foundationDate != null) {
            sb.append("foundationDate='").append(foundationDate).append('\'').append(", ");
        }
        if (description != null && !description.isEmpty()) {
            sb.append("description='").append(description).append('\'').append(", ");
        }
        sb = printUpdateControl(sb, createAt, createBy, updateAt, updateBy);
        sb = removeLastComma(sb);
        sb.append('}');
        return sb.toString();
    }
}
