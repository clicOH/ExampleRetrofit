package com.example.regrofit.api

import com.example.regrofit.model.home.ModelPerson
import com.example.regrofit.model.location.ModelLocations
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("character")
    suspend fun getPerson():Response<ModelPerson>

    @GET("location/{personId}")
    suspend fun getLocation(
        @Path("personId") personId: Int
    ):Response<ModelLocations>

    @GET("episode")
    suspend fun getEpisode():Response<ModelPerson>
}