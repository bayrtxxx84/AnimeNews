package com.example.animenews.datos


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animenews.datos.dao.ItemsDAO
import com.example.animenews.datos.entidades.AnimeItem


@Database(
    entities = [AnimeItem::class],
    version = 1
)
abstract class DataBaseRepository : RoomDatabase() {

    abstract fun animeItem(): ItemsDAO

}