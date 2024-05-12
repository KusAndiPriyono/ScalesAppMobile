package com.bangkit.scalesappmobile.data.remote

import com.bangkit.scalesappmobile.domain.model.RefreshTokenRequest
import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import com.bangkit.scalesappmobile.util.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor(
    private val dataStoreRepository: DataStoreRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val savedToken = runBlocking {
            dataStoreRepository.getAccessToken().first()
        }

        if (savedToken?.isNotEmpty() == true) {
            val response = makeRequest(
                chain = chain,
                token = savedToken
            )

            return if (response.code == 401) {
                runBlocking {
                    response.close()
                    val generatedToken = refreshExpiredToken(savedToken)
                    saveNewToken(generatedToken)
                    val newToken = dataStoreRepository.getAccessToken().first()
                    makeRequest(
                        chain = chain,
                        token = newToken
                    )
                }
            } else {
                response
            }
        } else {
            return makeRequest(
                chain = chain,
                token = null
            )
        }
    }

    private fun makeRequest(
        chain: Interceptor.Chain,
        token: String?
    ): Response {
        val newChain =
            chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
        return chain.proceed(newChain)
    }

    private suspend fun refreshExpiredToken(savedToken: String): String? {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val scalesApiService = retrofit.create(ScalesApiService::class.java)

        return scalesApiService.refreshToken(
            RefreshTokenRequest(savedToken)
        )?.token
    }

    private suspend fun saveNewToken(generatedToken: String?) {
        if (generatedToken != null) {
            dataStoreRepository.saveAccessToken(generatedToken)
        }
    }
}