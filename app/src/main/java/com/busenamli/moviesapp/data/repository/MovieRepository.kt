package com.busenamli.moviesapp.data.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.NetworkResult
import com.busenamli.moviesapp.data.model.MovieModel
import kotlinx.coroutines.flow.*
import retrofit2.Response

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<PagingData<MovieModel>>
}