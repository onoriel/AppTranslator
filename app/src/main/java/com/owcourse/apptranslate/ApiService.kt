package com.owcourse.apptranslate

import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/0.2/languages")
    suspend fun getLanguages(): Response<List<Language>>

    @Headers("Authorization: Bearer 55acac03930e5acff00b2e3d28356e63")
    @FormUrlEncoded
    @POST("/0.2/detect")
    suspend fun getTextLanguage(@Field("q") text: String):Response<DetectionResponse>
}