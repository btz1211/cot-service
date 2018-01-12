package com.cot.verticles

import io.vertx.core.AbstractVerticle
import routers.MainRouter


class Server: AbstractVerticle(){

    override fun start() {
        val server = vertx.createHttpServer()

        //routes
        server.requestHandler(MainRouter(vertx).router::accept).listen(8080)
    }

}
