package com.example.proyectofinalricardomitienda

import com.example.proyectofinalricardomitienda.entities.LoginRequest
import com.example.proyectofinalricardomitienda.entities.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApiService {
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<TokenResponse>
}