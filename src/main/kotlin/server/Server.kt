package server

import io.vertx.core.AbstractVerticle
import routers.MainRouter


class Server: AbstractVerticle(){

    override fun start() {
        val server = vertx.createHttpServer()

        //routes
        server.requestHandler(MainRouter.router::accept).listen(8080)
    }
}