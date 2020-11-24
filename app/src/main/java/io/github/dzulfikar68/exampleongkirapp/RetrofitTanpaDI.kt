package io.github.dzulfikar68.exampleongkirapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTanpaDI {
    private fun getInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(RajaOngkirConstants.BASE)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getService() = getRetrofit().create(RajaOngkirServices::class.java)
}