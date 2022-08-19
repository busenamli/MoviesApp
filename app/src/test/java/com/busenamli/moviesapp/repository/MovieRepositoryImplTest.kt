package com.busenamli.moviesapp.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.model.TestModel
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.model.GenreResponse
import com.busenamli.moviesapp.data.repository.MovieRepositoryImpl
import com.busenamli.moviesapp.datasource.FakeMovieRemoteDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    private lateinit var dataSource: FakeMovieRemoteDataSource
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setUp() {
        dataSource = FakeMovieRemoteDataSource()
        repository = MovieRepositoryImpl(dataSource)
    }

    @Test
    fun `Movie Repository - Is Genre List Return Same List If Network Result Success`() {
        runTest {
            dataSource.networkError(false)
            val result = repository.fetchGenreList()
            assertThat(result).isEqualTo(NetworkResult.Success(GenreResponse(TestModel.genreListModel)))
        }
    }

    @Test
    fun `Movie Repository - Genre List If Network Result Error`() {
        runTest {
            dataSource.networkError(true)
            val result = repository.fetchGenreList()
            assertThat(result).isEqualTo(NetworkResult.Error(TestModel.error))
        }
    }

    @Test
    fun `Movie Repository - Is Popular Movie List Return Same List`() {
        runTest {
            repository.fetchPopularMovies().collect {
                assertThat(it).isEqualTo(PagingData.from(TestModel.movieListModel))
            }
        }
    }

    @Test
    fun `Movie Repository - Is Movie List By Genre Return Same List`() {
        runTest {
            repository.fetchMoviesByGenre(1).collect {
                assertThat(it).isEqualTo(PagingData.from(TestModel.movieListModel))
            }
        }
    }
}