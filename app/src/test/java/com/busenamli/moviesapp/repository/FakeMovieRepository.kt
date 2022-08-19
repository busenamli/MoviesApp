package com.busenamli.moviesapp.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.model.TestModel
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.model.GenreResponse
import com.busenamli.moviesapp.data.model.Movie
import com.busenamli.moviesapp.data.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
        flow { PagingData.from(TestModel.movieListModel) }
}