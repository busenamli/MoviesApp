package com.busenamli.moviesapp.viewmodel

import com.busenamli.moviesapp.MainCoroutineRule
import com.busenamli.moviesapp.data.repository.MovieRepositoryImpl
import com.busenamli.moviesapp.datasource.FakeMovieRemoteDataSource
import com.busenamli.moviesapp.model.TestModel
import com.busenamli.moviesapp.ui.viewmodel.MovieListViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieListViewModelTest {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var repository: MovieRepositoryImpl
    private lateinit var dataSource: FakeMovieRemoteDataSource

    @get:Rule
    val testRule = MainCoroutineRule()

    @Before
    fun setup() {
        dataSource = FakeMovieRemoteDataSource()
        repository = MovieRepositoryImpl(dataSource)
        viewModel = MovieListViewModel(repository)
    }

    @Test
    fun `Movie List - Return Loading True If Initial Setup of View Model`() {
        val result = viewModel.uiState.value.isLoading
        assertThat(result).isTrue()
    }

    @Test
    fun `Movie List - Check isError True If Network Result Error`() {
        dataSource.networkError(true)
        viewModel.fetchGenreList()
        val result = viewModel.uiState.value.isError
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `Movie List - Check Error Message If Network Result Error`() {
        dataSource.networkError(true)
        viewModel.fetchGenreList()
        val message = viewModel.uiState.value.errorMessage
        val result = message?.get(message.lastIndex)?.message
        assertThat(TestModel.error).isEqualTo(result)
    }

    @Test
    fun `Movie List - Check Error Message If Network Result Success`() {
        dataSource.networkError(false)
        viewModel.fetchGenreList()
        val result = viewModel.uiState.value.errorMessage?.size
        assertEquals(0, result)
    }

    @Test
    fun `Movie List - Check Genre List If Network Result Success`() {
        dataSource.networkError(false)
        viewModel.fetchGenreList()
        val result = viewModel.uiState.value.genreList
        assertThat(result).isEqualTo(TestModel.genreListModel)
    }

    @Test
    fun `Movie List - Check Genre List If Network Result Error`() {
        dataSource.networkError(true)
        viewModel.fetchGenreList()
        val result = viewModel.uiState.value.genreList
        assertThat(result).isEqualTo(null)
    }

    @Test
    fun `Movie List - Check Movie List`() = runTest {
        dataSource.networkError(false)
        viewModel.fetchPopularMovies()
        val result = viewModel.uiState.value.movieList
        assertThat(result != null).isTrue()
    }

    @Test
    fun `Movie List - Check Error If Movie List Not Null`() = runTest {
        dataSource.networkError(false)
        viewModel.fetchPopularMovies()
        val result = viewModel.uiState.value.isError
        assertThat(result).isFalse()
    }

    @Test
    fun `Movie List - Error Message Shown`() = runTest {
        dataSource.networkError(true)
        viewModel.fetchGenreList()
        val message = viewModel.uiState.value.errorMessage
        assertThat(message?.size).isEqualTo(1)
        val result = message?.get(message.lastIndex)
        result?.let {
            viewModel.errorMessageShown(result)
        }
        assertThat(message?.size).isEqualTo(0)
    }

    @Test
    fun `Movie List - Is Refresh Working`() = runTest {
        dataSource.networkError(false)
        viewModel.refreshList()
        val result = viewModel.uiState.value.isRefresh
        assertThat(result).isTrue()
    }
}