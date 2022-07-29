package com.example.animenews.model.usercase


import com.example.animenews.data.entidades.api.json.getAnimeItem
import com.example.animenews.data.entidades.api.json.getAnimeItemModel
import com.example.animenews.data.entidades.database.AnimeItem
import com.example.animenews.data.repositories.ApiRepository
import com.example.animenews.data.services.AnimeItemsService
import com.example.animenews.model.entidades.AnimeItemModel

class AnimeItemsUserCase {

    private val apiService = ApiRepository().getRetrofit().create(AnimeItemsService::class.java)

    suspend fun getAllAnimeItems(page: Int): List<AnimeItem> {
        val items: MutableList<AnimeItem> = ArrayList()
        val call = apiService.getAllTopItems(page)
        if (call.isSuccessful) {
            call.body()?.data?.forEach() {
                items.add(it.getAnimeItem())
            }
        }
        return items
    }


    suspend fun getFullAnimeItem(id: Int): AnimeItemModel {
        var item: AnimeItemModel? = null
        val call = apiService.getOneFullItem(id)
//      val call1 = apiService.getOneFullItem1("anime/${id}/full")
        if (call.isSuccessful) {
            item = call.body()?.data?.getAnimeItemModel()
        }
        return item ?: AnimeItemModel()
    }
}