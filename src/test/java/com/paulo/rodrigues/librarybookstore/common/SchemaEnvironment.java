/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paulo.rodrigues.librarybookstore.common;

import com.paulo.rodrigues.librarybookstore.util.TestUtil;

/**
 *
 * @author paulo.rodrigues
 */
public class SchemaEnvironment {
    
    private static boolean schemaOk = false;

    public static void ensureSchemaIsScreated() {
        if (schemaOk) {
            return;
        }

        TestUtil.runScript("Schema.sql");
        TestUtil.runScript("Views.sql");
        schemaOk = true;
    }

    public void run() {
        ensureSchemaIsScreated();
    }
}
