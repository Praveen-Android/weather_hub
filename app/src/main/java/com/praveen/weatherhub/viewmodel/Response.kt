package com.praveen.weatherhub.viewmodel

import com.praveen.weatherhub.viewmodel.Status.*

class Response private constructor(val status: Status, val data: kotlin.Any?, val error: Throwable?) {

    companion object {
        fun loading(): Response {
            return Response(LOADING, null, null)
        }

        fun success(data: kotlin.Any?): Response {
            return Response(SUCCESS, data, null)
        }

        fun error(error: Throwable?): Response {
            return Response(ERROR, null, error)
        }
    }
}
