/*
 * Copyright (C) 2021 paulo.rodrigues
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
package com.paulo.rodrigues.librarybookstore.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paulo.rodrigues.librarybookstore.address.model.Address;
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author paulo.rodrigues
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name="lbs_user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
        
    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String name;
   
    @NotNull
    @Column(unique = true, nullable=true, length = ConstantsUtil.MAX_SIZE_NAME)
    private String username;
    
    @Column(unique = true, nullable=true, length = ConstantsUtil.MAX_SIZE_CPF)
    private String cpf;
    
    @Column(unique = true, nullable=true, length = ConstantsUtil.MAX_SIZE_NAME)
    private String email;
    
//    @JsonIgnore
    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String password;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private Address address;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthdate;
    
    @Column(length = 1)
    private String sex;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    private List<Role> roles;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createAt;
    private String createBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateAt;
    private String updateBy;

    public User() {
    }

    public User(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }
    public User(User user) {
        super();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.id = user.getId();
    }
    public User(String name, String email, String password, List<Role> roles) {
        super();
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }
    

    public void validation() throws LibraryStoreBooksException {
        if (!FormatUtils.isEmptyOrNull(name) && name.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("AUTHOR_NAME_NOT_INFORMED"));
        }                             
        if (!FormatUtils.isEmptyOrNull(sex) && (sex.length() > 1 || (!sex.equals("M") && !sex.equals("F") && !sex.equals("O")))) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("AUTHOR_SEX_INVALID"));
        }
        if (!FormatUtils.isEmptyOrNull(email) && email.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("AUTHOR_DESCRIPTION_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        } 
    }

    public void persistAt() {
        if (createAt == null) {
            setCreateAt(new Date());
            setCreateBy(FormatUtils.getUsernameLogged());
        } else {
            setUpdateAt(new Date());
            setUpdateBy(FormatUtils.getUsernameLogged());
        }
        
    }
}
