package com.busenamli.moviesapp.data.repository

import androidx.paging.PagingData
import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSourceImpl
import com.busenamli.moviesapp.data.model.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRepository {

    override suspend fun getPopularMovies(): Flow<PagingData<MovieModel>> =
        movieRemoteDataSourceImpl.getPopularMovies()
}
