package com.cot.controllers

import com.cot.daos.CircleDao
import io.mockk.*
import io.vertx.core.Future
import io.vertx.core.http.HttpServerRequest
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonObject
import io.vertx.ext.sql.ResultSet
import io.vertx.ext.web.RoutingContext
import org.junit.Before
import org.junit.Test

class CircleControllerTest {
    private val circleDao = mockk<CircleDao>()
    private val mockedRequest = mockk<HttpServerRequest>()
    private val mockedResponse = mockk<HttpServerResponse>()
    private val mockedRoutingContext = mockk<RoutingContext>()
    private val mockedResultSet = mockk<ResultSet>()

    // subject
    private val resultFuture = Future.future<ResultSet>()
    private val circleController = CircleController(circleDao)

    @Before
    fun setUp() {
        every { circleDao.getCircle(any()) } returns resultFuture

        // mock server request
        every { mockedRoutingContext.request() } returns mockedRequest
        every { mockedRequest.getParam(any()) } returns "1"

        // mock server response
        every { mockedRoutingContext.response() } returns mockedResponse
        every { mockedResponse.setStatusCode(any()) } returns mockedResponse
        every { mockedResponse.end(ofType(String::class)) } just Runs
    }

    @Test
    fun testGetCircleThatExists() {
        // given
        val json = JsonObject().put("hello", "world")
        val expectedResult = listOf(json)
        every { mockedResultSet.rows } returns expectedResult

        // when
        circleController.getCircle(mockedRoutingContext)
        resultFuture.complete(mockedResultSet)

        // then
        verify {
            mockedResponse.setStatusCode(200)
            mockedResponse.end(json.encode())
        }
    }

    @Test
    fun testGetCircleThatDoesNotExist() {
        // given
        every { mockedResultSet.rows } returns listOf()

        // when
        circleController.getCircle(mockedRoutingContext)
        resultFuture.complete(mockedResultSet)

        // then
        verify {
            mockedResponse.setStatusCode(404)
            mockedResponse.end("Circle with id[1] does not exist")
        }
    }

    @Test
    fun testGetCircleFailed() {
        // when
        circleController.getCircle(mockedRoutingContext)
        resultFuture.fail(Exception("something went wrong"))

        // then
        verify {
            mockedResponse.setStatusCode(500)
            mockedResponse.end("something went wrong")
        }
    }
}