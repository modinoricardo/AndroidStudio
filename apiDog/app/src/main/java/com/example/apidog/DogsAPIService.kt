package com.example.apidog

import retrofit2.Response
import retrofit2.http.GET

interface DogsAPIService {

    @GET("images")
    suspend fun getFotosPerros(): Response<DogRespuesta>
}