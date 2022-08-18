package com.busenamli.moviesapp.data.repository

import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.model.Credit
import com.busenamli.moviesapp.data.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun fetchMovieDetail(movieId: Int): NetworkResult<MovieDetail>
    suspend fun fetchMovieCredit(movieId: Int): NetworkResult<Credit>
}