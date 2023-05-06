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
package com.paulo.rodrigues.librarybookstore.publisher.repository;

import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author paulo.rodrigues
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long>{
    
    public Publisher findByCnpj (String cnpj);
    
    @Query("SELECT c "
            + " FROM Publisher c "
            + " WHERE (:name IS NULL OR :name = '' OR LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%'))) "
            + "")
    public List<Publisher> findByName (@Param("name") String name);

    @Query("SELECT c "
            + " FROM Publisher c "
            + " WHERE (:id IS NULL OR c.id = :id) "
            + " AND (:name IS NULL OR :name = '' OR LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%'))) "
            + " AND (:cnpj IS NULL OR :cnpj = '' OR c.cnpj LIKE CONCAT('%',:cnpj,'%')) "
            + " AND ((coalesce(:startDate, null) is null AND coalesce(:finalDate, null) is null) OR (c.foundationDate BETWEEN :startDate AND :finalDate)) "
            + "")
    public Page<Publisher> findPageble(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("cnpj") String cnpj,                        
            @Param("startDate") LocalDate startDate,
            @Param("finalDate") LocalDate finalDate,
            Pageable page);

    @Modifying
    @Query(value = "UPDATE "
            + " Publisher "
            + " SET  address_id = null"
            + " WHERE address_id = :addressId ",
            nativeQuery = true)
    public void deleteAddressReference(Long addressId);
    
}
