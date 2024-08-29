package br.senai.sp.jandira.rickandmortyapi.service

import br.senai.sp.jandira.rickandmortyapi.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character/{id}")
    // o valor que vai ser inserido tem que ser passado pelo @Path
    fun getCharacterById(@Path("id") id: Int): Call<Character>
}