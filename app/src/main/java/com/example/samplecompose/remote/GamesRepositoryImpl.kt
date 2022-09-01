package com.example.samplecompose.remote

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.samplecompose.model.Games
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GamesRepositoryImpl constructor(
    private val gamesService: GamesService = GamesService.getInstance(),
): GamesRepository {
    /**
     * This will be called the paging function to retrieve the bulk of GamesResponse.kt from GamesService.kt
     */
    override fun getAllGames(): Flow<PagingData<Games>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            GamesPagingSource(
                response = { pageNext ->
                    gamesService.getAllGames(
                        page = pageNext,
                        pageSize = PAGE_SIZE,
                    )
                }
            )
        }
    ).flow

    /**
     * This will called Response of Games.kt that contains the detail of Games from GamesService.kt
     */
    override fun getDetailGames(id: Int): Flow<Response<Games>> = flow{
        try {
            emit(Response.Loading)
            val responseApi = gamesService.getGamesDetail(id = id)
            emit(Response.Success(responseApi))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}