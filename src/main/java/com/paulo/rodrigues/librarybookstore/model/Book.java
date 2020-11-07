/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import com.paulo.rodrigues.librarybookstore.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.enums.EBookFormat;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import static com.paulo.rodrigues.librarybookstore.utils.FormatUtils.isCPF;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
    @Index(name = "idx_title", columnList = "title"),})
@Getter
@Setter
@Builder
public class Book implements Serializable {

    @SequenceGenerator(name = "SEQ_BOOK", allocationSize = 1, sequenceName = "book_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOOK")
    @Id
    private long id;
    
    @NotNull
    @Column(length = 100)
    private String title;
    
    @NotNull
    //@OneToMany(targetEntity = Author.class, mappedBy = "books", fetch = FetchType.LAZY)
    @OneToMany
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private List<Author> authors;
    
    @OneToOne
    @JoinColumn(name = "LANGUAGE_ID", referencedColumnName = "ID")
    private Language language;
    
    @OneToOne
    @JoinColumn(name = "PUBLISHER_ID", referencedColumnName = "ID")
    private Publisher publisher;
    
    @OneToOne
    @JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID")
    private BookSubject subject;
    
    @Column(length = 100)
    private String subtitle;
    
    @Column(length = 500)
    private String review;
    
    @Column(length = 100)
    private String link;
    
    @Enumerated(EnumType.STRING)
    private EBookFormat format;
    
    @Enumerated(EnumType.STRING)
    private EBookCondition condition;
    
    private int edition;
    private Date publishDate;   
    private Double rating;    
    private int length;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createAt;
    private String createBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateAt;
    private String updateBy;

    public void bookValidation() throws LibraryStoreBooksException {
        if (FormatUtils.isEmpty(title)) {
            throw new LibraryStoreBooksException("Título deve ser informado!");
        }
        if (title.length() > 100) {
            throw new LibraryStoreBooksException("Título com tamanho maior que 100 caracteres!");
        }
        if (!FormatUtils.isEmptyOrNull(subtitle) && subtitle.length() > 100) {
            throw new LibraryStoreBooksException("Subtítulo com tamanho maior que 100 caracteres!");
        }
        if (!FormatUtils.isEmptyOrNull(link) && link.length() > 100) {
            throw new LibraryStoreBooksException("Link com tamanho maior que 100 caracteres!");
        }
        if (!FormatUtils.isEmptyOrNull(review) && review.length() > 500) {
            throw new LibraryStoreBooksException("Resumo com tamanho maior que 500 caracteres!");
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
