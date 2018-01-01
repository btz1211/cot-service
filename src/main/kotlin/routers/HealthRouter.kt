package routers

import controllers.HealthController
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class HealthRouter (vertx: Vertx) {
    val router = Router.router(vertx)

    init {
        val controller = HealthController()

        //set up circle routes
        router.get("/").handler(controller::ping)
    }
}