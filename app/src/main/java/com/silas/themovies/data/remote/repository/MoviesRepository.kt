package com.silas.themovies.data.remote.repository

import com.silas.themovies.data.local.dao.MoviesDao
import com.silas.themovies.data.remote.service.MoviesService
import com.silas.themovies.model.dto.response.Movie
import com.silas.themovies.model.dto.request.MovieDetailsDto
import com.silas.themovies.model.dto.request.PagedListMoviesDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * You receive a request, request data and decide where, and how to get the request.
 *
 * @param service Single Service instance that will set up the request,
 * ask the Client to initialize the request and will listen to the request return to return to the caller
 * @param moviesDao Single instance of Dao that assembles a query,
 * asked a Database for the necessary data and will be listening to your response to return it
 *
 * @author Silas at 22/02/2020
 */
class MoviesRepository(private val service: MoviesService, private val moviesDao: MoviesDao) {

    suspend fun loadPopulars(pagedListMoviesDto: PagedListMoviesDto) =
        service.loadPopulars(
            pagedListMoviesDto.apiKey,
            pagedListMoviesDto.language,
            pagedListMoviesDto.page)

    suspend fun searchMovie(pagedListMovies: PagedListMoviesDto) =
        service.searchMovie(
            pagedListMovies.apiKey,
            pagedListMovies.language,
            pagedListMovies.page,
            pagedListMovies.search ?: "")

    suspend fun loadDetails(movieDetails: MovieDetailsDto) =
        service.loadDetails(
            movieDetails.idMovie,
            movieDetails.apiKey,
            movieDetails.language)

    suspend fun loadRelated(pagedListMovies: PagedListMoviesDto) =
        service.searchRelated(
            pagedListMovies.movieId,
            pagedListMovies.apiKey,
            pagedListMovies.language,
            pagedListMovies.page)

    suspend fun insertFavorite(vararg movie: Movie) = moviesDao.insertFavorite(*movie)

    suspend fun deleteFavorite(vararg movie: Movie)  = moviesDao.deleteFavorite(*movie)

    suspend fun loadFavoriteId(movieId: Long)  = moviesDao.loadFavoriteId(movieId)

    suspend fun loadFavorites()  = moviesDao.loadFavorites()

    suspend fun searchFavorites(query: String)  = moviesDao.searchFavorites(query)
}