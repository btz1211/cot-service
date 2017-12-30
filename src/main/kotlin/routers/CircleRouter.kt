package routers

import controllers.CircleController
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

object CircleRouter {
    val router = Router.router(Vertx.vertx())

    init {
        val controller = CircleController()

        //set up circle routes
        router.get("/:circleId").handler(controller::getCircle)
        router.get("/").handler(controller::getCircles)
    }
}