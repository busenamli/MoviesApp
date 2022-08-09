package com.busenamli.moviesapp.data.datasource

import androidx.paging.PagingData
import com.busenamli.moviesapp.NetworkResult
import com.busenamli.moviesapp.data.model.CreditModel
import com.busenamli.moviesapp.data.model.MovieModel
import com.busenamli.moviesapp.data.model.MoviesDetailModel
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {

    suspend fun getPopularMovies(): Flow<PagingData<MovieModel>>
    suspend fun fetchMovieDetails(movieId: Int): Flow<NetworkResult<MoviesDetailModel>>
    suspend fun fetchMovieCredits(movieId: Int): Flow<NetworkResult<CreditModel>>
}