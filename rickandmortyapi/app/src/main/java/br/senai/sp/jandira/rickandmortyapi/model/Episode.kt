package br.senai.sp.jandira.rickandmortyapi.model

import com.google.gson.annotations.SerializedName

data class Episode(
    val id: Int = 0,
    val name: String = "",
    @SerializedName("air_date") val airDate: String = "",
    val episode: String = "",
    val characters: List<Character>,
    val url: String = "",
    val created: String = ""
)
