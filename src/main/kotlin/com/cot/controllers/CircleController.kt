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

            when {
                result.succeeded() -> {
                    val data = result.result().rows

                    when {

                        data.isEmpty() -> response.setStatusCode(404).end("Circle with id[$circleId] does not exist")
                        else -> response.setStatusCode(200).end(data.first().encode())
                    }

                }
                result.failed() -> {
                    response.setStatusCode(404).end(result.cause().localizedMessage)
                }
            }
        })
    }
}