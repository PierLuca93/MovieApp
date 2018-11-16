package it.android.luca.movieapp.detail.ui

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import com.bumptech.glide.Glide
import it.android.luca.movieapp.App
import it.android.luca.movieapp.BaseActivity
import it.android.luca.movieapp.R
import it.android.luca.movieapp.detail.presenter.DefaultDetailPresenter
import it.android.luca.movieapp.di.DaggerDetailComponent
import it.android.luca.movieapp.di.DetailModule
import it.android.luca.movieapp.network.MovieApi.Companion.IMAGE_URL
import it.android.luca.movieapp.repository.Movie
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject
import java.text.SimpleDateFormat


class DetailActivity : BaseActivity(), DefaultDetailPresenter.View, DynamicColorsActivity {

    @Inject
    lateinit var presenter: DefaultDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initDagger()
        initToolbar()

        val id = intent?.extras?.getString(MOVIE_ID)
        id?.let { presenter.fetchMovie(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clear()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initDagger() {
        DaggerDetailComponent.builder()
            .appComponent((application as App).getAppComponent())
            .detailModule(DetailModule(this))
            .build().inject(this)
    }

    override fun showMovie(item: Movie) {
        collapsing_toolbar.title = item.title
        val date = SimpleDateFormat("yyyy-MM-dd").parse(item.release_date)
        release_date.text = SimpleDateFormat("dd-MM-yyyy").format(date)
        description.text = item.overview
        Glide.with(this)
            .load(IMAGE_URL + item.poster_path)
            .into(MoviePosterTarget(poster, this))
    }

    override fun setTextColor(color: Int){
        toolbar.navigationIcon?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        collapsing_toolbar.setCollapsedTitleTextColor(color)
    }

    override fun setBackgroundColor(color: Int){
        collapsing_toolbar.setContentScrimColor(color)
    }

    companion object {

        val MOVIE_ID = "movie_id"

        fun createIntent(context: Context, id: String) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_ID, id)
            context.startActivity(intent)
        }
    }
}
