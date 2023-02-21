package com.example.travelapp.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("api/auth-login")
    suspend fun login(
        @Field("phone_number") phone: String,
        @Field("password") password: String
    ) : String

    @FormUrlEncoded
    @GET("api/tour-img-slider?limit=5")
    suspend fun imageSlider(
    ) : String

    @FormUrlEncoded
    @GET("api/category-list")
    suspend fun categoryList(
        @Field("id") id : String,
        @Field("name") name : String,
        @Field("created_at") createdAt : String,
        @Field("updated_at") updatedAt : String
    ) : String

    @GET("api/tour-list")
    suspend fun tourList(
    ) : String

    @FormUrlEncoded
    @POST("api/auth-logout")
    suspend fun logout() : String

    @FormUrlEncoded
    @POST("api/category-detail/1")
    suspend fun tourCategoryNature() : String

    @FormUrlEncoded
    @POST("api/category-detail/2")
    suspend fun tourCategoryPark() : String

    @GET("api/category-detail/3")
    suspend fun tourCategoryAll() : String

}