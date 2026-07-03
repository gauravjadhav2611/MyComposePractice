package com.example.mycomposepractice.repository

import com.example.mycomposepractice.remote.RetrofitInstance

class GameRepository {

    suspend fun getGames() =
        RetrofitInstance.api.getGames()

}