package net.berkayak.trendmovies.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import net.berkayak.trendmovies.model.GenreList
import net.berkayak.trendmovies.model.service.MovieServiceManager
import net.berkayak.trendmovies.utilities.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreRepository {
    private var genres = MutableLiveData<GenreList>()

    constructor(){
        MovieServiceManager.getInstance()!!.getGenres().enqueue(object : Callback<GenreList> {
            override fun onResponse(call: Call<GenreList>, response: Response<GenreList>) {
                if (response.isSuccessful)
                    genres.postValue(response.body())
                else
                    genres.postValue(null)
            }

            override fun onFailure(call: Call<GenreList>, t: Throwable) {
                genres.postValue(null)
                Log.e(Const.TRENDS_TAG, t.message)
            }
        })
    }

    fun getGenres(): MutableLiveData<GenreList>{
        return genres
    }
}