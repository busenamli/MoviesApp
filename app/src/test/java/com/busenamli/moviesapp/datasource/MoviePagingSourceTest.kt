package com.busenamli.moviesapp.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.busenamli.moviesapp.TestModel
import com.busenamli.moviesapp.data.api.MoviesService
import com.busenamli.moviesapp.data.datasource.MoviePagingSource
import com.busenamli.moviesapp.data.model.MoviesResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class MoviePagingSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var service: MoviesService

    private lateinit var response: Response<MoviesResponse>
    private lateinit var pagingSource: MoviePagingSource

    private val movieList = listOf(
        TestModel.movieModel,
        TestModel.movieModel,
        TestModel.movieModel
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        pagingSource = MoviePagingSource(service)
        response = Response.success(MoviesResponse(movieList))
    }

    @Test
    fun `Paging Source - Get Items`() = runTest {
        Truth.assertThat(
            PagingSource.LoadResult.Page(
                data = response.body()!!.movieList,
                prevKey = null,
                nextKey = 1
            )
        )
            .isEqualTo(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 1,
                        placeholdersEnabled = false
                    )
                )
            )
    }
}