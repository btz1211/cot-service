package com.cot.daos

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.Before
import org.junit.runner.RunWith
import org.dbunit.JdbcDatabaseTester
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.operation.DatabaseOperation
import org.junit.After
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(VertxUnitRunner::class)
class CircleDaoTest {
    private val vertx = Vertx.vertx()
    private lateinit var circleDao : CircleDao
    private lateinit var dbTester : JdbcDatabaseTester

    @Before
    fun setUp(context: TestContext) {
        preloadDB(readDataSet())

        val options = JsonObject()
                        .put("url", H2DB.JDBC_URL)
                        .put("driver_class", H2DB.JDBC_DRIVER)
                        .put("user", H2DB.USER)
                        .put("password", H2DB.PASSWORD)

        val client = JDBCClient.createShared(vertx, options)
        circleDao = CircleDao(client)
    }

    @After
    fun tearDown() {
        dbTester.onTearDown()
    }

    @Test
    fun testGetCircle(context: TestContext) {
        val async = context.async()

        circleDao.getCircle("1", {result ->

            assertTrue(result.succeeded())

            val circles = result.result().toJson().getJsonArray("rows")
            assertEquals(1, circles.size())
            assertEquals("first circle", circles.getJsonObject(0).getString("NAME"))

            async.complete()
        })
    }

    private fun preloadDB(dataSet: IDataSet) {
        dbTester = JdbcDatabaseTester(H2DB.JDBC_DRIVER, H2DB.JDBC_URL, H2DB.USER, H2DB.PASSWORD)
        dbTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT)
        dbTester.setDataSet(dataSet)
        dbTester.onSetup()
    }

    private fun readDataSet() : IDataSet {
        return FlatXmlDataSetBuilder().build(File("src/test/resources/dataset.xml"));
    }
}

