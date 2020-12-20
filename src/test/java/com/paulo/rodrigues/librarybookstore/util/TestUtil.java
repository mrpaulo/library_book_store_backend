/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
