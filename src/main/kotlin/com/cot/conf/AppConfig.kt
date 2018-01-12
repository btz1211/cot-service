package com.cot.conf

import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.json.JsonObject

object AppConfig {
    private val CONFIG_FILE = "src/main/conf/app-conf.json"
    private var config : JsonObject

    init {
        val configData : Buffer = Vertx.vertx().fileSystem().readFileBlocking(CONFIG_FILE)
        config = JsonObject(configData.toString())
    }

    fun getStringConfig(key: String) : String? {
        return config.getString(key)
    }

    fun getJsonConfig(key: String) : JsonObject? {
        return config.getJsonObject(key)
    }
}