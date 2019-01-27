package it.android.luca.movieapp.home.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import it.android.luca.movieapp.BasePresenterView
import it.android.luca.movieapp.network.MovieService
import it.android.luca.movieapp.repository.Movie

class DefaultHomePresenter(private val service: MovieService, private val view: View) :
    HomePresenter {

    private var homeFeed: BehaviorSubject<Int> = BehaviorSubject.create()
    private val subscription: CompositeDisposable = CompositeDisposable()

    init {
        subscription
            .add(homeFeed
                .subscribe {
                    service.getTopRated(it)
                        .filter { it != null }
                        .doFinally {
                            view.showLoading(false)
                        }
                        .subscribe(
                            {
                                view.showMovies(it!!.results)
                            },
                            {
                                it.message?.let { view.showError(it) }
                            })
                })
    }

    override fun dispose() {
        subscription.dispose()
    }

    override fun fetchMovies() {
        homeFeed.onNext(1)
    }

    override fun loadNextPage(page: Int) {
        homeFeed.onNext(page)
    }


    interface View: BasePresenterView {
        fun showMovies(items: List<Movie>)
    }
}