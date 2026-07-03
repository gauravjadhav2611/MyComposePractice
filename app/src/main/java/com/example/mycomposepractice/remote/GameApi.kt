package com.example.mycomposepractice.remote

import com.example.mycomposepractice.model.Game
import retrofit2.http.GET

interface GameApi {

    @GET("games")
    suspend fun getGames(): List<Game>

}