package com.example.epicfilms.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


const val BASE_URL = "https://api.themoviedb.org/3/movie/"
const val TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZjNhOTkzOTI3NTljNTFlZmE1ZmU1ZjY2YTAxNmI3YiIsInN1YiI6IjY2MGQxYTU1NWFhZGM0MDE3YzY2MWY5MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.M2VfrQRa6WEBLClQavWYYgvewVXwPk8Nf0ozy-NU0Jo"

data class Response(
    val page: Int,
    val results: List<Movie>
)

interface APIService {
    @GET("popular?language=en-US")
    suspend fun getPopular(
        @Query("page") page: Int = 1,
        @Header("Authorization") authHeader: String = TOKEN,
        @Header("Accept") accept: String = "application/json"
    ): Response

    @GET("top_rated?language=en-US")
    suspend fun getTopRated(
        @Query("page") page: Int = 1,
        @Header("Authorization") authHeader: String = TOKEN,
        @Header("Accept") accept: String = "application/json"
    ): Response

    @GET("{id}")
    suspend fun getMovie(
        @Path("id") id: Int = 1,
        @Header("Authorization") authHeader: String = TOKEN,
        @Header("Accept") accept: String = "application/json"
    ): Movie

    companion object {
        private var apiService: APIService? = null

        fun getInstance() : APIService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIService::class.java)
            }

            return apiService!!
        }
    }
}