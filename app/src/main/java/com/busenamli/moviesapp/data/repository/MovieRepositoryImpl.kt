package com.busenamli.moviesapp.data.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSource
import com.busenamli.moviesapp.data.model.GenresResponseModel
import com.busenamli.moviesapp.data.model.MovieModel
import com.busenamli.moviesapp.data.api.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource): MovieRepository {

    override suspend fun fetchPopularMovies(): Flow<PagingData<MovieModel>> =
        movieRemoteDataSource.fetchPopularMovies()

    override suspend fun fetchGenreList(): Flow<NetworkResult<GenresResponseModel>> =
        movieRemoteDataSource.fetchGenreList()

    override suspend fun fetchMoviesByGenre(genreId: Int): Flow<PagingData<MovieModel>> =
        movieRemoteDataSource.fetchMoviesByGenre(genreId)
}