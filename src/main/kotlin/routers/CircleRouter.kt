package routers

import controllers.CircleController
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

class CircleRouter(vertx: Vertx) {
    val router = Router.router(vertx)

    init {
        val controller = CircleController()

        //set up circle routes
        router.get("/:circleId").handler(controller::getCircle)
        router.get("/").handler(controller::getCircles)
    }
}