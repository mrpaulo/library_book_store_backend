/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.repository;

import com.paulo.rodrigues.librarybookstore.model.Book;
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
        sql.append(" SELECT TOP 1 u.cdUsuario from EADSUSUARIO u  ");
        sql.append(" INNER JOIN  EADSUSUARIOEXPORTAUTOMATICA perf on perf.cdUsuario = u.cdUsuario ");
        sql.append(" WHERE perf.tpExportacao = :tpExportacao ");
        sql.append(" AND EXISTS ( ");
        sql.append(" 	SELECT 1 FROM esfoDocumento d ");
        sql.append(" 	JOIN ectoTitulo t ON d.nuTitulo = t.nuTitulo ");
        sql.append(" 	LEFT JOIN vsfoliqempenhoitem li on d.nuseqdocumento = li.nuseqdocumento and d.nutitulo = li.nutitulo ");
        sql.append(" 	JOIN esfoEmpenho e ON li.nuAnoOrc = e.nuAnoOrc AND li.nuSeqEmpenho = e.nuSeqEmpenho ");
        sql.append(" 	JOIN esfoDotacao dot ON E.nuAnoOrc = dot.nuAnoOrc AND E.nuseqDotacao = dot.nuseqDotacao ");
        sql.append(" 	where t.nuTitulo  = :nuTitulo ");
        sql.append(" 	AND d.nuSeqDocumento = :nuSeqDocumento ");
        sql.append(" 	AND dot.cdUnidGestora = (SELECT csu.cdUnidGestora FROM esegUsrSetorSist aux ");
        sql.append(" 					  INNER JOIN esfoConfigSetorUg csu ON csu.cdOrgaoSetor = aux.cdOrgaoSetor ");
        sql.append(" 	  				  WHERE aux.cdusuario = u.cdUsuario AND aux.cdsistema = 529 )  ");
        sql.append(" 		) ");
        sql.append(" ORDER BY perf.dtCriacao asc ");

        try {
            Query query = em.createNativeQuery(sql.toString());
            query.setParameter("title", title);
            query.setParameter("author", author);
            query.setParameter("publisher", publisher);
            query.setParameter("startDate", startDate);
            query.setParameter("finalDate", finalDate);
            

            List<Object> rs = query.getResultList();
            
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    

}
