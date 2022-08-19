package com.busenamli.moviesapp.datasource

import androidx.paging.PagingData
import com.busenamli.moviesapp.model.TestModel
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSource
import com.busenamli.moviesapp.data.model.Credit
import com.busenamli.moviesapp.data.model.GenreResponse
import com.busenamli.moviesapp.data.model.MovieDetail
import com.busenamli.moviesapp.data.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class FakeMovieRemoteDataSource : MovieRemoteDataSource {

    private var networkError = false

    fun networkError(value: Boolean) {
        networkError = value
    }

    override suspend fun fetchPopularMovies(): Flow<PagingData<Movie>> =
        flow { PagingData.from(TestModel.movieListModel) }

    override suspend fun fetchMovieDetails(movieId: Int): Flow<NetworkResult<MovieDetail>> =
        flow {
            if (networkError) {
                emit(NetworkResult.Error(TestModel.error))
            } else {
                emit(NetworkResult.Success(TestModel.movieDetailModel))
            }
        }

    override suspend fun fetchMovieCredits(movieId: Int): Flow<NetworkResult<Credit>> =
        flow {
            if (networkError) {
                emit(NetworkResult.Error(TestModel.error))
            } else {
                emit(NetworkResult.Success(TestModel.creditModel))
            }
        }

    override suspend fun fetchGenreList(): Flow<NetworkResult<GenreResponse>> =
        flow {
            if (networkError) {
                emit(NetworkResult.Error(TestModel.error))
            } else {
                emit(NetworkResult.Success(GenreResponse(TestModel.genreListModel)))
            }
        }

    override suspend fun fetchMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> =
        flow { PagingData.from(TestModel.movieListModel) }
}