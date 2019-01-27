package it.android.luca.movieapp.utils

import okhttp3.*
import java.io.IOException


class MockedInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val response: Response?
        var code = 200
        val uri = chain.request().url().uri()
        val responseString = when(uri.path){
            "/1" -> {
                    code = 404
                "Movie not found" }
            "/372058" -> getJson(MOVIE_DETAIL_RESPONSE)//MOVIE_DETAIL_RESPONSE
            "/top_rated" -> when(uri.rawQuery){
                "page=1" -> getJson(TOP_RATED_RESPONSE)
                "page=2" -> getJson(TOP_RATED_2_RESPONSE)
                else -> {
                    code = 404
                    "Path not found." }
            }
            else -> {
                code = 404
                "Path not found." }
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

    private fun getJson(path : String) : String {
        return String(javaClass.getResourceAsStream(path)!!.readBytes())
    }

    companion object {
        private val MOVIE_DETAIL_RESPONSE = "/detail_response.json"
        private val TOP_RATED_RESPONSE = "/top_rated_1.json"
        private val TOP_RATED_2_RESPONSE = "/top_rated_2.json"
    }
}