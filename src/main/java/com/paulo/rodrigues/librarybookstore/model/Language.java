/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
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
    @Index(name = "idx_name", columnList = "name"),
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class Language {
    @SequenceGenerator(name = "SEQ_LANGUAGE", allocationSize = 1, sequenceName = "language_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LANGUAGE")
    @Id
    private long id;
    
    @Column(length = 100)
    @NotNull
    private String name;
}
