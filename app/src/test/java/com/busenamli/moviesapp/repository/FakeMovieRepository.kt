package com.busenamli.moviesapp.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.TestModel
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.model.GenreResponse
import com.busenamli.moviesapp.data.model.Movie
import com.busenamli.moviesapp.data.repository.MovieRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class FakeMovieRepository : MovieRepository {

    private var networkError = false

    fun networkError(value: Boolean) {
        networkError = value
    }

    override suspend fun fetchPopularMovies(): Flow<PagingData<Movie>> =
        flow { PagingData.from(TestModel.movieListModel) }

    override suspend fun fetchGenreList(): NetworkResult<GenreResponse> {
        if (networkError) {
            return NetworkResult.Error(TestModel.error)
        } else {
            return NetworkResult.Success(GenreResponse(TestModel.genreListModel))
        }
    }

    override suspend fun fetchMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> =
        flow { PagingData.empty<Movie>() }

    @Test
    fun `Movie Repository - Genre List - If Network Error Exists`() =
        runTest {
            networkError(true)
            val result = fetchGenreList()
            Truth.assertThat(result).isEqualTo(NetworkResult.Error(TestModel.error))
        }

    @Test
    fun `Movie Repository - Genre List - If Network Error Not Exists`() =
        runTest {
            networkError(false)
            val result = fetchGenreList()
            Truth.assertThat(result)
                .isEqualTo(NetworkResult.Success(GenreResponse(TestModel.genreListModel)))
        }
}