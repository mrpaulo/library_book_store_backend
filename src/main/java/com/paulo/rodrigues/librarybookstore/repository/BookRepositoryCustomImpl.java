/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.dto.BookDTO;
import com.paulo.rodrigues.librarybookstore.filter.BookFilter;
import com.paulo.rodrigues.librarybookstore.model.Book;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import com.paulo.rodrigues.librarybookstore.utils.PagedResult;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
                    listOrderBy.add(" " + ordernation[0] + " " + ordernation[1] + " ");
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

            query.setFirstResult(filter.getOffset());
            query.setMaxResults(filter.getRowsPerPage());

            List<BookDTO> list = montaListaObjetos(query.getResultList());

            Integer total = (Integer) queryCount.getSingleResult();
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
        sql.append("    b.title  ");
        sql.append("    b.sutitle  ");
        sql.append(" FROM BOOK b  ");
        sql.append(" INNER JOIN  AUTHOR a ON a.id = b.author_id ");
        sql.append(" INNER JOIN  PUBLISHER pu ON pu.id = b.publisher_id ");
        sql.append(" WHERE 1 = 1 ");

        montaFiltro(sql, filter);

        return sql;
    }

    private StringBuilder montaFiltro(StringBuilder sql, BookFilter filter) {

        if (!FormatUtils.isEmpty(filter.getId())) {
            sql.append(" AND b.id = ");
            sql.append(filter.getId());
        }
        if (!FormatUtils.isEmpty(filter.getTitle())) {
            sql.append(" AND b.title like CONCAT('%',");
            sql.append(filter.getTitle());
            sql.append(",'%') ");
        }
        if (!FormatUtils.isEmpty(filter.getAuthor())) {
            sql.append(" AND ");
            sql.append(filter.getAuthor());
            sql.append(" IN (SELECT p.name from PERSON p WHERE p.id = b.author_id) ");
        }
        if (!FormatUtils.isEmpty(filter.getPublisher())) {
            sql.append(" AND ");
            sql.append(filter.getPublisher());
            sql.append(" IN (SELECT c.name FROM COMPANY c WHERE c.id = b.publisher_id ) ");
        }
        if (filter.getStartDate() != null && filter.getFinalDate() != null) {
            sql.append(" AND b.publishDate BETWEEN ");
            sql.append(filter.getStartDate());
            sql.append(" AND ");
            sql.append(filter.getFinalDate());

        }
        return sql;
    }

    private List<BookDTO> montaListaObjetos(List<Object[]> resultList) {
        List<BookDTO> books = new ArrayList<>();

        resultList.forEach(b -> books.add(
                BookDTO.builder()
                        .id(((BigDecimal) b[0]).longValue())
                        .title((String) b[1])
                        .subtitle((String) b[2])
                        .authors((String) b[3])
                        .language((String) b[4])
                        .publisher((String) b[5])
                        .subject((String) b[6])                        
                        .review((String) b[7])
                        .link((String) b[8])
                        .format((String) b[9])
                        .condition((String) b[10])
                        .edition(((BigDecimal) b[11]).intValue())
                        .publishDate((String) b[12])
                        .rating(((BigDecimal) b[13]).doubleValue())
                        .length(((BigDecimal) b[14]).intValue())
                        .build())
        );

        return books;

    }

}
