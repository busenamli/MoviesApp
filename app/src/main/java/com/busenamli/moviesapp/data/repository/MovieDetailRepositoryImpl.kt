package com.busenamli.moviesapp.data.repository

import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSource
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.model.Credit
import com.busenamli.moviesapp.data.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieDetailRepository {

    override suspend fun fetchMovieDetail(movieId: Int): Flow<NetworkResult<MovieDetail>> =
        movieRemoteDataSource.fetchMovieDetails(movieId)

    override suspend fun fetchMovieCredit(movieId: Int): Flow<NetworkResult<Credit>> =
        movieRemoteDataSource.fetchMovieCredits(movieId)
}