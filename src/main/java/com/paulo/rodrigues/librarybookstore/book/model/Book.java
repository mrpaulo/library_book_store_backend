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
package com.paulo.rodrigues.librarybookstore.book.model;

import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookFormat;
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
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
 * {@code Book} class represents properties of book objects
 * in the Library Book Store system.
 * 
 * <br>
 * It's a entity on database and implements {@link java.io.Serializable Serializable} class.
 * 
 * <br>
 * 
 * @version 1
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
    @OneToMany(targetEntity = Author.class, mappedBy = "books", fetch = FetchType.EAGER)
    private Set<Author> authors;

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

    /**
     * Validate a object with the basic requirements.
     * 
     * If something isn't right it
     * @throws LibraryStoreBooksException 
     */
    public void validation() throws LibraryStoreBooksException {
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
        if (createBy == null) {
            setCreateAt(new Date());
            setCreateBy(FormatUtils.getUsernameLogged());
        } else {
            setUpdateAt(new Date());
            setUpdateBy(FormatUtils.getUsernameLogged());
        }
    }

}
