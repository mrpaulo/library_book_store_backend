/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import com.paulo.rodrigues.librarybookstore.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.enums.EBookFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author paulo.rodrigues
 */
@Entity
@Table(indexes = {
    @Index(name = "idx_title", columnList = "title"),
    @Index(name = "idx_authors", columnList = "authors")
})
@Getter
@Setter
@Builder
public class Book implements Serializable {

    @SequenceGenerator(name = "SEQ_BOOK", allocationSize = 1, sequenceName = "book_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOOK")
    @Id
    private long id;
    @Column(length = 100)
    @NotNull
    private String title;
    @NotNull
    private List<Author> authors;
    @Column(length = 100)
    private String subtitle;
    private BookSubject subject;
    private String edition;
    @Column(length = 500)
    private String review;
    private List<String> keywords;
    private Date publishDate;
    private Language language;
    private Double rating;
    private Publisher publisher;
    private int length;
    @Column(length = 100)
    private String link;
    private EBookFormat format;
    private EBookCondition condition;

}
