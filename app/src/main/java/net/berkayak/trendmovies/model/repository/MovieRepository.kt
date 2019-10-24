package net.berkayak.trendmovies.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import net.berkayak.trendmovies.model.Movie
import net.berkayak.trendmovies.model.service.MovieServiceManager
import net.berkayak.trendmovies.utilities.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {
    private var movie = MutableLiveData<Movie>()

    constructor(movieID: Int){
        refreshMovie(movieID)
    }

    fun getMovie(): MutableLiveData<Movie> {
        return movie
    }

    fun refreshMovie(movieID: Int){
        MovieServiceManager.getInstance()!!.getMovieDetail(movieID).enqueue(object :
            Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.isSuccessful)
                    movie.postValue(response.body())
                else
                    movie.postValue(null)
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                movie.postValue(null)
                Log.e(Const.TRENDS_TAG, t.message)
            }
        })
    }
}

