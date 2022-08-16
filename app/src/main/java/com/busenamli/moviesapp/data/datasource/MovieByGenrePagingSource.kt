package com.busenamli.moviesapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.busenamli.moviesapp.data.model.Movie
import com.busenamli.moviesapp.data.api.MoviesService
import com.busenamli.moviesapp.util.Constants
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieByGenrePagingSource @Inject constructor(
    private val moviesService: MoviesService,
    private val genreId: Int
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val pageNumber = params.key ?: 1
            val response = moviesService.fetchMoviesByGenreList(
                Constants.API_KEY,
                Constants.LANGUAGE,
                genreId,
                pageNumber
            )
            if (response.isSuccessful) {
                return LoadResult.Page(
                    data = response.body()!!.movieList,
                    prevKey = if (pageNumber == 1) null else pageNumber.minus(1),
                    nextKey = if (response.body()!!.movieList.isEmpty()) null else pageNumber.plus(1)
                )
            } else {
                return LoadResult.Error(Throwable(response.message()))
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}