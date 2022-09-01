package com.example.samplecompose.remote

import com.example.samplecompose.model.Games
import com.example.samplecompose.model.GamesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesService {

    @GET("games")
    suspend fun getAllGames(
        @Query("key") key: String = "d5600a331be3446c945541a2df42697f",
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
    ) : GamesResponse

    @GET("games/{id}")
    suspend fun getGamesDetail(
        @Path("id") id: Int,
        @Query("key") key: String = "d5600a331be3446c945541a2df42697f",
    ) : Games

    companion object {
        private var retrofitService: GamesService? = null
        fun getInstance() : GamesService {
            if (retrofitService == null) {
                val client = OkHttpClient
                    .Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.rawg.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                retrofitService = retrofit.create(GamesService::class.java)
            }
            return retrofitService!!
        }
    }
}