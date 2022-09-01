package com.example.samplecompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.samplecompose.detail.DetailFragment
import com.example.samplecompose.home.HomeFragment
import com.example.samplecompose.ui.theme.SampleComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleComposeTheme {
                    JetpackComposeAppScreen()
            }
        }
    }
}


@Composable
fun JetpackComposeAppScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
    ) {
        composable(route = Route.Home.route) {
            HomeFragment(onClickToDetailScreen = { gamesId ->
                navController.navigate(
                    Route.Detail.createRoute(gamesId)
                )
            })
        }
        composable(
            route = Route.Detail.route,
            arguments = listOf(
                navArgument("gamesId"){
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val gamesId = backStackEntry.arguments?.getInt("gamesId")
            requireNotNull(gamesId) { "gamesId parameter wasn't found. Please make sure it's set!" }
            DetailFragment(id = gamesId)
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    SampleComposeTheme {
        JetpackComposeAppScreen()
    }
}