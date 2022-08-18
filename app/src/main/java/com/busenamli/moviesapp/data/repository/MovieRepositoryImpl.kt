package com.busenamli.moviesapp.data.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSource
import com.busenamli.moviesapp.data.model.GenreResponse
import com.busenamli.moviesapp.data.model.Movie
import com.busenamli.moviesapp.data.api.NetworkResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {

    override suspend fun fetchPopularMovies(): Flow<PagingData<Movie>> =
        movieRemoteDataSource.fetchPopularMovies()

    override suspend fun fetchGenreList(): NetworkResult<GenreResponse> {
        try {
            lateinit var response: NetworkResult<GenreResponse>
            movieRemoteDataSource.fetchGenreList().collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        response = NetworkResult.Success(networkResult.data)
                    }
                    is NetworkResult.Error -> {
                        response = NetworkResult.Error(networkResult.exception)
                    }
                }
            }
            return response
        } catch (e: Exception) {
            return NetworkResult.Error(e.message.toString())
        }
    }

    override suspend fun fetchMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> =
        movieRemoteDataSource.fetchMoviesByGenre(genreId)
}