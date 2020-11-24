package io.github.dzulfikar68.exampleongkirapp

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    factory {
        GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    factory<Retrofit> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        Retrofit.Builder()
                .baseUrl(RajaOngkirConstants.BASE)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                get()
                        )
                )
                .build()
    }

    factory<RajaOngkirServices> {
        get<Retrofit>().create(RajaOngkirServices::class.java)
    }
}