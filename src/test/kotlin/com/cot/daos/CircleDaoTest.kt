package com.cot.daos

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.dbunit.JdbcDatabaseTester
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.operation.DatabaseOperation
import org.h2.tools.RunScript
import org.junit.*
import org.junit.runner.RunWith
import java.io.File
import kotlin.test.assertTrue

@RunWith(VertxUnitRunner::class)
class CircleDaoTest {
    private val vertx = Vertx.vertx()
    private lateinit var circleDao : CircleDao

    companion object {
        private val JDBC_DRIVER = "org.h2.Driver"
        private val JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
        private val USER = "sa"
        private val PASSWORD = ""
        private lateinit var dbTester : JdbcDatabaseTester

        @BeforeClass
        @JvmStatic fun setup() {
            RunScript.execute(JDBC_URL, USER, PASSWORD, "src/main/resources/db/schema.sql", Charsets.UTF_8, false);
            preloadDB(readDataSet())
        }

        @AfterClass
        @JvmStatic fun teardown() {
            dbTester.onTearDown()
        }

        private fun preloadDB(dataSet: IDataSet) {
            dbTester = JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD)
            dbTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT)
            dbTester.setDataSet(dataSet)
            dbTester.onSetup()
        }

        private fun readDataSet() : IDataSet {
            return FlatXmlDataSetBuilder().build(File("src/test/resources/dataset.xml"));
        }
    }

    @Before
    fun setUp(context: TestContext) {

        val options = JsonObject()
                        .put("url", JDBC_URL)
                        .put("driver_class",JDBC_DRIVER)
                        .put("user", USER)
                        .put("password", PASSWORD)

        val client = JDBCClient.createShared(vertx, options)
        circleDao = CircleDao(client)
    }

    @After
    fun tearDown() {
        dbTester.onTearDown()
    }

    @Test
    fun testGetCircleThatExists(context: TestContext) {
        val async = context.async()

        circleDao.getCircle("1").setHandler { result ->

            context.assertTrue(result.succeeded())

            val circles = result.result().toJson().getJsonArray("rows")
            context.assertEquals(1, circles.size())
            context.assertEquals("first circle", circles.getJsonObject(0).getString("NAME"))
            async.complete()
        }
    }

    @Test
    fun testGetCircleThatDoesNotExist(context: TestContext) {
        val async = context.async()

        circleDao.getCircle("2").setHandler { result ->

            assertTrue(result.succeeded())

            val circles = result.result().toJson().getJsonArray("rows")
            context.assertEquals(0, circles.size())
            async.complete()
        }
    }
}

