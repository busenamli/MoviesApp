package com.busenamli.moviesapp.data.api

import com.busenamli.moviesapp.data.model.*
import com.busenamli.moviesapp.util.Constants.GET_GENRE_LIST
import com.busenamli.moviesapp.util.Constants.GET_MOVIES_BY_GENRE_LIST
import com.busenamli.moviesapp.util.Constants.GET_MOVIE_CREDITS
import com.busenamli.moviesapp.util.Constants.GET_MOVIE_DETAIL
import com.busenamli.moviesapp.util.Constants.GET_POPULAR_MOVIES
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET(GET_POPULAR_MOVIES)
    suspend fun fetchPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET(GET_MOVIE_DETAIL)
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Response<MovieDetail>

    @GET(GET_MOVIE_CREDITS)
    suspend fun fetchMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<Credit>

    @GET(GET_GENRE_LIST)
    suspend fun fetchGenreList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<GenreResponse>

    @GET(GET_MOVIES_BY_GENRE_LIST)
    suspend fun fetchMoviesByGenreList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): Response<MoviesResponse>
}
