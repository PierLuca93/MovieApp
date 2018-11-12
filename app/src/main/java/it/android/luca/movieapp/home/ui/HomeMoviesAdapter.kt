package it.android.luca.movieapp.home.ui

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import it.android.luca.movieapp.R
import it.android.luca.movieapp.repository.Movie
import it.android.luca.movieapp.detail.ui.DetailActivity
import it.android.luca.movieapp.home.presenter.DefaultHomePresenter
import it.android.luca.movieapp.network.MovieApi.Companion.IMAGE_URL


class HomeMoviesAdapter(val presenter: DefaultHomePresenter) : RecyclerView.Adapter<HomeMoviesAdapter.MovieHolder>() {


    companion object {
        private var homeList: ArrayList<Movie> = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        return MovieHolder(view, parent.context)
    }

    override fun getItemCount(): Int = homeList.size


    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindId(homeList[position].id.toString())
        Glide.with(holder.context)
            .load(IMAGE_URL + homeList[position].poster_path)
            .into(holder.poster)
    }

    fun addItems(movies: List<Movie>) {
        val oldcount = itemCount
        if(!homeList.containsAll(movies)) {
            homeList.addAll(movies)
            notifyDataSetChanged()
        }
    }


    class MovieHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        val poster: AppCompatImageView = itemView.findViewById(R.id.poster)
        lateinit var id: String

        init {
            itemView.setOnClickListener(this)
        }

        fun bindId(id: String) {
            this.id = id
        }

        override fun onClick(v: View?) {
            DetailActivity.createIntent(context, id)
        }

    }

}

