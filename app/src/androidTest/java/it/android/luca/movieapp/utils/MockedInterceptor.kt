package it.android.luca.movieapp.utils

import okhttp3.*
import java.io.IOException


class MockedInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val response: Response?
        var code = 200
        val uri = chain.request().url().uri()

        val responseString = when {
            mockedResponseMap.containsKey(uri.toString().replace("https://localhost:8080", "")) ->
                getJson(mockedResponseMap[uri.toString().replace("https://localhost:8080", "")]!!)

            mockedErrorResponseMap.containsKey(uri.toString().replace("https://localhost:8080", "")) -> {
                code = 404
                mockedErrorResponseMap[uri.toString().replace("https://localhost:8080", "")]!!
            }
            else -> {
                code = 404
                "Path not found in the map"
            }
        }
        response = Response.Builder()
            .code(code)
            .message(responseString)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
            .addHeader("content-type", "application/json")
            .build()

        return response
    }

    private fun getJson(path: String): String {
        return String(javaClass.getResourceAsStream(path)!!.readBytes())
    }

    companion object {
        val mockedResponseMap = hashMapOf<String, String>()
        val mockedErrorResponseMap = hashMapOf<String, String>()
    }
}