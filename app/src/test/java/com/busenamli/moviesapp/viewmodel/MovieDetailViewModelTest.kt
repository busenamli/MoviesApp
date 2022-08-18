package com.busenamli.moviesapp.viewmodel

import com.busenamli.moviesapp.MainCoroutineRule
import com.busenamli.moviesapp.TestModel
import com.busenamli.moviesapp.repository.FakeMovieDetailRepository
import com.busenamli.moviesapp.ui.viewmodel.MovieDetailViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var repository: FakeMovieDetailRepository

    @get:Rule
    val testRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = FakeMovieDetailRepository()
        viewModel = MovieDetailViewModel(repository)
    }

    @Test
    fun `Movie Detail - Return Loading True If Initial Setup of View Model`() {
        val result = viewModel.uiState.value.isLoading
        assertThat(result).isTrue()
    }

    @Test
    fun `Movie Detail - Check Movie Detail`() {
        viewModel.fetchMovieDetail(1)
        val result = viewModel.uiState.value.movie
        //assertTrue(result != null)
        assertThat(TestModel.movieDetailModel).isEqualTo(result)
    }

    @Test
    fun `Movie Detail - Check isError True If Network Result Error`() {
        repository.networkError(true)
        viewModel.fetchMovieDetail(1)
        val result = viewModel.uiState.value.isError
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `Movie Detail - Check Error Message If Network Result Error`() {
        repository.networkError(true)
        viewModel.fetchMovieDetail(1)
        val message = viewModel.uiState.value.errorMessage
        val result = message?.get(message.lastIndex)?.message
        assertThat(TestModel.error).isEqualTo(result)
    }

    @Test
    fun `Movie Detail - Check Error Message If Network Result Success`() {
        repository.networkError(false)
        viewModel.fetchMovieDetail(1)
        val result = viewModel.uiState.value.errorMessage?.size
        assertEquals(0, result)
    }

    @Test
    fun `Movie Detail - Check Loading If Network Result Exists`() {
        viewModel.fetchMovieDetail(1)
        val result = viewModel.uiState.value.isLoading
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `Movie Detail - Check Cast List`() {
        viewModel.fetchMovieCredit(1)
        val result = viewModel.uiState.value.cast
        assertThat(result).isEqualTo(TestModel.creditModel.castModel)
    }
}