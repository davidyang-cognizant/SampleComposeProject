package com.example.samplecompose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.samplecompose.model.Games
import com.example.samplecompose.remote.GamesRepository
import com.example.samplecompose.remote.GamesRepositoryImpl
import kotlinx.coroutines.flow.Flow

class HomeViewModel constructor(
    gamesRepository: GamesRepository = GamesRepositoryImpl(),
): ViewModel() {

    val gamesListState: Flow<PagingData<Games>> =
        gamesRepository.getAllGames().cachedIn(viewModelScope)
}