package com.example.animenews.data.repositories

import com.example.animenews.data.entidades.AnimeItem

object DataRepository {

    fun listAnime(): List<AnimeItem> {
        return listOf(
            AnimeItem(
                1,
                "https://cdn.animenewsnetwork.com/thumbnails/fit200x200/encyc/A5564-18.jpg",
                "Steel Ball Run"
            ),
            AnimeItem(
                2,
                "https://cdn.animenewsnetwork.com/thumbnails/max500x600/encyc/A2298-11.jpg",
                "Berserk"
            ),
            AnimeItem(
                3,
                "https://cdn.animenewsnetwork.com/thumbnails/fit200x200/encyc/A1511-11.jpg",
                " Monster (TV)"
            ),
            AnimeItem(
                4,
                "https://www.animenewsnetwork.com/images/encyc/A1264-61.jpg",
                "Nausicaä of the Valley of the Wind"
            ),
            AnimeItem(
                5,
                "https://www.animenewsnetwork.com/images/encyc/A3605-25.1409849568.jpg",
                "Yokohama Kaidashi Kikou"
            )
        )
    }

}