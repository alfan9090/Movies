package com.ahmadalfan.movieapp.data.network

import com.ahmadalfan.movieapp.utils.ApiException
import com.ahmadalfan.movieapp.utils.TokenExpired
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Response<T> {
        val response = call.invoke()

        if (response.isSuccessful) {
            return response
        } else {
            val error = response.errorBody()?.toString()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {

                }
                message.append("\n")
            }
            message.append("Error code : ${response.code()}")
            when (response.code()) {
                401 -> {
                    throw TokenExpired(response.code().toString())
                }
                500 -> {
                    throw ApiException(response.code().toString())
                }
                else -> {
                    throw ApiException(response.code().toString())
                }
            }
        }
    }

}