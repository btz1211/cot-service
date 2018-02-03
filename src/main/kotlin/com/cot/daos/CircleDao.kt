package com.cot.daos

import io.vertx.core.Future
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.sql.ResultSet
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json

class CircleDao(private val client : JDBCClient){
    private val GET_CIRCLE_QUERY = "SELECT * FROM circles WHERE id = ?"

    fun getCircle(id : String): Future<ResultSet> {
        val resultFuture : Future<ResultSet> = Future.future()

        client.queryWithParams(GET_CIRCLE_QUERY, json { array(id) }, { result ->

            when {
                result.succeeded() -> resultFuture.complete(result.result())
                result.failed() -> resultFuture.fail(result.cause())
            }
        })

        return resultFuture
    }
}