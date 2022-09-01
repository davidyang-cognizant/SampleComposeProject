package com.example.samplecompose.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.samplecompose.model.Games
import com.example.samplecompose.model.GamesResponse


class GamesPagingSource(
    private val response: suspend (Int) -> GamesResponse,
) : PagingSource<Int, Games>() {

    /**
     * This function is responsible when load want to be executed. This function will return
     * PagingState.anchorposition in order for load to know which position
     * the user at right now, so the load will execute the service properly without issue.
     */
    override fun getRefreshKey(state: PagingState<Int, Games>): Int? {
        return state.anchorPosition
    }

    /**
     * This function will call getAllGames from GamesService function from response parameter,
     * and store it with nextPage and prevPage information to
     * LoadResult from PagingSource class from paging library.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Games> {
        return try {
            val nextPage = params.key ?: 1
            val gamesList = response.invoke(nextPage)
            LoadResult.Page(
                data = gamesList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = gamesList
                    .next
                    ?.substringAfter("page=")
                    ?.substringBefore("&")
                    ?.toInt() ?: nextPage
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}