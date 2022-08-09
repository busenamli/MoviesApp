package com.busenamli.moviesapp.network

import com.busenamli.moviesapp.data.model.CreditModel
import com.busenamli.moviesapp.data.model.MoviesDetailModel
import com.busenamli.moviesapp.data.model.ResultModel
import com.busenamli.moviesapp.util.Constants.GET_MOVIE_CREDITS
import com.busenamli.moviesapp.util.Constants.GET_MOVIE_DETAIL
import com.busenamli.moviesapp.util.Constants.GET_POPULAR_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET(GET_POPULAR_KEY)
    suspend fun fetchPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<ResultModel>


    @GET(GET_MOVIE_DETAIL)
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Response<MoviesDetailModel>

    @GET(GET_MOVIE_CREDITS)
    suspend fun fetchMovieCredits(
        @Path("movie_id") movieId:Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<CreditModel>
}
