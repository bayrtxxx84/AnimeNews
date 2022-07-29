package com.example.animenews.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.animenews.data.repositories.DataBaseConnection
import com.example.animenews.data.repositories.DataBaseRepository

class AnimeNews : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


    fun SharePreferences() {
        val publicShare =
            applicationContext.getSharedPreferences(
                "sesiones",
                Context.MODE_PRIVATE
            )

        with(publicShare.edit()) {
            putString("nombre", "Alex")
            putString("apellido", "Bustamante")
        }.apply()

        val activityShare = PreferenceManager.getDefaultSharedPreferences(this)
        with(activityShare.edit()) {
            putString("nombre", "Bayron")
            putString("apellido", "Torres")
        }.apply()

        val a = publicShare.getString("nombre", "Dato inexistente")
        val b = activityShare.getString("nombre", "Dato inexistente")

        Log.d("UCE", a.toString())
        Log.d("UCE", b.toString())
    }

    companion object {

        private var db: DataBaseRepository? = null
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        fun getDbInstance(): DataBaseRepository {
            if (db == null) {
                db = DataBaseConnection(context!!).getConnection()
            }
            return db!!
        }
    }

}