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
package com.paulo.rodrigues.librarybookstore.util;

import java.sql.Connection;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.h2.tools.RunScript;

/**
 *
 * @author paulo.rodrigues
 */
public class TestUtil {
     private static String SCRIPT_PREFIX = "scripts/";

    public static void runScript(String script) {
        Connection currentConnection = StaticBeanUtil.getBeanByClass(Connection.class);

        try {
            RunScript.execute(currentConnection, new FileReader(Thread.currentThread().getContextClassLoader().getResource(SCRIPT_PREFIX + script).getFile()));
        } catch (FileNotFoundException | SQLException e) {
            System.out.println("SCRIPT ERROR: " + script);
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }
    
}
