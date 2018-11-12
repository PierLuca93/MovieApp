package it.android.luca.movieapp.home.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.support.v7.widget.RecyclerView
import it.android.luca.movieapp.App
import it.android.luca.movieapp.BaseActivity
import it.android.luca.movieapp.home.presenter.DefaultHomePresenter
import it.android.luca.movieapp.R
import it.android.luca.movieapp.di.*
import it.android.luca.movieapp.repository.Movie
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject





class HomeActivity : BaseActivity(), DefaultHomePresenter.View{


    @Inject
    lateinit var presenter: DefaultHomePresenter
    private var column: Int = 2
    private var homeLayoutManager: GridLayoutManager? = null
    private var adapter: HomeMoviesAdapter? = null
    private var state: LinearLayoutManager.SavedState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initDagger()
        initViews()
        presenter.fetchMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
//        presenter.clear()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("position", movie_list.layoutManager?.onSaveInstanceState())
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        state = savedInstanceState?.getParcelable("position")
    }


    private fun initViews(){
        homeLayoutManager = GridLayoutManager(this, column)
        movie_list.layoutManager = homeLayoutManager
        movie_list.setHasFixedSize(true)
        adapter = HomeMoviesAdapter(presenter)
        movie_list.adapter = adapter
        movie_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(homeLayoutManager!!.findLastCompletelyVisibleItemPosition() >= adapter!!.itemCount-5){
                    presenter.loadNextPage(adapter!!.itemCount/20+1)
                }
            }
        })
    }

    private fun initDagger(){
        DaggerHomeComponent.builder()
            .appComponent((application as App).getAppComponent())
            .homeModule(HomeModule(this))
            .build().inject(this)
    }

    override fun showMovies(items: List<Movie>) {
        adapter?.addItems(items)
        state?.let { movie_list.layoutManager?.onRestoreInstanceState(it) }
        state = null
    }

}
