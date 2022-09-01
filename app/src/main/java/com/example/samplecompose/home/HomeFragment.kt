package com.example.samplecompose.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.samplecompose.ui.theme.SampleComposeTheme
import com.example.samplecompose.viewmodels.HomeViewModel

// Since this is a composable, add a parameter modifier
@Composable
fun HomeFragment(
    modifier: Modifier = Modifier,
    onClickToDetailScreen: (Int) -> Unit = {},
    homeViewModel: HomeViewModel = viewModel(),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        HomeScreen(
            modifier = Modifier
            .padding(
                horizontal = 16.dp
            ),
            onClickToDetailScreen = onClickToDetailScreen,
            gamesList = homeViewModel.gamesListState.collectAsLazyPagingItems(),
        )
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeFragmentPreview() {
    SampleComposeTheme() {
        HomeFragment()
    }
}