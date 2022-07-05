package com.example.animenews.datos

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animenews.datos.dao.ItemAnimeDAO

@Database(
    entities = [ItemAnimeDAO::class],
    version = 1
)
abstract class DataBaseRepository : RoomDatabase() {

    abstract fun animeItem() : ItemAnimeDAO

}