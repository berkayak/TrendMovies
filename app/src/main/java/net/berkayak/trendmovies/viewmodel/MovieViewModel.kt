package net.berkayak.trendmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import net.berkayak.trendmovies.model.Movie
import net.berkayak.trendmovies.model.repository.MovieRepository

class MovieViewModel(application: Application, movieID: Int): AndroidViewModel(application){
    private val repo = MovieRepository(movieID)
    private val movie = repo.getMovie()

    fun refresh(movieID: Int){
        repo.refreshMovie(movieID)
    }

    fun getMovie(): MutableLiveData<Movie>{
        return movie
    }

}