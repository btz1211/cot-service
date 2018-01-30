package com.cot.server

import io.vertx.core.DeploymentOptions
import org.junit.Test
import org.junit.Before
import org.junit.After
import org.junit.runner.RunWith
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner
import kotlin.test.assertTrue


@RunWith(VertxUnitRunner::class)
class ServerTest {
    private val vertx = Vertx.vertx()

    @Before
    fun setUp(context: TestContext) {
        val options = DeploymentOptions()
                .setConfig(JsonObject().put("db_config", JsonObject()
                        .put("url", "test")
                        .put("driver_class", "org.hsqldb.jdbcDriver")
                        .put("max_pool_size", 30)))

        vertx.deployVerticle(Server::class.java.name, options, context.asyncAssertSuccess())
    }

    @After
    fun teardown(context: TestContext) {
        vertx.close(context.asyncAssertSuccess())
    }

    @Test
    fun ServerStartTest(context: TestContext) {
        val async = context.async()

        vertx.createHttpClient().getNow(8080, "localhost", "/ping"
        ) { response ->
            response.handler { body ->
                assertTrue(body.toString().contains("OK"))
                async.complete()
            }
        }
    }
}