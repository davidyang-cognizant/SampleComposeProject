package com.example.samplecompose.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.samplecompose.model.Games
import com.example.samplecompose.ui.theme.SampleComposeTheme
import com.example.samplecompose.utils.ErrorButton
import com.example.samplecompose.utils.LoadingCircular

/**
 * https://betterprogramming.pub/viewmodel-to-view-in-jetpack-compose-7c7183b54fb5
 * PLEASE READ
 *
 * gamesList is part of LazyPagingItems that use Paging Jetpack Compose API.
 *
 * If the gamesList is null, that means it doesn’t have the data for us to show, so we will stop the process there.
 * If it is not null, then we will continue the process.
 *
 * PagingList Games.kt will continue to add the data because of Paging feature.
 * Naturally, value itemCount in PagingList will continue to increase by number.
 *
 * Each PagingList will be shown to us on a list of ProductCard.kt. But we need to parse the detail into ProductCard.kt.
 * Since the ProductCard.kt is a composable function that may be used in other classes beside HomeScreen.kt,
 * and the content may not be limited to PagingList of Games.kt,
 * we have to parse the detail by giving the parameter in general for ProductCard.kt.
 *
 * gamesList has CombinedLoadStates and each state contains LoadState such as loading and error.
 * We simply just have to show the user the loading view if gamesList is in loading state and error view if gamesList is in error state.
 * If the gamesList is not in loading state nor error state, we will show a list of ProductCard.kt.
 *
 * ProductCard.kt has features in onClickProduct with the correct Id in Games.kt to be parsed as an argument to DetailFragment.kt.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickToDetailScreen: (Int) -> Unit = {},
    gamesList: LazyPagingItems<Games>? = null,
) {
    if(gamesList == null) return
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Adaptive(minSize = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(gamesList.itemCount) { index ->
            gamesList[index].let { games ->
                val id = games?.id
                val name = games?.name ?: ""
                val imageUrl = games?.backgroundImage ?: ""
                val releaseDate = games?.released ?: ""
                ProductCard(
                    modifier = modifier
                        .padding(8.dp),
                    name = name,
                    imageUrl = imageUrl,
                    releaseDate = releaseDate,
                    onClickProduct = {
                        id?.let {
                            onClickToDetailScreen.invoke(it)
                        }
                    }
                )
            }
        }
        /*
        Append: part of CombinedLoadStates that is executed by scrolling to the bottom of the screen.
        Usually, this consists of scrolling content to the bottom with infinite content because the
        content keeps being added to the bottom of the screen.

        Prepend : part of CombinedLoadStates that is executed by scrolling to the upper of the screen.
        Usually, this consists of scrolling the page up to refresh the page so the content is replaced by new content.

        Refresh : part of CombinedLoadStates that is executed by either append, prepend, or manual refresh.
        Manual means having an action for an end user to refresh the page,
        such as pressing the refresh button to refresh the page so the content is replaced by new content.
         */
        gamesList.apply {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                when {
                    loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                        // Handle if the callback api is loading
                        LoadingCircular(
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                        // Handle if the callback api is error
                        ErrorButton(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Error occurred. Please try again",
                            onClick = {
                                retry()
                            }
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SampleComposeTheme() {
        HomeScreen()
    }
}