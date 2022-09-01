package com.example.samplecompose.remote

import androidx.paging.PagingData
import com.example.samplecompose.model.Games
import kotlinx.coroutines.flow.Flow
interface GamesRepository {
    fun getAllGames(): Flow<PagingData<Games>>
    fun getDetailGames(id: Int): Flow<Response<Games>>
}