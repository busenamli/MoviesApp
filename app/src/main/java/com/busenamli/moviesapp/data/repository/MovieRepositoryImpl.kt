package com.busenamli.moviesapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.busenamli.moviesapp.NetworkResult
import com.busenamli.moviesapp.data.datasource.MoviePagingSource
import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSource
import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSourceImpl
import com.busenamli.moviesapp.data.model.MovieModel
import com.busenamli.moviesapp.network.MoviesService
import com.busenamli.moviesapp.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRepository {

    override suspend fun getPopularMovies(): Flow<PagingData<MovieModel>> =
        movieRemoteDataSourceImpl.getPopularMovies()
}
