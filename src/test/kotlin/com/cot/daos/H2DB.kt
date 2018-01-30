package com.cot.daos

import org.h2.tools.RunScript

object H2DB {
    val JDBC_DRIVER = "org.h2.Driver"
    val JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
    val USER = "sa"
    val PASSWORD = ""

    init {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/main/resources/db/schema.sql", Charsets.UTF_8, false);
        System.out.println("setup complete")
    }
}