package com.example.animenews.model.entidades

import com.example.animenews.data.entidades.api.json.*

data class AnimeItemModel(val mal_id: Int = -1, val title: String = "") {
    var duration: String? = ""
    var episodes: Int? = 0
    var favorites: Int? = 0
    var genres: String? = ""
    var images: Images? = null
    var members: Int? = 0
    var popularity: Int? = 0
    var rank: Int? = 0
    var rating: String? = ""
    var score: Double? = 0.0
    var season: String? = ""
    var source: String? = ""
    var synopsis: String? = ""
    var title_english: String? = ""
    var title_japanese: String? = ""
    var trailer_url: String? = ""
    var type: String? = ""
    var url: String? = ""
    var year: Int? = 0
}
