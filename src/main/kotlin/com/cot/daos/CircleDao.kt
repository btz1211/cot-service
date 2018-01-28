package com.cot.daos

import io.vertx.core.AsyncResult
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.sql.ResultSet
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json

class CircleDao(private val client : JDBCClient){
    private val GET_CIRCLE_QUERY = "select * from circles where id = ?"

    fun getCircle(id : String, resultHandler : (AsyncResult<ResultSet>) -> Unit ) {
        client.queryWithParams(GET_CIRCLE_QUERY,  json { array(id) }, resultHandler)
    }
}