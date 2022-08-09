package com.busenamli.moviesapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.busenamli.moviesapp.data.model.MovieModel
import com.busenamli.moviesapp.util.Constants.API_KEY
import com.busenamli.moviesapp.util.Constants.LANGUAGE
import com.busenamli.moviesapp.network.MoviesService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource @Inject constructor(private val moviesService: MoviesService): PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        try {
            val pageNumber = params.key ?: 1
            val response = moviesService.fetchPopularMovies(API_KEY, LANGUAGE, pageNumber)
            if (response.isSuccessful){
                return LoadResult.Page(
                    data = response.body()!!.movieList,
                    prevKey = if (pageNumber == 1) null else pageNumber.minus(1),
                    nextKey = if (response.body()!!.movieList.isEmpty()) null else pageNumber.plus(1)
                )
            }else{
                return LoadResult.Error(Throwable(response.message()))
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }
    }
}