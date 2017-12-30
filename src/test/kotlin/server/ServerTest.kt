package server

import org.junit.Test
import org.junit.Before
import org.junit.After
import org.junit.runner.RunWith
import io.vertx.core.Vertx
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner


@RunWith(VertxUnitRunner::class)
class ServerTest {
    private val vertx = Vertx.vertx()

    @Before
    fun setUp(context: TestContext) {
        vertx.deployVerticle(Server::class.java.name, context.asyncAssertSuccess())
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
                context.assertTrue(body.toString().contains("OK"))
                async.complete()
            }
        }
    }
}