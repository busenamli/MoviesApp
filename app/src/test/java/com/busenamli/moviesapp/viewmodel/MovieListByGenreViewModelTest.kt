package com.busenamli.moviesapp.viewmodel

import com.busenamli.moviesapp.MainCoroutineRule
import com.busenamli.moviesapp.data.repository.MovieRepositoryImpl
import com.busenamli.moviesapp.datasource.FakeMovieRemoteDataSource
import com.busenamli.moviesapp.ui.viewmodel.MovieListByGenreViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieListByGenreViewModelTest {

    private lateinit var viewModel: MovieListByGenreViewModel
    private lateinit var repository: MovieRepositoryImpl
    private lateinit var dataSource: FakeMovieRemoteDataSource

    @get:Rule
    val testRule = MainCoroutineRule()

    @Before
    fun setup() {
        dataSource = FakeMovieRemoteDataSource()
        repository = MovieRepositoryImpl(dataSource)
        viewModel = MovieListByGenreViewModel(repository)
    }

    @Test
    fun `Movie List By Genre - Return Loading True If Initial Setup of View Model`() {
        val result = viewModel.uiState.value.isLoading
        assertThat(result).isTrue()
    }

    @Test
    fun `Movie List By Genre - Check Movie List`() = runTest {
        viewModel.fetchMoviesByGenre(1)
        val result = viewModel.uiState.value.movieList
        assertThat(result != null).isTrue()
    }

    @Test
    fun `Movie List By Genre - Return Loading False If Movie List Not Null`() = runTest {
        viewModel.networkCheck(true)
        viewModel.fetchMoviesByGenre(1)
        viewModel.uiState.value.movieList
        val result = viewModel.uiState.value.isLoading
        assertThat(result).isFalse()
    }

    @Test
    fun `Movie List By Genre - Return Network Connection False If Network Check False`() {
        viewModel.networkCheck(false)
        val result = viewModel.uiState.value.isNetworkConnection
        assertThat(result).isFalse()
    }

    @Test
    fun `Movie List By Genre - Return Network Connection True If Network Check False`() {
        viewModel.networkCheck(true)
        val result = viewModel.uiState.value.isNetworkConnection
        assertThat(result).isTrue()
    }

    @Test
    fun `Movie List By Genre - Return Error True If Network Check False`() {
        viewModel.networkCheck(false)
        val result = viewModel.uiState.value.isError
        assertThat(result).isTrue()
    }

    @Test
    fun `Movie List By Genre - Return Error False If Network Check True`() {
        viewModel.networkCheck(true)
        val result = viewModel.uiState.value.isError
        assertThat(result).isFalse()
    }

    @Test
    fun `Movie List By Genre - Error Message Not Null If Network Check False`() {
        viewModel.networkCheck(false)
        val result = viewModel.uiState.value.errorMessage
        assertThat(result != null).isTrue()
    }
}