package com.busenamli.moviesapp.data.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.model.MovieModel
import kotlinx.coroutines.flow.*

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<PagingData<MovieModel>>
}