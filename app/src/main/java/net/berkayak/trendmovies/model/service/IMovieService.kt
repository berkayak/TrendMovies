package net.berkayak.trendmovies.model.service

import net.berkayak.trendmovies.model.GenreList
import net.berkayak.trendmovies.model.Movie
import net.berkayak.trendmovies.model.TrendList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMovieService {

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "ab2a366c08405280632a081894b72544"

        const val MINI_POSTER_BASE = "https://image.tmdb.org/t/p/w138_and_h175_face"
        const val POSTER_BASE = "https://image.tmdb.org/t/p/w780"
    }

    @GET("trending/movie/day")
    fun getTrendListDaily(@Query("page")page: Int, @Query("api_key") apiKey: String = API_KEY): Call<TrendList>

    @GET("trending/movie/week")
    fun getTrendListWeekly(@Query("page")page: Int, @Query("api_key") apiKey: String = API_KEY): Call<TrendList>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") movieID: Int, @Query("api_key") apiKey: String = API_KEY): Call<Movie>

    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String = API_KEY): Call<GenreList>

}


/*
trend list
https://api.themoviedb.org/3/trending/movie/day?api_key=ab2a366c08405280632a081894b72544&page=3

movie detail
https://api.themoviedb.org/3/movie/555?api_key=ab2a366c08405280632a081894b72544&language=tr

genres
https://api.themoviedb.org/3/genre/movie/list?api_key=ab2a366c08405280632a081894b72544&language=en-US

poster path
https://image.tmdb.org/t/p/w250_and_h141_face/
https://image.tmdb.org/t/p/w600_and_h900_bestv2/
https://image.tmdb.org/t/p/w1400_and_h450_face/
https://image.tmdb.org/t/p/w300_and_h450_bestv2/
https://image.tmdb.org/t/p/w138_and_h175_face/
https://image.tmdb.org/t/p/w276_and_h350_face/
https://image.tmdb.org/t/p/w500/ -> poster_path
https://image.tmdb.org/t/p/w780/ -> backdrop_path


*/