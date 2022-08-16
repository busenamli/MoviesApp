package com.busenamli.moviesapp.data.datasource

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.model.*
import com.busenamli.moviesapp.data.api.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    suspend fun fetchPopularMovies(): Flow<PagingData<Movie>>
    suspend fun fetchMovieDetails(movieId: Int): Flow<NetworkResult<MovieDetail>>
    suspend fun fetchMovieCredits(movieId: Int): Flow<NetworkResult<Credit>>
    suspend fun fetchGenreList(): Flow<NetworkResult<GenreResponse>>
    suspend fun fetchMoviesByGenre(genreId: Int): Flow<PagingData<Movie>>
}