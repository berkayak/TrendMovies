package net.berkayak.trendmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import net.berkayak.trendmovies.model.TrendList
import net.berkayak.trendmovies.model.repository.TrendListRepository

class TrendListViewModel(application: Application, page: Int = 1): AndroidViewModel(application) {
    private val repo = TrendListRepository(page)
    private val list = repo.getMovieList()


    fun refresh(page: Int = 1){
        repo.refreshList(page)
    }

    fun getTrendList(): MutableLiveData<TrendList>{
        return list
    }
}