package com.busenamli.moviesapp.repository

import com.busenamli.moviesapp.model.TestModel
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.repository.MovieDetailRepositoryImpl
import com.busenamli.moviesapp.datasource.FakeMovieRemoteDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailRepositoryImplTest {

    private lateinit var dataSource: FakeMovieRemoteDataSource
    private lateinit var repository: MovieDetailRepositoryImpl

    @Before
    fun setUp() {
        dataSource = FakeMovieRemoteDataSource()
        repository = MovieDetailRepositoryImpl(dataSource)
    }

    @Test
    fun `Movie Detail Repository -  Is Movie Detail Return Same Data If Network Result Success`() {
        runTest {
            dataSource.networkError(false)
            val result = repository.fetchMovieDetail(1)
            assertThat(result).isEqualTo(NetworkResult.Success(TestModel.movieDetailModel))
        }
    }

    @Test
    fun `Movie Detail Repository - Movie Detail - Return Error If Network Result Error`() {
        runTest {
            dataSource.networkError(true)
            val result = repository.fetchMovieDetail(1)
            assertThat(result).isEqualTo(NetworkResult.Error(TestModel.error))
        }
    }

    @Test
    fun `Movie Detail Repository - Is Movie Credit Return Same Data If Network Result Success`() {
        runTest {
            dataSource.networkError(false)
            val result = repository.fetchMovieCredit(1)
            assertThat(result).isEqualTo(NetworkResult.Success(TestModel.creditModel))
        }
    }

    @Test
    fun `Movie Detail Repository - Movie Credit -Return Error If Network Result Error`() {
        runTest {
            dataSource.networkError(true)
            val result = repository.fetchMovieCredit(1)
            assertThat(result).isEqualTo(NetworkResult.Error(TestModel.error))
        }
    }
}