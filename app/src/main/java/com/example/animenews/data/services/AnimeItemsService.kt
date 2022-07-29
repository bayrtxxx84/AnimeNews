package com.example.animenews.data.services


import com.example.animenews.data.entidades.api.AnimeItemApi
import com.example.animenews.data.entidades.api.FullAnimeItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface AnimeItemsService {

    // https://api.jikan.moe/v4/top/anime
    @GET("top/anime")
    suspend fun getAllTopItems(@Query("page") page: Int): Response<AnimeItemApi>

    @GET("anime/{id}/full")
    suspend fun getOneFullItem(@Path("id") id: Int): Response<FullAnimeItem>

    @GET
    suspend fun getOneFullItem1(@Url url: String): Response<FullAnimeItem>
}