package co.api.docs

import retrofit2.Call
import retrofit2.http.GET

interface DocsApi {

    @GET("api")
    fun apis(): Call<List<Api>>

}
