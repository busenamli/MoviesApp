package com.busenamli.moviesapp.repository

import com.busenamli.moviesapp.model.TestModel
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.model.*
import com.busenamli.moviesapp.data.repository.MovieDetailRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FakeMovieDetailRepository : MovieDetailRepository {

    private var networkError = false

    fun networkError(value: Boolean) {
        networkError = value
    }

    override suspend fun fetchMovieDetail(movieId: Int): NetworkResult<MovieDetail> {
        if (networkError) {
            return NetworkResult.Error(TestModel.error)
        } else {
            return NetworkResult.Success(TestModel.movieDetailModel)
        }
    }

    override suspend fun fetchMovieCredit(movieId: Int): NetworkResult<Credit> {
        if (networkError) {
            return NetworkResult.Error(TestModel.error)
        } else {
            return NetworkResult.Success(TestModel.creditModel)
        }
    }
}