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
package com.paulo.rodrigues.librarybookstore.utils;

/**
 *
 * @author paulo.rodrigues
 */
public class ConstantsUtil {

    public static final int MAX_SIZE_NAME = 100;
    public static final int MAX_SIZE_SHORT_TEXT = 200;
    public static final int MAX_SIZE_LONG_TEXT = 600;
    
    public static final int MAX_SIZE_ADDRESS_NUMBER = 9;
    public static final int MAX_SIZE_ADDRESS_CEP = 8;
    public static final int MAX_SIZE_ADDRESS_ZIPCODE = 12;
    public static final int MAX_SIZE_ADDRESS_COORDINATION = 20;
    
    public static final int MAX_SIZE_CNPJ = 14;
    public static final int MAX_SIZE_CPF = 11;

    public static final String ADDRESSES_V1_BASE_API = "/api/v1/addresses";
    public static final String AUTHORS_V1_BASE_API = "/api/v1/authors";
    public static final String BOOKS_V1_BASE_API = "/api/v1/books";
    public static final String PUBLISHERS_V1_BASE_API = "/api/v1/publishers";
    public static final String USERS_V1_BASE_API = "/api/v1/users";
    public static final String GET_ALL_PATH = "/all";
    public static final String FIND_PAGEABLE_PATH = "/fetch";
    public static final String GET_BY_ID_PATH = "/{id}";
    public static final String GET_BY_NAME_PATH = "/fetch/{name}";
    public static final String UPDATE_PATH = "/{id}";
    public static final String DELETE_PATH = "/{id}";
    public static final String SAFE_DELETE_PATH = "/safe/{id}";
    public static final String GET_SUBJECTS_PATH = "/subjects";
    public static final String GET_FORMATS_PATH = "/formats";
    public static final String GET_CONDITIONS_PATH = "/conditions";
    public static final String GET_LANGUAGES_PATH = "/languages";
    public static final String GET_TYPE_PUBLIC_PLACE_PATH = "/logradouros";
    public static final String GET_CITIES_PATH = "/{country}/{state}/cities";
    public static final String GET_STATES_PATH = "/{country}/states";
    public static final String GET_COUNTRIES_PATH = "/countries";
    public static final String UPDATE_USER_PATH = "/update";
    public static final String GET_ROLES_PATH = "/roles";
}
