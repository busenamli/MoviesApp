package com.busenamli.moviesapp.data.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.model.GenresResponseModel
import com.busenamli.moviesapp.data.model.MovieModel
import com.busenamli.moviesapp.data.api.NetworkResult
import kotlinx.coroutines.flow.*

interface MovieRepository {
    suspend fun fetchPopularMovies(): Flow<PagingData<MovieModel>>
    suspend fun fetchGenreList(): Flow<NetworkResult<GenresResponseModel>>
    suspend fun fetchMoviesByGenre(genreId: Int): Flow<PagingData<MovieModel>>
}