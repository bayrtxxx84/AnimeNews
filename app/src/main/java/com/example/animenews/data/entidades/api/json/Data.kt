package com.example.animenews.data.entidades.api.json

import com.example.animenews.data.entidades.database.AnimeItem
import com.example.animenews.model.entidades.AnimeItemModel

data class Data(
    val aired: Aired,
    val airing: Boolean,
    val background: String,
    val broadcast: Broadcast,
    val demographics: List<Demographic>,
    val duration: String,
    val episodes: Int,
    val explicit_genres: List<Any>,
    val favorites: Int,
    val genres: List<Genre>,
    val images: Images,
    val licensors: List<Licensor>,
    val mal_id: Int,
    val members: Int,
    val popularity: Int,
    val producers: List<Producer>,
    val rank: Int,
    val rating: String,
    val score: Double,
    val scored_by: Int,
    val season: String,
    val source: String,
    val status: String,
    val studios: List<Studio>,
    val synopsis: String,
    val themes: List<Theme>,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val title_synonyms: List<String>,
    val trailer: Trailer,
    val type: String,
    val url: String,
    val year: Int
)

fun Data.getAnimeItemModel(): AnimeItemModel {
    val item = AnimeItemModel(mal_id, title)
    item.duration = duration
    item.episodes = episodes
    item.favorites = favorites
    item.genres = genres[0].toString()
    item.images = images
    item.members = members
    item.popularity = popularity
    item.rank = rank
    item.rating = rating
    item.score = score
    item.season = season
    item.source = source
    item.synopsis = synopsis
    item.title_english = title_english
    item.title_japanese = title_japanese
    item.trailer_url = trailer.url
    item.type = type
    item.url = url
    item.year = year
    return item
}

fun Data.getAnimeItem() : AnimeItem {
    val item = AnimeItem(mal_id,images.jpg.image_url, title)
    if (synopsis.length > 120) {
        item.description = synopsis.substring(0,120) + "..."
    }
    else {
        item.description = synopsis.substring(0,synopsis.length) + "..."
    }

    return item
}

