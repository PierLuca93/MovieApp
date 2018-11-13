package it.android.luca.movieapp.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat.startActivity
import android.text.TextUtils
import android.view.View
import android.view.View.*
import com.bumptech.glide.Glide
import it.android.luca.movieapp.App
import it.android.luca.movieapp.BaseActivity
import it.android.luca.movieapp.R
import it.android.luca.movieapp.detail.presenter.DefaultDetailPresenter
import it.android.luca.movieapp.di.DaggerDetailComponent
import it.android.luca.movieapp.di.DaggerHomeComponent
import it.android.luca.movieapp.di.DetailModule
import it.android.luca.movieapp.di.HomeModule
import it.android.luca.movieapp.home.presenter.DefaultHomePresenter
import it.android.luca.movieapp.network.MovieApi
import it.android.luca.movieapp.network.MovieApi.Companion.IMAGE_URL
import it.android.luca.movieapp.repository.Movie
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*
import javax.inject.Inject
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import com.bumptech.glide.request.target.ImageViewTarget
import android.support.v7.graphics.Palette
import android.provider.MediaStore.Images.Media.getBitmap




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
    }

    private fun initViews(){
        description.setOnClickListener {
            if(description.maxLines == 3) {
                description.maxLines = Int.MAX_VALUE
            } else {
                description.maxLines = 3
            }
        }
    }

    private fun initDagger(){
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
            .load(IMAGE_URL+item.poster_path)
            .into(object : ImageViewTarget<Drawable>(poster) {
                override fun setResource(resource: Drawable?) {
                    resource?.let {
                        setImage(it)
                        extractColor(it) }
                }

                private fun setImage(resource: Drawable) {
                    poster.setBackgroundDrawable(resource)
                }

                private fun extractColor(resource: Drawable) {
                    val b = (resource as BitmapDrawable).bitmap

                    Palette.from(b).generate { palette ->
                        val defaultColor = resources.getColor(R.color.colorPrimary)
                        val color = palette!!.getDominantColor(defaultColor)
                        collapsing_toolbar.setContentScrimColor(color)
                    }


                }
            })
    }

    companion object {

        val MOVIE_ID = "movie_id"

        fun createIntent(context: Context, id: String){
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_ID, id)
            context.startActivity(intent)
        }
    }
}
