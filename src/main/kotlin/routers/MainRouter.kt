package routers

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler

class MainRouter (vertx: Vertx){
    val router = Router.router(vertx)

    init {
        router.route().handler(StaticHandler.create());
        router.mountSubRouter("/ping", HealthRouter(vertx).router)
        router.mountSubRouter("/circles", CircleRouter(vertx).router)
    }
}