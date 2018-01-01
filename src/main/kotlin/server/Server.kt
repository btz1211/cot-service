package server

import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.handler.StaticHandler
import routers.MainRouter


class Server: AbstractVerticle(){

    override fun start() {
        val server = vertx.createHttpServer()

        //routes
        server.requestHandler(MainRouter(vertx).router::accept).listen(8080)
    }
}