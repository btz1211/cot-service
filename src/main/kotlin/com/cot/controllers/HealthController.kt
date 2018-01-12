package com.cot.controllers

import io.vertx.ext.web.RoutingContext

class HealthController {
    fun ping(context: RoutingContext) {
        context.response().setStatusCode(200).end("OK")
    }
}