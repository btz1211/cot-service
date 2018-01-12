package com.cot.controllers

import com.cot.daos.CircleDao
import io.vertx.ext.web.RoutingContext

class CircleController(val circleDao : CircleDao) {
    fun getCircles(context: RoutingContext) {
        context.response().end("circles!")
    }

    fun getCircle(context: RoutingContext) {
        val circleId = context.request().getParam("circleId")
        val response = context.response()

        circleDao.getCircle(circleId, {result ->
            println("---succeeded----${result}")

            when {
                result.succeeded() -> {
                    println("---succeeded----${result}")

                    response.setStatusCode(200).end(result.result().first().toString())
                }
                result.failed() -> {
                    println("----failed---${result}")

                    response.setStatusCode(404).end(result.cause().localizedMessage)
                }
            }
        })
    }
}