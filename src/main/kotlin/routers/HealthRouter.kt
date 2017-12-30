package routers

import controllers.HealthController
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

object HealthRouter {
    val router = Router.router(Vertx.vertx())

    init {
        val controller = HealthController()

        //set up circle routes
        router.get("/").handler(controller::ping)
    }
}