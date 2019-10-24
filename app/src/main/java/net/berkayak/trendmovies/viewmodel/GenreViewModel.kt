package net.berkayak.trendmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import net.berkayak.trendmovies.model.Genre
import net.berkayak.trendmovies.model.GenreList
import net.berkayak.trendmovies.model.repository.GenreRepository

class GenreViewModel(application: Application): AndroidViewModel(application) {
    private val repo = GenreRepository()
    private val genres = repo.getGenres()

    fun getAll(): MutableLiveData<GenreList>{
        return genres
    }

    fun getGenreById(id: Int): Genre? {
        return genres.value?.genres?.find { it -> it.id == id }
    }

    fun getGenreByName(name: String): Genre?{
        return genres.value?.genres?.find { it -> it.name == name}
    }
}