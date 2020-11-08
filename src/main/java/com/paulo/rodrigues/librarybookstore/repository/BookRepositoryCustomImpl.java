/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.model.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Repository;

/**
 *
 * @author paulo.rodrigues
 */
@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Override
    public Page<Book> findPageble(String title, String author, String publisher, Date startDate, Date finalDate, Pageable page) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT b.id  ");
        sql.append("    b.title  ");
        sql.append("    b.sutitle  ");
        sql.append(" FROM BOOK b  ");
        sql.append(" INNER JOIN  AUTHOR a ON a.id = b.author_id ");
        sql.append(" INNER JOIN  PUBLISHER pu ON pu.id = b.publisher_id ");
        sql.append(" WHERE (:id IS NULL OR :id = '' OR b.id = :id)  ");
        sql.append(" AND (:title IS NULL OR :title = '' OR b.title like CONCAT('%',:title,'%')) ");
        sql.append(" AND (:author IS NULL OR :author = '' OR :author IN (SELECT p.name from PERSON p WHERE p.id = b.author_id) ) ");
        sql.append(" AND (:publisher IS NULL OR :publisher = '' OR :publisher IN (SELECT c.name FROM COMPANY c WHERE c.id = b.publisher_id ) ) ");
        sql.append(" AND ((:startDate IS NULL AND :finalDate IS NULL) OR (b.publishDate BETWEEN :startDate AND :finalDate)) ");

        try {
            Query query = em.createNativeQuery(sql.toString());
            query.setParameter("title", title);
            query.setParameter("author", author);
            query.setParameter("publisher", publisher);
            query.setParameter("startDate", startDate);
            query.setParameter("finalDate", finalDate);

            List<Object> rs = query.getResultList();

            List<Book> listBooks = new ArrayList<>();

            for (Object r : rs) {
                listBooks.add(montaObjeto(r));
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Book montaObjeto(Object r) {
        return Book.builder()
                .id(0)
                .title("")
                .subtitle("")
                .build();

    }

}
