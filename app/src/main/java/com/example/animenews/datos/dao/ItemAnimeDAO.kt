package com.example.animenews.datos.dao

import androidx.room.*
import com.example.animenews.AnimeItem

@Dao
interface ItemAnimeDAO {

    @Query("SELECT * FROM AnimeItem")
    fun getAllItems() : List<AnimeItem>

    @Query("SELECT * FROM AnimeItem WHERE id = :idItem")
    fun getOneItem(idItem: Int) : AnimeItem

    @Insert
    fun insertItem(item : AnimeItem)

    @Delete
    fun deleteItem(item : AnimeItem)

    @Update
    fun updateItem(item : AnimeItem)


}