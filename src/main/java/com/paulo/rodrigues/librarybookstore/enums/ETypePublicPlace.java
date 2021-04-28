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
package com.paulo.rodrigues.librarybookstore.enums;

/**
 *
 * @author paulo.rodrigues
 */
public enum ETypePublicPlace {
    STREET(0, "Rua"),
    AVENUE(1, "Avenida"),
    SQUARE(2, "Praça"),
    SV(3, "Servidão"),
    BC(4, "Beco"),
    ROD(5, "Rodovia"),
    EST(6, "Estrada");
    
    int cod;
    String description;

    private ETypePublicPlace(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public ETypePublicPlace getBookCFormatByCod(int cod) {
        for (ETypePublicPlace value : ETypePublicPlace.values()) {
            if (cod == value.getCod()) {
                return value;
            }
        }
        return null;
    }
}
