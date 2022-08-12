package com.busenamli.moviesapp.di

import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSource
import com.busenamli.moviesapp.data.datasource.MovieRemoteDataSourceImpl
import com.busenamli.moviesapp.data.repository.MovieDetailRepository
import com.busenamli.moviesapp.data.repository.MovieDetailRepositoryImpl
import com.busenamli.moviesapp.data.repository.MovieRepository
import com.busenamli.moviesapp.data.repository.MovieRepositoryImpl
import com.busenamli.moviesapp.data.api.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideMovieRemoteDataSourceService(moviesService: MoviesService, @IoDispatcher dispatcher: CoroutineDispatcher): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(moviesService, dispatcher)
    }

    @Provides
    fun provideMovieRepository(movieRemoteDataSource: MovieRemoteDataSource): MovieRepository{
        return MovieRepositoryImpl(movieRemoteDataSource)
    }

    @Provides
    fun provideMovieDetailRepository(movieRemoteDataSource: MovieRemoteDataSource): MovieDetailRepository {
        return MovieDetailRepositoryImpl(movieRemoteDataSource)
    }
}