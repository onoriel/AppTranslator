package com.owcourse.apptranslate

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/0.2/languages")
    suspend fun getLanguages(): Response<List<Language>>
}