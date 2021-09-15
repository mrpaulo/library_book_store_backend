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
package com.paulo.rodrigues.librarybookstore.book.repository;

import com.paulo.rodrigues.librarybookstore.book.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.publisher.dto.PublisherDTO;
import com.paulo.rodrigues.librarybookstore.author.dto.AuthorDTO;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.book.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.book.model.Book;
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;
import com.paulo.rodrigues.librarybookstore.author.model.Author;
import com.paulo.rodrigues.librarybookstore.book.model.BookSubject;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookCondition;
import com.paulo.rodrigues.librarybookstore.book.enums.EBookFormat;
import com.paulo.rodrigues.librarybookstore.book.model.Language;
import com.paulo.rodrigues.librarybookstore.publisher.service.PublisherService;
import com.paulo.rodrigues.librarybookstore.utils.DateUtils;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

/**
 *
 * @author paulo.rodrigues
 */
@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private static final Logger LOGGER = Logger.getLogger(BookRepositoryCustomImpl.class.getName());

    @PersistenceContext
    EntityManager em;

    @Autowired
    private PublisherService companyService;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private BookSubjectRepository bookSubjectRepository;

    @Override
    @SuppressWarnings("unchecked")
    public PagedResult<BookDTO> findPageble(BookFilter filter) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(getSqlQuery(filter));

            if (!FormatUtils.isEmptyOrNull(filter.getSortColumn())) {
                String[] arrOrdernation = filter.getSortColumn().split(";");
                List<String> listOrderBy = new ArrayList<String>();
                for (int i = 0; i < arrOrdernation.length; i++) {
                    String[] ordernation = arrOrdernation[i].split(",");
                    listOrderBy.add(" " + ordernation[0]);
                }
                sql.append(" ORDER BY ");
                sql.append(String.join(" , ", listOrderBy));
            } else {
                sql.append(" ORDER BY title ASC");
            }

            Query query = em.createNativeQuery(sql.toString());

            StringBuilder sqlCount = new StringBuilder();
            sqlCount.append("SELECT COUNT(*) as total FROM ( ");
            sqlCount.append(getSqlQuery(filter));
            sqlCount.append(") as a ");
            Query queryCount = em.createNativeQuery(sqlCount.toString());
            queryCount = buildFilterParameter(queryCount, filter);

            query.setFirstResult(filter.getOffset());
            query.setMaxResults(filter.getRowsPerPage());
            query = buildFilterParameter(query, filter);

            List<BookDTO> list = buildListBooksDTO(query.getResultList());

            Integer total = ((BigInteger) queryCount.getSingleResult()).intValue();
            Integer totalPaginas = 0;
            if (total != null && total > 0) {
                totalPaginas = (int) Math.ceil(total / filter.getRowsPerPage());
                if (totalPaginas == 0) {
                    totalPaginas = 1;
                }
            } else if (total == null) {
                total = 0;
            }

            return new PagedResult<BookDTO>(list, total, totalPaginas);

        } catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
            return null;

        }
    }

    private StringBuilder getSqlQuery(BookFilter filter) {

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT b.id  ");
        sql.append(" , b.title  ");
        sql.append(" , b.subtitle  ");
        sql.append(" , b.language_id ");
        sql.append(" , b.publisher_id ");
        sql.append(" , b.subject_id ");
        sql.append(" , b.review ");
        sql.append(" , b.link ");
        sql.append(" , b.format ");
        sql.append(" , b.condition ");
        sql.append(" , b.edition ");
        sql.append(" , b.publish_date ");
        sql.append(" , b.rating ");
        sql.append(" , b.length ");
        sql.append(" FROM BOOK b  ");
        sql.append(" LEFT JOIN  PUBLISHER pu ON pu.id = b.publisher_id ");
        sql.append(" WHERE 1 = 1 ");

        buildFilter(sql, filter);

        return sql;
    }

    private StringBuilder buildFilter(StringBuilder sql, BookFilter filter) {

        if (!FormatUtils.isEmpty(filter.getId())) {
            sql.append(" AND b.id = :bookId");            
        }
        if (!FormatUtils.isEmpty(filter.getTitle())) {
            sql.append(" AND LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) ");            
        }
        if (!FormatUtils.isEmpty(filter.getAuthor())) {            
            sql.append("AND LOWER(:authorName) IN ");
            sql.append("(SELECT LOWER(p.name) FROM AUTHOR p "
                    + "INNER JOIN author_books ab ON p.id = ab.author_id "
                    + "WHERE ab.books_id = b.id)");
        }
        if (!FormatUtils.isEmpty(filter.getPublisher())) {            
            sql.append("AND LOWER(pu.name) LIKE LOWER(CONCAT('%', :publisherName, '%')) ");            
        }
        if (!FormatUtils.isEmpty(filter.getSubjectName())) {
            sql.append(" AND b.subject_id =");
            sql.append("(SELECT id FROM BOOK_SUBJECT WHERE LOWER(name) = LOWER(:subjectName)) ");           
        }
        if (filter.getStartDate() != null && filter.getFinalDate() != null) {
            sql.append(" AND b.publish_date BETWEEN :startDate AND :finalDate ");
        }
        return sql;
    }

    private Query buildFilterParameter(Query query, BookFilter filter) {

        if (!FormatUtils.isEmpty(filter.getId())) {
            query.setParameter("bookId", filter.getId());
        }
        if (!FormatUtils.isEmpty(filter.getTitle())) {
            query.setParameter("title", filter.getTitle());
        }
        if (!FormatUtils.isEmpty(filter.getAuthor())) {
            query.setParameter("authorName", filter.getAuthor());
        }
        if (!FormatUtils.isEmpty(filter.getPublisher())) {
            query.setParameter("publisherName", filter.getPublisher());
        }
        if (!FormatUtils.isEmpty(filter.getSubjectName())) {
            query.setParameter("subjectName", filter.getSubjectName());
        }
        if (filter.getStartDate() != null && filter.getFinalDate() != null) {
           query.setParameter("startDate", filter.getStartDate());
           query.setParameter("finalDate", filter.getFinalDate());
        }
        return query;
    }

    private List<BookDTO> buildListBooksDTO(List<Object[]> resultList) {
        List<BookDTO> books = new ArrayList<>();

        resultList.forEach(b -> {
            try {
                books.add(
                        BookDTO.builder()
                                .id(((BigInteger) b[0]).longValue())
                                .title((String) b[1])
                                .subtitle(b[2] != null ? (String) b[2] : null)
                                .languageName(getLanguageName(b[3]))
                                .publisher(getCompanyDTO(b[4]))
                                .subjectName(getSubjectName(b[5]))
                                .review(b[6] != null ? (String) b[6] : null)
                                .link(b[7] != null ? (String) b[7] : null)
                                .format(b[8] != null ? EBookFormat.valueOf((String) b[8]) : null)
                                .condition(b[9] != null ? EBookCondition.valueOf((String) b[9]) : null)
                                .edition(b[10] != null ? (Integer) b[10] : null)
                                .publishDate(b[11] != null ? (Date) b[11] : null)
                                .rating(b[12] != null ? (Double) b[12] : null)
                                .length((b[13] != null ? (Integer) b[13] : null))
                                .authors(getListAuthorsDTOByBookId(((BigInteger) b[0]).longValue()))
                                .build());
            } catch (LibraryStoreBooksException ex) {
                Logger.getLogger(BookRepositoryCustomImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );

        return books;

    }

    private List<Book> buildListBooks(List<Object[]> resultList) {
        List<Book> books = new ArrayList<>();

        resultList.forEach(b -> {
            try {
                books.add(
                        Book.builder()
                                .id(((BigInteger) b[0]).longValue())
                                .title((String) b[1])
                                .subtitle(b[2] != null ? (String) b[2] : null)
                                .language(getLanguage(b[3]))
                                .publisher(getPublisher(b[4]))
                                .subject(getSubject(b[5]))
                                .review(b[6] != null ? (String) b[6] : null)
                                .link(b[7] != null ? (String) b[7] : null)
                                .format(b[8] != null ? EBookFormat.valueOf((String) b[8]) : null)
                                .condition(b[9] != null ? EBookCondition.valueOf((String) b[9]) : null)
                                .edition(b[10] != null ? (Integer) b[10] : null)
                                .publishDate(b[11] != null ? (Date) b[11] : null)
                                .rating(b[12] != null ? (Double) b[12] : null)
                                .length((b[13] != null ? (Integer) b[13] : null))
                                .authors(getListAuthorsByBookId(((BigInteger) b[0]).longValue()))
                                .build());
            } catch (LibraryStoreBooksException ex) {
                Logger.getLogger(BookRepositoryCustomImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );

        return books;

    }

    @Override
    public List<AuthorDTO> getListAuthorsDTOByBookId(Long bookId) throws LibraryStoreBooksException {
        return buildListAuthorsDTO(selectQueryAuthorsByBookId(bookId).getResultList());
    }

    private Set<Author> getListAuthorsByBookId(Long idBook) throws LibraryStoreBooksException {
        return buildListAuthors(selectQueryAuthorsByBookId(idBook).getResultList());
    }

    private Query selectQueryAuthorsByBookId(Long bookId) throws LibraryStoreBooksException {
        try {
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT p.id  ");
            sql.append(" , p.name  ");
            sql.append(" , p.birthdate ");
            sql.append(" , p.sex ");
            sql.append(" , p.email ");
            sql.append(" , a.description ");
            sql.append(" FROM author p ");
            sql.append(" INNER JOIN author a ON a.id = p.id ");
            sql.append(" INNER JOIN author_books ab ON ab.author_id = a.id ");
            sql.append(" WHERE AB.books_id = :bookId ");

            Query query = em.createNativeQuery(sql.toString());
            query.setParameter("bookId", bookId);

            return query;
        } catch (Exception e) {
            return null;
        }
    }

    private List<AuthorDTO> buildListAuthorsDTO(List<Object[]> resultList) {
        List<AuthorDTO> authors = new ArrayList<>();

        resultList.forEach(b -> {
            authors.add(AuthorDTO.builder()
                    .id(((BigInteger) b[0]).longValue())
                    .name((String) b[1])
                    .birthdate(b[2] != null ? (Date) b[2] : null)
                    .sex(b[3] != null ? (String) b[3] : null)
                    .email(b[4] != null ? (String) b[4] : null)
                    .description(b[5] != null ? (String) b[5] : null)
                    .build());
        }
        );

        return authors;

    }

    private Set<Author> buildListAuthors(List<Object[]> resultList) {
        Set<Author> authors = new HashSet<>();

        resultList.forEach(b -> {
            authors.add(Author.builder()
                    .id(((BigInteger) b[0]).longValue())
                    .name((String) b[1])
                    .birthdate(b[2] != null ? (Date) b[2] : null)
                    .sex(b[3] != null ? (String) b[3] : null)
                    .email(b[4] != null ? (String) b[4] : null)
                    .build());

        }
        );

        return authors;

    }

    private PublisherDTO getCompanyDTO(Object idObj) throws LibraryStoreBooksException {
        if (idObj == null) {
            return null;
        }

        Long id = ((BigInteger) idObj).longValue();
        return id != null ? companyService.toDTO(companyService.findById(id)) : null;
    }

    private Publisher getPublisher(Object idObj) throws LibraryStoreBooksException {
        PublisherDTO companyDTO = getCompanyDTO(idObj);
        return companyDTO != null ? companyService.fromDTO(companyDTO) : null;
    }

    private String getLanguageName(Object idObj) throws LibraryStoreBooksException {

        Language language = getLanguage(idObj);

        return language != null ? language.getName() : null;
    }

    private Language getLanguage(Object idObj) throws LibraryStoreBooksException {
        if (idObj == null) {
            return null;
        }

        Long id = ((BigInteger) idObj).longValue();
        return languageRepository.findById(id).orElse(null);
    }

    private String getSubjectName(Object idObj) throws LibraryStoreBooksException {

        BookSubject subject = getSubject(idObj);

        return subject != null ? subject.getName() : null;
    }

    private BookSubject getSubject(Object idObj) throws LibraryStoreBooksException {
        if (idObj == null) {
            return null;
        }

        Long id = ((BigInteger) idObj).longValue();
        BookSubject subject = bookSubjectRepository.findById(id).orElse(null);

        return subject != null ? subject : null;
    }

    @Override
    public List<Book> getBooksFromAuthor(String authorName) {
        try {

            StringBuilder sql = new StringBuilder();
            BookFilter filter = new BookFilter(null, authorName, null, null, null);
            sql.append(getSqlQuery(filter));

            Query query = em.createNativeQuery(sql.toString());
            query = buildFilterParameter(query, filter);

            List<Book> list = buildListBooks(query.getResultList());

            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteBookAuthor(long authorId, long bookId) {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append(" DELETE FROM author_books ");
            sql.append("   WHERE author_id = :authorId ");
            sql.append("  AND books_id = :bookId ");

            Query query = em.createNativeQuery(sql.toString());
            query.setParameter("authorId", authorId);
            query.setParameter("bookId", bookId);

            query.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}