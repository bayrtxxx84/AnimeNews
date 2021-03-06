package com.example.animenews.data.repositories


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.animenews.data.dao.ItemsDAO
import com.example.animenews.data.entidades.database.AnimeItem


@Database(
    entities = [AnimeItem::class],
    version = 1,
    exportSchema = false
)
abstract class DataBaseRepository : RoomDatabase() {

    abstract fun animeItem(): ItemsDAO

}

class DataBaseConnection(private val context: Context) {

    val _DBNAME : String = "animeDataBase"

    fun getConnection() = Room.databaseBuilder(
        context,
        DataBaseRepository::class.java,
        _DBNAME
    ).build()
}
