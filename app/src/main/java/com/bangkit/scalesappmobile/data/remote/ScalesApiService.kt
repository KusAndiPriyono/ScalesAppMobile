package com.bangkit.scalesappmobile.data.remote

import com.bangkit.scalesappmobile.data.remote.scales.AuthResponse
import com.bangkit.scalesappmobile.data.remote.scales.GetAllScalesResponse
import com.bangkit.scalesappmobile.data.remote.scales.GetScalesDetailResponse
import com.bangkit.scalesappmobile.data.remote.scales.GetScalesUpdateResponse
import com.bangkit.scalesappmobile.domain.model.ForgotPasswordRequest
import com.bangkit.scalesappmobile.domain.model.LoginRequest
import com.bangkit.scalesappmobile.domain.model.RefreshTokenRequest
import com.bangkit.scalesappmobile.domain.model.RegisterRequest
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ScalesApiService {

    @GET("api/v1/scales")
    suspend fun getAllScales(
        @Query("page") page: Int,
    ): GetAllScalesResponse

    @GET("api/v1/scales/{id}")
    suspend fun getScalesDetail(
        @Path("id") id: String,
    ): GetScalesDetailResponse

    @Multipart
    @POST("api/v1/scales")
    suspend fun createNewScales(
        @Header("Authorization") token: String,
        @Part body: RequestBody,
    )

    @PATCH("api/v1/scales/{id}")
    suspend fun updateScales(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): GetScalesUpdateResponse

    @DELETE("api/v1/scales/{id}")
    suspend fun deleteScales(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    )

    @POST("api/v1/users/login")
    suspend fun refreshToken(
        @Body refreshTokenRequest: RefreshTokenRequest,
    ): AuthResponse?

    @POST("api/v1/users/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest,
    ): AuthResponse

    @POST("api/v1/users/signup")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest,
    ): AuthResponse

    @POST("api/v1/users/forgotPassword")
    suspend fun forgotPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest,
    ): ForgotPasswordRequest
}