package br.senai.sp.jandira.rickandmortyapi.service

import br.senai.sp.jandira.rickandmortyapi.model.Episode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeService {

    @GET("episode/{ids}/")
    fun getEpisodesById (@Path("ids") id: String) : Call<List<Episode>>


}