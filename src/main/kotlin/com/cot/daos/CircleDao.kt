package com.cot.daos

import io.vertx.core.AsyncResult
import io.vertx.core.json.JsonArray
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.kotlin.core.json.JsonArray

class CircleDao(private val client : JDBCClient){
    private val GET_CIRCLE_QUERY = "SELECT * from circles where id = 1"

    fun getCircle(id : String, resultHandler : (AsyncResult<JsonArray>) -> Unit ) {
        client.querySingleWithParams(GET_CIRCLE_QUERY, JsonArray(id.toInt()), resultHandler)
    }
}