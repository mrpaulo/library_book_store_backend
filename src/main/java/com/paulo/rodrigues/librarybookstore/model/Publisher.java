/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author paulo.rodrigues
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Publisher extends Company {
    
    private static final long serialVersionUID = 1L;
    
    @Column(length = 500)
    private String description;
}
