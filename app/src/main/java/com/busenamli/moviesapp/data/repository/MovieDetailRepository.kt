package com.busenamli.moviesapp.data.repository

import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.model.CreditModel
import com.busenamli.moviesapp.data.model.MovieDetailModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun fetchMovieDetail(movieId: Int): Flow<NetworkResult<MovieDetailModel>>
    suspend fun fetchMovieCredit(movieId: Int): Flow<NetworkResult<CreditModel>>
}