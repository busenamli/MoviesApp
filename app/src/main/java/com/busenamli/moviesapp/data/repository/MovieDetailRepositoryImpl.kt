package com.busenamli.moviesapp.data.repository

import com.busenamli.moviesapp.NetworkResult
import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSourceImpl
import com.busenamli.moviesapp.data.model.CreditModel
import com.busenamli.moviesapp.data.model.MoviesDetailModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(private val movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieDetailRepository {

    override suspend fun fetchMovieDetail(movieId: Int): Flow<NetworkResult<MoviesDetailModel>> =
        movieRemoteDataSourceImpl.fetchMovieDetails(movieId)

    override suspend fun fetchMovieCredit(movieId: Int): Flow<NetworkResult<CreditModel>> =
        movieRemoteDataSourceImpl.fetchMovieCredits(movieId)
}