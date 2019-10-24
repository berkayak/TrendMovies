package net.berkayak.trendmovies.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso

import net.berkayak.trendmovies.R
import net.berkayak.trendmovies.model.Movie
import net.berkayak.trendmovies.model.service.IMovieService
import net.berkayak.trendmovies.utilities.Const
import net.berkayak.trendmovies.viewmodel.MovieViewModel
import java.lang.StringBuilder

class DetailFragment : Fragment(), DetailTrigger{
    lateinit var movieVM : MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = inflater.inflate(R.layout.fragment_detail, container, false)

        init(fragmentView)

        return fragmentView
    }

    override fun movieSelected(movieID: Int) {
        movieVM.refresh(movieID)
    }

    private fun init(v: View) {
        var id = arguments?.getInt(Const.KEY_MOVIE_ID, -1)
        if (id != null &&  id != -1)
            movieVM = MovieViewModel(activity!!.application, id!!)
        else
        movieVM = MovieViewModel(activity!!.application, 475557)

        movieVM.getMovie().observe(this, Observer {
            setMovieView(it, v)
        })
    }

    fun setMovieView(m: Movie, v: View){
        Picasso.get().load(IMovieService.POSTER_BASE + m.backdropPath)
            .placeholder(R.drawable.themoviedb)
            .error(R.drawable.themoviedb)
            .fit()
            .centerInside()
            .into(v.findViewById(R.id.moviePosterIV) as ImageView)

        v.findViewById<TextView>(R.id.titleTV).text = m.title
        var genreSb = StringBuilder()
        m.genres.forEach {
            genreSb.append("${it.name}, ")
        }
        v.findViewById<TextView>(R.id.genresTV).text = genreSb.deleteCharAt(genreSb.lastIndexOf(","))
        v.findViewById<TextView>(R.id.releaseYearTV).text = m.releaseDate
        v.findViewById<TextView>(R.id.overviewTV).text = m.overview
        v.findViewById<TextView>(R.id.scoreTV).text = m.voteAverage.toString()
    }

    fun getDetailTrigger(): DetailTrigger{
        return this
    }
}
