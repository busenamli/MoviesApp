package com.busenamli.moviesapp.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.busenamli.moviesapp.TestModel
import com.busenamli.moviesapp.data.api.MoviesService
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSourceImpl
import com.busenamli.moviesapp.data.model.MovieDetail
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class MovieRemoteDataSourceImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var service: MoviesService

    private lateinit var dataSource: MovieRemoteDataSourceImpl
    private lateinit var responseSuccess: Response<MovieDetail>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = MovieRemoteDataSourceImpl(service, StandardTestDispatcher())
        responseSuccess = Response.success(TestModel.movieDetailModel)
    }

    @Test
    fun `Movie Data Source - Is Movie Return Same Movie`() =
        runTest {
            dataSource.fetchMovieDetails(1).collect{networkResult ->
                when(networkResult){
                    is NetworkResult.Success -> {
                        Truth.assertThat(networkResult.data).isEqualTo(flowOf((TestModel.movieListModel)))
                    }
                    is NetworkResult.Error -> {
                        Truth.assertThat(networkResult.exception).isEqualTo(TestModel.error)
                    }
                }
            }
        }
}