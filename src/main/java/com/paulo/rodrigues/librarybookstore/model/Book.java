/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.model;

import com.paulo.rodrigues.librarybookstore.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.enums.EBookFormat;
import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
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
    @Index(name = "idx_title", columnList = "title"),})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "SEQ_BOOK", allocationSize = 1, sequenceName = "book_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOOK")
    @Id
    private long id;

    @NotNull
    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String title;

    @NotNull
    @OneToMany(targetEntity = Author.class, mappedBy = "books", fetch = FetchType.LAZY)
    private List<Author> authors;

    @NotNull
    @OneToOne
    @JoinColumn(name = "PUBLISHER_ID", referencedColumnName = "ID")
    private Publisher publisher;

    @OneToOne
    @JoinColumn(name = "LANGUAGE_ID", referencedColumnName = "ID")
    private Language language;

    @OneToOne
    @JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID")
    private BookSubject subject;

    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String subtitle;

    @Column(length = ConstantsUtil.MAX_SIZE_LONG_TEXT)
    private String review;

    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String link;

    @Enumerated(EnumType.STRING)
    private EBookFormat format;

    @Enumerated(EnumType.STRING)
    private EBookCondition condition;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date publishDate;

    private int edition;
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
            throw new LibraryStoreBooksException(MessageUtil.getMessage("BOOK_TITLE_NOT_INFORMED"));
        }
        if (title.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("BOOK_TITLE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }
        if (!FormatUtils.isEmptyOrNull(subtitle) && subtitle.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("BOOK_SUBTITLE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }
        if (!FormatUtils.isEmptyOrNull(link) && link.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("BOOK_LINK_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }
        if (!FormatUtils.isEmptyOrNull(review) && review.length() > ConstantsUtil.MAX_SIZE_LONG_TEXT) {
            throw new LibraryStoreBooksException(MessageUtil.getMessage("BOOK_REVIEW_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_LONG_TEXT + ""));

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
