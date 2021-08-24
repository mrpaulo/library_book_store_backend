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
package com.paulo.rodrigues.librarybookstore.book;

/**
 *
 * @author paulo.rodrigues
 */
public enum EBookCondition {
    USED(0, "Usado"),
    NEW(1, "Novo"),
    COLLECTABLE(2, "Colecionável");

    int cod;
    String description;

    private EBookCondition(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public String getName() {
        return this.name();
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

    public EBookCondition getBookConditionByCod(int cod) {
        for (EBookCondition value : EBookCondition.values()) {
            if (cod == value.getCod()) {
                return value;
            }
        }
        return null;
    }
}
