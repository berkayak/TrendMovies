package net.berkayak.trendmovies.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import net.berkayak.trendmovies.model.TrendList
import net.berkayak.trendmovies.model.service.MovieServiceManager
import net.berkayak.trendmovies.utilities.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendListRepository {
    private var trendList = MutableLiveData<TrendList>()

    constructor(page: Int){
        refreshList(page)
    }

    fun getMovieList(): MutableLiveData<TrendList>{
        return trendList
    }

    fun refreshList(page: Int = 1){
        MovieServiceManager.getInstance()!!.getTrendListWeekly(page).enqueue(object : Callback<TrendList> {
            override fun onResponse(call: Call<TrendList>, response: Response<TrendList>) {
                if (response.isSuccessful)
                    trendList.postValue(response.body())
                else
                    trendList.postValue(null)
            }
            override fun onFailure(call: Call<TrendList>, t: Throwable) {
                trendList.postValue(null)
                Log.e(Const.TRENDS_TAG, t.message)
            }
        })
    }
}