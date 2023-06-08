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

import com.paulo.rodrigues.librarybookstore.utils.InvalidRequestException;
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookFormat;
import com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.MessageUtil;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.paulo.rodrigues.librarybookstore.utils.FormatUtils.printUpdateControl;
import static com.paulo.rodrigues.librarybookstore.utils.FormatUtils.removeLastComma;

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

    @NotNull(message = "Authors are required")
    @Size(min = 1, message = "The Authors needs at least one item")
    @ManyToMany
    @JoinTable(name = "author_books",
            joinColumns = { @JoinColumn(name = "book_id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id") })
    private Set<Author> authors;

    @NotNull(message = "Publisher is required")
    @ManyToOne
    @JoinColumn(name = "PUBLISHER_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "PUBLISHER_BOOK"))
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "LANGUAGE_BOOK"))
    private Language language;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "SUBJECT_BOOK"))
    private BookSubject subject;

    @Column(length = ConstantsUtil.MAX_SIZE_NAME)
    private String subtitle;

    @Column(length = ConstantsUtil.MAX_SIZE_LONG_TEXT)
    private String review;

    @Column(length = ConstantsUtil.MAX_SIZE_SHORT_TEXT)
    private String link;

    @Enumerated(EnumType.STRING)
    private EBookFormat format;

    @Enumerated(EnumType.STRING)
    private EBookCondition condition;

    private LocalDate publishDate;

    private int edition;
    private Double rating;
    private int length;

    private boolean adultsOnly;

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
     * @throws InvalidRequestException 
     */
    public void validation() throws InvalidRequestException {
        if (FormatUtils.isEmpty(title)) {
            throw new InvalidRequestException(MessageUtil.getMessage("BOOK_TITLE_NOT_INFORMED"));
        }
        if (title.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new InvalidRequestException(MessageUtil.getMessage("BOOK_TITLE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }
        if (!FormatUtils.isEmptyOrNull(subtitle) && subtitle.length() > ConstantsUtil.MAX_SIZE_NAME) {
            throw new InvalidRequestException(MessageUtil.getMessage("BOOK_SUBTITLE_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_NAME + ""));
        }
        if (!FormatUtils.isEmptyOrNull(link) && link.length() > ConstantsUtil.MAX_SIZE_SHORT_TEXT) {
            throw new InvalidRequestException(MessageUtil.getMessage("BOOK_LINK_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_SHORT_TEXT + ""));
        }
        if (!FormatUtils.isEmptyOrNull(review) && review.length() > ConstantsUtil.MAX_SIZE_LONG_TEXT) {
            throw new InvalidRequestException(MessageUtil.getMessage("BOOK_REVIEW_OUT_OF_BOUND", ConstantsUtil.MAX_SIZE_LONG_TEXT + ""));

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
        StringBuilder sb = new StringBuilder("Book{");
        sb.append("id='").append(id).append('\'').append(", ");
        if (title != null && !title.isEmpty()) {
            sb.append("title='").append(title).append('\'').append(", ");
        }
        if (!authors.isEmpty()) {
            sb.append("authors=[");
            StringBuilder finalSb = sb;
            authors.forEach(a -> finalSb.append("{id:'").append(a.getId()).append('\'')
                    .append(", name:'").append(a.getName()).append('\'')
                    .append("}, "));
            sb.append("], ");
        }
        if (publisher != null) {
            sb.append("publisher={id:'").append(publisher.getId()).append('\'')
                    .append(", name:'").append(publisher.getName()).append('\'')
                    .append("}, ");
        }
        if (language != null) {
            sb.append("language={id:'").append(language.getId()).append('\'')
                    .append(", name:'").append(language.getName()).append('\'')
                    .append("}, ");
        }
        if (subject != null) {
            sb.append("subject={id:'").append(subject.getId()).append('\'')
                    .append(", name:'").append(subject.getName()).append('\'')
                    .append("}, ");
        }
        if (subtitle != null && !subtitle.isEmpty()) {
            sb.append("subtitle='").append(subtitle).append('\'').append(", ");
        }
        if (review != null && !review.isEmpty()) {
            sb.append("review='").append(review).append('\'').append(", ");
        }
        if (link != null && !link.isEmpty()) {
            sb.append("link='").append(link).append('\'').append(", ");
        }
        if (format != null) {
            sb.append("format='").append(format).append('\'').append(", ");
        }
        if (condition != null) {
            sb.append("condition='").append(condition).append('\'').append(", ");
        }
        if (publishDate != null) {
            sb.append("publishDate='").append(publishDate).append('\'').append(", ");
        }
        sb.append("edition='").append(edition).append('\'').append(", ");
        sb.append("rating='").append(rating).append('\'').append(", ");
        sb.append("length='").append(length).append('\'').append(", ");
        sb.append("adultsOnly='").append(adultsOnly).append('\'').append(", ");
        sb = printUpdateControl(sb, createAt, createBy, updateAt, updateBy);
        sb = removeLastComma(sb);
        sb.append('}');
        return sb.toString();
    }
}
