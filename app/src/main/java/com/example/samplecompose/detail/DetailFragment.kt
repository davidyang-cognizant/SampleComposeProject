package com.example.samplecompose.detail

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.samplecompose.remote.Response
import com.example.samplecompose.ui.theme.SampleComposeTheme
import com.example.samplecompose.utils.ErrorButton
import com.example.samplecompose.utils.LoadingCircular
import com.example.samplecompose.viewmodels.DetailViewModel

@Composable
fun DetailFragment(
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = viewModel(),
    id: Int = -1,
    ) {

    fun launch() {
        detailViewModel.getDetailGames(id)
    }

    launch()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when (val gamesResponse = detailViewModel.gamesState.value) {
            is Response.Loading -> {
                LoadingCircular(
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is Response.Success -> {
                DetailScreen(
                    games = gamesResponse.data
                )
            }
            is Response.Failure -> {
                ErrorButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Error occurred. Please try again",
                    onClick = {
                        launch()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailFragmentPreview() {
    SampleComposeTheme() {
        DetailFragment()
    }
}