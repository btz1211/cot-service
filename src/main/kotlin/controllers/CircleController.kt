package controllers

import io.vertx.ext.web.RoutingContext

class CircleController {
    fun getCircles(context: RoutingContext) {
        context.response().end("circles!")
    }

    fun getCircle(context: RoutingContext) {
        val circleId = context.request().getParam("circleId").toInt()
        val response = context.response()

        if(circleId == 1) {
            response.setStatusCode(200).end("Success")
        } else {
            response.setStatusCode(404).end("cannot find circle with id: $circleId")
        }
    }
}