package routers

import com.cot.conf.AppConfig
import com.cot.daos.CircleDao
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler

class MainRouter (vertx: Vertx){
    val router = Router.router(vertx)

    init {
        val dbConfig = vertx.orCreateContext.config().getJsonObject("db_config")
        val jdbc = JDBCClient.createShared(vertx, dbConfig)

        router.route().handler(StaticHandler.create());
        router.mountSubRouter("/ping", HealthRouter(vertx).router)
        router.mountSubRouter("/circles", CircleRouter(vertx, CircleDao(jdbc)).router)
    }
}