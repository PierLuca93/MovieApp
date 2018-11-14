package it.android.luca.movieapp.detail.ui

import android.content.Context
import android.content.Intent
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
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable
import com.bumptech.glide.request.target.ImageViewTarget
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import it.android.luca.movieapp.R.id.collapsing_toolbar
import it.android.luca.movieapp.R.id.poster


class DetailActivity : BaseActivity(), DefaultDetailPresenter.View {

    @Inject
    lateinit var presenter: DefaultDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initDagger()
        initViews()
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
//        collapsing_toolbar.isTitleEnabled = false
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initViews() {
        description.setOnClickListener {
            if (description.maxLines == 3) {
                description.maxLines = Int.MAX_VALUE
            } else {
                description.maxLines = 3
            }
        }
    }

    private fun initDagger() {
        DaggerDetailComponent.builder()
            .appComponent((application as App).getAppComponent())
            .detailModule(DetailModule(this))
            .build().inject(this)
    }

    override fun showMovie(item: Movie) {
        collapsing_toolbar.title = item.title
        release_date.text = item.release_date
        description.text = item.overview
        Glide.with(this)
            .load(IMAGE_URL + item.poster_path)
            .into(MoviePosterTarget(poster, this))
    }

    class MoviePosterTarget(val poster: ImageView, val activity: DetailActivity) : ImageViewTarget<Drawable>(poster) {
        override fun setResource(resource: android.graphics.drawable.Drawable?) {
            resource?.let {
                setImage(it)
                extractColor(it)
            }
        }

        private fun setImage(resource: android.graphics.drawable.Drawable) {
            poster.setBackgroundDrawable(resource)
        }

        private fun extractColor(resource: android.graphics.drawable.Drawable) {
            val b = (resource.current as BitmapDrawable).bitmap

            Palette.from(b).generate { palette ->
                val defaultColor = activity.resources.getColor(R.color.colorPrimary)
                var color = 0
                color = palette!!.getDominantColor(defaultColor)
                activity.collapsing_toolbar.setContentScrimColor(color)
            }

        }
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
