package net.berkayak.trendmovies.view


import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import net.berkayak.trendmovies.R
import net.berkayak.trendmovies.view.adapter.MovieRecyclerviewAdapter
import net.berkayak.trendmovies.view.adapter.RecyclerViewClickListener
import net.berkayak.trendmovies.viewmodel.GenreViewModel
import net.berkayak.trendmovies.viewmodel.TrendListViewModel

class ListFragment : Fragment() {
    lateinit var trendlistVM: TrendListViewModel
    lateinit var genreVM : GenreViewModel
    var pageNumber = 1
    lateinit var refreshLayout : SwipeRefreshLayout
    lateinit var movieRv: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = inflater.inflate(R.layout.fragment_list, container, false)

        init(fragmentView)

        return fragmentView
    }

    private fun init(v: View) {
        genreVM = GenreViewModel(activity!!.application)
        trendlistVM = TrendListViewModel(activity!!.application, pageNumber)
        movieRv = v.findViewById<RecyclerView>(R.id.movieRV)
        movieRv.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)

        refreshLayout = v.findViewById<SwipeRefreshLayout>(R.id.refresherSR)
        refreshLayout.setOnRefreshListener {
            pageNumber++
            trendlistVM.refresh(pageNumber)
        }
        startObserveGenres(v)
    }

    fun startObserveGenres(v: View){
        genreVM.getAll().observe(this, Observer { t ->
            startObserveList(v)
        })
    }

    fun startObserveList(v: View){
        trendlistVM.getTrendList().observe(this, Observer {
            if (movieRv.adapter == null)
                movieRv.adapter = MovieRecyclerviewAdapter(context!!, it, genreVM.getAll().value!!, rvListener)
            else
                (movieRv.adapter as MovieRecyclerviewAdapter).setData(it)
            refreshLayout.isRefreshing = false
            Toast.makeText(context!!, "Page: $pageNumber", Toast.LENGTH_SHORT).show()
        })
    }

    var rvListener = object : RecyclerViewClickListener {
        override fun onClick(pos: Int, itemId: Int) {
            (activity as MainActivity).listItemSelected(itemId)
        }
    }
}
