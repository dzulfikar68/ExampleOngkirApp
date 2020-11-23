package io.github.dzulfikar68.exampleongkirapp

import retrofit2.Call
import retrofit2.http.*

interface RajaOngkirServices {
    @GET("city")
    fun getCities(
            @Header("key") auth: String? = RajaOngkirConstants.AUTH
    ): Call<RajaOngkirResponse<ResultList<CityItem>?>?>

    @FormUrlEncoded
    @POST("cost")
    fun postCosts(
            @Field("origin") cityOrigin: Int? = null,
            @Field("destination") cityDestination: Int? = null,
            @Field("weight") beratBarang: String? = null,
            @Field("courier") tipeEkspedisi: String? = null,
            @Header("key") auth: String? = RajaOngkirConstants.AUTH
    ): Call<RajaOngkirResponse<ResultList<OngkirItem>?>?>
}