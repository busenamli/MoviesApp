package com.busenamli.moviesapp.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.busenamli.moviesapp.network.NetworkResult
import com.busenamli.moviesapp.data.model.CreditModel
import com.busenamli.moviesapp.data.model.MovieModel
import com.busenamli.moviesapp.data.model.MoviesDetailModel
import com.busenamli.moviesapp.network.MoviesService
import com.busenamli.moviesapp.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val moviesService: MoviesService): MovieRemoteDataSource {

    override suspend fun getPopularMovies(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGING_PAGE_SIZE)
        ) {
            MoviePagingSource(moviesService)
        }.flow
    }

    override suspend fun fetchMovieDetails(movieId: Int): Flow<NetworkResult<MoviesDetailModel>> =
        flow {
            try {
                val response = moviesService.fetchMovieDetail(movieId, Constants.API_KEY, Constants.LANGUAGE)
                if (response.isSuccessful){
                    emit(NetworkResult.Success(response.body()!!))
                }else{
                    emit(NetworkResult.Error(response.message()))
                }
            }catch (e: IOException){
                emit(NetworkResult.Error(e.message!!))
            }
        }

    override suspend fun fetchMovieCredits(movieId: Int): Flow<NetworkResult<CreditModel>> =
        flow {
            try {
                val response = moviesService.fetchMovieCredits(movieId, Constants.API_KEY, Constants.LANGUAGE)
                if (response.isSuccessful){
                    emit(NetworkResult.Success(response.body()!!))
                }else{
                    emit(NetworkResult.Error(response.message()))
                }
            }catch (e: IOException){
                emit(NetworkResult.Error(e.message!!))
            }
        }
}