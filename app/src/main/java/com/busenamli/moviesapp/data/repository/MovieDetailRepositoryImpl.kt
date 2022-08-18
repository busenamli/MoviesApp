package com.busenamli.moviesapp.data.repository

import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSource
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.model.Credit
import com.busenamli.moviesapp.data.model.MovieDetail
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieDetailRepository {

    override suspend fun fetchMovieDetail(movieId: Int): NetworkResult<MovieDetail> {
        try {
            lateinit var response: NetworkResult<MovieDetail>
            movieRemoteDataSource.fetchMovieDetails(movieId).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        response = NetworkResult.Success(networkResult.data)
                    }
                    is NetworkResult.Error -> {
                        response = NetworkResult.Error(networkResult.exception)
                    }
                }
            }
            return response
        } catch (e: Exception) {
            return NetworkResult.Error(e.message.toString())
        }
    }

    override suspend fun fetchMovieCredit(movieId: Int): NetworkResult<Credit> {
        try {
            lateinit var response: NetworkResult<Credit>
            movieRemoteDataSource.fetchMovieCredits(movieId).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        response = NetworkResult.Success(networkResult.data)
                    }
                    is NetworkResult.Error -> {
                        response = NetworkResult.Error(networkResult.exception)
                    }
                }
            }
            return response
        } catch (e: Exception) {
            return NetworkResult.Error(e.message.toString())
        }
    }
}