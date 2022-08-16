package com.busenamli.moviesapp.data.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.model.GenreResponse
import com.busenamli.moviesapp.data.model.Movie
import com.busenamli.moviesapp.data.api.NetworkResult
import kotlinx.coroutines.flow.*

interface MovieRepository {
    suspend fun fetchPopularMovies(): Flow<PagingData<Movie>>
    suspend fun fetchGenreList(): Flow<NetworkResult<GenreResponse>>
    suspend fun fetchMoviesByGenre(genreId: Int): Flow<PagingData<Movie>>
}