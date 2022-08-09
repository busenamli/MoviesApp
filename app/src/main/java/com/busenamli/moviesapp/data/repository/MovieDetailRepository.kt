package com.busenamli.moviesapp.data.repository

import com.busenamli.moviesapp.NetworkResult
import com.busenamli.moviesapp.data.model.CreditModel
import com.busenamli.moviesapp.data.model.MoviesDetailModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {

    suspend fun fetchMovieDetail(movieId: Int): Flow<NetworkResult<MoviesDetailModel>>
    suspend fun fetchMovieCredit(movieId: Int): Flow<NetworkResult<CreditModel>>
}