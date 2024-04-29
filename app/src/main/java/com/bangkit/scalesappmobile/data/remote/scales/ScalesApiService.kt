package com.bangkit.scalesappmobile.data.remote.scales

import com.bangkit.scalesappmobile.data.remote.ErrorMessageResponse
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.FieldMap
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
        @Query("page") page: Int
    ): GetAllScalesResponse

    @GET("api/v1/scales/{id}")
    suspend fun getScalesDetail(
        @Path("id") id: String
    ): GetScalesDetailResponse

    @Multipart
    @POST("api/v1/scales")
    suspend fun createNewScales(
        @Header("Authorization") token: String,
        @Part body: RequestBody
    ): ErrorMessageResponse

    @PATCH("api/v1/scales/{id}")
    suspend fun updateScales(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @FieldMap body: Map<String, Any>
    ): ErrorMessageResponse

    @DELETE("api/v1/scales/{id}")
    suspend fun deleteScales(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ErrorMessageResponse
}