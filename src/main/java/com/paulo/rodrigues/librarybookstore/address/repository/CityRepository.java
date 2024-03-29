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
package com.paulo.rodrigues.librarybookstore.address.repository;

import com.paulo.rodrigues.librarybookstore.address.model.City;
import com.paulo.rodrigues.librarybookstore.address.model.Country;
import com.paulo.rodrigues.librarybookstore.address.model.StateCountry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author paulo.rodrigues
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    public City getByName(String name);

    public City getById(long id);

    public List<City> findByCountryAndState(Country country, StateCountry state);

}
