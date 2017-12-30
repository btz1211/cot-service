package routers

import io.vertx.core.Vertx.vertx
import io.vertx.ext.web.Router

object MainRouter {
    val router = Router.router(vertx())

    init {
        router.mountSubRouter("/ping", HealthRouter.router)
        router.mountSubRouter("/circles", CircleRouter.router)
    }
}