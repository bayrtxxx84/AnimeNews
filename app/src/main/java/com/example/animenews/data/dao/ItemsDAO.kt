package com.example.animenews.data.dao

import androidx.room.*
import com.example.animenews.data.entidades.AnimeItem

@Dao
interface ItemsDAO {

    @Query("SELECT * FROM AnimeItem")
    fun getAllItems() : List<AnimeItem>

    @Query("SELECT * FROM AnimeItem WHERE id = :idItem")
    fun getOneItem(idItem: Int) : AnimeItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item : AnimeItem)

    @Delete
    fun deleteItem(item : AnimeItem)

    @Update
    fun updateItem(item : AnimeItem)
}