package com.example.travelapp.api

import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("api/auth-login")
    suspend fun login(
        @Field("phone_number") phone: String,
        @Field("password") password: String
    ) : String

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

    @GET("api/category-detail/{id_tour}")
    suspend fun tourCategory(
        @Path("id_tour") id_tour : Int?
    ) : String


//    @FormUrlEncoded
//    @POST("api/category-detail/1")
//    suspend fun tourCategoryNature() : String
//
//    @FormUrlEncoded
//    @POST("api/category-detail/2")
//    suspend fun tourCategoryPark() : String
//
//    @GET("api/category-detail/3")
//    suspend fun tourCategoryAll() : String

    @FormUrlEncoded
    @POST("api/user-edit")
    suspend fun userUpdate(
        @Field("_method") method: String,
        @Field("name") name : String,
        @Field("phone_number") phone : String,
    ) : String

    @Multipart
    @POST("api/user-edit")
    suspend fun userUpdateWithPhoto(
        @Part("_method") method: String,
        @Part("name") name : String,
        @Part("phone_number") phone : String,
        @Part photo : MultipartBody.Part?
    ) : String

    @FormUrlEncoded
    @POST("api/add-favourite")
    suspend fun postFavourite(
        @Field("tour_id")tour_id : Int?
    ) : String

}