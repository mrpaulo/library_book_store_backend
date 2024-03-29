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
package com.paulo.rodrigues.librarybookstore.authentication.filter;

import com.paulo.rodrigues.librarybookstore.utils.PageableFilter;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author paulo.rodrigues
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserFilter extends PageableFilter {

    private String cpf;    
    private Date birthdate;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserFilter{");
        if (cpf != null && !cpf.isEmpty()) {
            sb.append("cpf='").append(cpf).append('\'').append(", ");
        }
        if (birthdate != null) {
            sb.append("birthdate='").append(birthdate).append('\'').append(", ");
        }
        return toStringSuper(sb).toString();
    }
}
