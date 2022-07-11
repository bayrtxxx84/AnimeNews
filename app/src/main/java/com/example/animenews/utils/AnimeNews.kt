package com.example.animenews.utils

import android.app.Application
import android.content.Context
import com.example.animenews.data.repositories.DataBaseConnection
import com.example.animenews.data.repositories.DataBaseRepository

class AnimeNews : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {

        private var db: DataBaseRepository? = null
        private var context: Context? = null

        fun getDbInstance(): DataBaseRepository {
            if (db == null) {
                db = DataBaseConnection(context!!).getConnection()
            }
            return db!!
        }
    }

}