package com.busenamli.moviesapp.data.datasource

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.model.*
import com.busenamli.moviesapp.data.api.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    suspend fun fetchPopularMovies(): Flow<PagingData<MovieModel>>
    suspend fun fetchMovieDetails(movieId: Int): Flow<NetworkResult<MovieDetailModel>>
    suspend fun fetchMovieCredits(movieId: Int): Flow<NetworkResult<CreditModel>>
    suspend fun fetchGenreList(): Flow<NetworkResult<GenresResponseModel>>
    suspend fun fetchMoviesByGenre(genreId: Int): Flow<PagingData<MovieModel>>
}