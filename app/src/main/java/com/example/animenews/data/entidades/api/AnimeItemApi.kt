package com.example.animenews.data.entidades.api

import com.example.animenews.data.entidades.api.json.Data
import com.example.animenews.data.entidades.api.json.Links
import com.example.animenews.data.entidades.api.json.Meta
import com.example.animenews.data.entidades.api.json.Pagination


data class AnimeItemApi(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta,
    val pagination: Pagination
)
