package io.github.dzulfikar68.exampleongkirapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTanpaDI {
    private fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        return okHttpClient
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