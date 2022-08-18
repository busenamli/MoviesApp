package com.busenamli.moviesapp.viewmodel

import androidx.paging.PagingData
import com.busenamli.moviesapp.MainCoroutineRule
import com.busenamli.moviesapp.TestModel
import com.busenamli.moviesapp.repository.FakeMovieRepository
import com.busenamli.moviesapp.ui.viewmodel.MovieListViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieListViewModelTest {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var repository: FakeMovieRepository

    @get:Rule
    val testRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = FakeMovieRepository()
        viewModel = MovieListViewModel(repository)
    }

    @Test
    fun `Movie List - Return Loading True If Initial Setup of View Model`() {
        val result = viewModel.uiState.value.isLoading
        assertThat(result).isTrue()
    }

    @Test
    fun `Movie List - Check isError True If Network Result Error`() {
        repository.networkError(true)
        viewModel.fetchGenreList()
        val result = viewModel.uiState.value.isError
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `Movie List - Check Error Message If Network Result Error`() {
        repository.networkError(true)
        viewModel.fetchGenreList()
        val message = viewModel.uiState.value.errorMessage
        val result = message?.get(message.lastIndex)?.message
        assertThat(TestModel.error).isEqualTo(result)
    }

    @Test
    fun `Movie List - Check Error Message If Network Result Success`() {
        repository.networkError(false)
        viewModel.fetchGenreList()
        val result = viewModel.uiState.value.errorMessage?.size
        assertEquals(0, result)
    }

    @Test
    fun `Movie List - Check Genre List If Network Result Success`() {
        repository.networkError(false)
        viewModel.fetchGenreList()
        val result = viewModel.uiState.value.genreList
        assertThat(result).isEqualTo(TestModel.genreListModel)
    }

    @Test
    fun `Movie List - Check Genre List If Network Result Error`() {
        repository.networkError(true)
        viewModel.fetchGenreList()
        val result = viewModel.uiState.value.genreList
        assertThat(result).isEqualTo(null)
    }

    @Test
    fun `Movie List - Is Movie List Return Same List`() = runTest {
        repository.networkError(false)
        repository.fetchPopularMovies().collectLatest {
            viewModel.fetchPopularMovies()
            val result = viewModel.uiState.value.movieList
            //assertTrue(result != null)
            assertThat(result).isEqualTo(PagingData.from(listOf(TestModel.movieDetailModel)))
        }
    }

    @Test
    fun `Movie List - Error Message Shown`() = runTest {
        repository.networkError(true)
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
        repository.networkError(false)
        viewModel.refreshList()
        val result = viewModel.uiState.value.isRefresh
        assertThat(result).isTrue()
    }
}