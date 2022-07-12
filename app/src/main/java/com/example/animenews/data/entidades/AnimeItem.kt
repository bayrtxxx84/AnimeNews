package com.example.animenews.data.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class AnimeItem(
    @PrimaryKey
    val id: Int,
    val photo: String,
    var name: String
    ) {
        var description: String = ""
        var url: String = ""
    }