package routers

import com.cot.controllers.CircleController
import com.cot.daos.CircleDao
import io.vertx.core.Vertx
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.web.Router

class CircleRouter(vertx: Vertx, circleDao: CircleDao) {
    val router = Router.router(vertx)

    init {
        val controller = CircleController(circleDao)

        //set up circle routes
        router.get("/:circleId").handler(controller::getCircle)
        router.get("/").handler(controller::getCircles)
    }
}