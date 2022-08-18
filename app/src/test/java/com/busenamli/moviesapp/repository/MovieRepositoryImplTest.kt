package com.busenamli.moviesapp.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.TestModel
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.repository.MovieRepositoryImpl
import com.busenamli.moviesapp.datasource.FakeMovieRemoteDataSource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    private lateinit var dataSource: FakeMovieRemoteDataSource
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = FakeMovieRemoteDataSource()
        repository = MovieRepositoryImpl(dataSource)
    }

    @Test
    fun `Movie Repository - Is Popular Movie List Return Same List`() {
        runTest {
            dataSource.fetchPopularMovies().collect {
                Truth.assertThat(it).isEqualTo(flowOf(PagingData.from(TestModel.movieListModel)))
            }
        }
    }

    @Test
    fun `Movie Repository - Is Movie List By Genre Return Same List`() {
        runTest {
            dataSource.fetchMoviesByGenre(1).collect {
                Truth.assertThat(it).isEqualTo(flowOf(PagingData.from(TestModel.movieListModel)))
            }
        }
    }

    @Test
    fun `Movie Repository - Genre List If Network Result Success`() {
        runTest {
            dataSource.networkError(false)
            dataSource.fetchGenreList().collect { networkResult ->
                if (networkResult is NetworkResult.Success) {
                    Truth.assertThat(networkResult.data.genreList)
                        .isEqualTo(TestModel.genreListModel)
                }
            }
        }
    }

    @Test
    fun `Movie Repository - Genre List If Network Result Error`() {
        runTest {
            dataSource.networkError(true)
            dataSource.fetchGenreList().collect { networkResult ->
                if (networkResult is NetworkResult.Error) {
                    Truth.assertThat(networkResult.exception).isEqualTo(TestModel.error)
                }
            }
        }
    }
}