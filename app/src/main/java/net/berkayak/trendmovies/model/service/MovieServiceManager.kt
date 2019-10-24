package net.berkayak.trendmovies.model.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieServiceManager {

    companion object {
        private var instance: IMovieService? = null

        fun getInstance(): IMovieService? {
            if(instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl(IMovieService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IMovieService::class.java)
            }
            return instance
        }
    }
}