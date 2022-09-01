package com.example.samplecompose

sealed class Route(val route:String){
    object Home: Route("Home")
    object Detail: Route("detail/{gamesId}"){
        fun createRoute(gamesId: Int) = "detail/$gamesId"
    }
}