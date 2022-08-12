package com.busenamli.moviesapp.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.busenamli.moviesapp.data.model.*
import com.busenamli.moviesapp.di.IoDispatcher
import com.busenamli.moviesapp.data.api.NetworkResult
import com.busenamli.moviesapp.data.api.MoviesService
import com.busenamli.moviesapp.util.Constants.API_KEY
import com.busenamli.moviesapp.util.Constants.LANGUAGE
import com.busenamli.moviesapp.util.Constants.PAGING_PAGE_SIZE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val moviesService: MoviesService, @IoDispatcher private val ioDispatcher: CoroutineDispatcher): MovieRemoteDataSource {

    override suspend fun fetchPopularMovies(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGING_PAGE_SIZE)
        ) {
            MoviePagingSource(moviesService)
        }.flow.flowOn(ioDispatcher)
    }

    override suspend fun fetchMovieDetails(movieId: Int): Flow<NetworkResult<MovieDetailModel>> =
        flow {
            try {
                val response = moviesService.fetchMovieDetail(movieId, API_KEY, LANGUAGE)
                if (response.isSuccessful){
                    emit(NetworkResult.Success(response.body()!!))
                }else{
                    emit(NetworkResult.Error(response.message()))
                }
            }catch (e: IOException){
                emit(NetworkResult.Error(e.message!!))
            }
        }.flowOn(ioDispatcher)

    override suspend fun fetchMovieCredits(movieId: Int): Flow<NetworkResult<CreditModel>> =
        flow {
            try {
                val response = moviesService.fetchMovieCredits(movieId, API_KEY, LANGUAGE)
                if (response.isSuccessful){
                    emit(NetworkResult.Success(response.body()!!))
                }else{
                    emit(NetworkResult.Error(response.message()))
                }
            }catch (e: IOException){
                emit(NetworkResult.Error(e.message!!))
            }
        }.flowOn(ioDispatcher)

    override suspend fun fetchGenreList(): Flow<NetworkResult<GenresResponseModel>> =
        flow {
            try {
                val response = moviesService.fetchGenreList(API_KEY, LANGUAGE)
                if (response.isSuccessful){
                    emit(NetworkResult.Success(response.body()!!))
                }else{
                    emit(NetworkResult.Error(response.message()))
                }
            }catch (e: IOException){
                emit(NetworkResult.Error(e.message!!))
            }
        }.flowOn(ioDispatcher)

    override suspend fun fetchMoviesByGenre(genreId: Int): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGING_PAGE_SIZE)
        ) {
            MovieByGenrePagingSource(moviesService, genreId)
        }.flow.flowOn(ioDispatcher)
    }
}