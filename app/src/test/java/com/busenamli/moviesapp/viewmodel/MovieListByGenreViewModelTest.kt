package com.busenamli.moviesapp.viewmodel

import androidx.paging.PagingData
import com.busenamli.moviesapp.MainCoroutineRule
import com.busenamli.moviesapp.TestModel
import com.busenamli.moviesapp.repository.FakeMovieRepository
import com.busenamli.moviesapp.ui.viewmodel.MovieListByGenreViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieListByGenreViewModelTest {

    private lateinit var viewModel: MovieListByGenreViewModel
    private lateinit var repository: FakeMovieRepository

    @get:Rule
    val testRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = FakeMovieRepository()
        viewModel = MovieListByGenreViewModel(repository)
    }

    @Test
    fun `Movie List By Genre - Return Loading True If Initial Setup of View Model`() {
        val result = viewModel.uiState.value.isLoading
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `Movie List By Genre - Check Movie List`() = runTest {
        repository.networkError(false)
        repository.fetchMoviesByGenre(1).collectLatest {
            viewModel.fetchMoviesByGenre(1)
            val result = viewModel.uiState.value.movieList
            //assertTrue(result != null)
            Truth.assertThat(result).isEqualTo(PagingData.from(listOf(TestModel.movieDetailModel)))
        }
    }
}