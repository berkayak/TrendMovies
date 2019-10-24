package net.berkayak.trendmovies.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.berkayak.trendmovies.R
import net.berkayak.trendmovies.model.GenreList
import net.berkayak.trendmovies.model.TrendList
import net.berkayak.trendmovies.model.TrendListBean
import net.berkayak.trendmovies.model.service.IMovieService

class MovieRecyclerviewAdapter(val context: Context, var listOfMovies: TrendList, val genreList: GenreList, val listner: RecyclerViewClickListener): RecyclerView.Adapter<MovieRecyclerviewAdapter.MoviewViewHolder>(){

    fun setData(newTrendList:  TrendList){
        this.listOfMovies = newTrendList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviewViewHolder {
        var v = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MoviewViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listOfMovies.trendListBeans.size
    }

    override fun onBindViewHolder(holder: MoviewViewHolder,  position: Int) {
        holder.bindItems(position, listOfMovies.trendListBeans[position], listner)
    }

    inner class MoviewViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bindItems(pos: Int, moviewItem: TrendListBean, listner: RecyclerViewClickListener){
            //Picasso set image
            Picasso.get()?.
                load(IMovieService.MINI_POSTER_BASE + moviewItem.posterPath)?.
                error(R.drawable.preview)?.
                placeholder(R.drawable.preview)?.
                into(itemView.findViewById<ImageView>(R.id.miniPosterIV) as ImageView)

            //genres is writing from genrelist
            var genreSb = StringBuilder()
            moviewItem.genreİds.forEach { id ->
                genreList.genres.forEach { it ->
                    if(id == it.id)
                        genreSb.append("${it.name}, ")
                }
            }

            //other views filling
            itemView.findViewById<TextView>(R.id.titleTV).text = moviewItem.title
            itemView.findViewById<TextView>(R.id.releaseYearTV).text = moviewItem.releaseDate
            itemView.findViewById<TextView>(R.id.scoreTV).text = moviewItem.voteAverage.toString()
            itemView.findViewById<TextView>(R.id.genresTV).text = genreSb.deleteCharAt(genreSb.lastIndexOf(","))
            itemView.setOnClickListener{ listner.onClick(pos, moviewItem.id) }
        }
    }
}