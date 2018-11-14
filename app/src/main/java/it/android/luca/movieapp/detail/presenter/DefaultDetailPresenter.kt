package it.android.luca.movieapp.detail.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import it.android.luca.movieapp.BasePresenterView
import it.android.luca.movieapp.network.MovieService
import it.android.luca.movieapp.repository.Movie

class DefaultDetailPresenter(private val view: View, private val service: MovieService) :
    DetailPresenter {

    private var movieDetailSubject: BehaviorSubject<String> = BehaviorSubject.create()
    private val subscription: CompositeDisposable = CompositeDisposable()

    init {
        subscription
            .add(movieDetailSubject
                .subscribe {
                    service.getMovie(it)
                        .filter { it != null }
                        .doFinally { view.showLoading(false) }
                        .subscribe(
                            {
                                view.showMovie(it)
                            },
                            {
                                it.message?.let { view.showError(it) }
                            })
                })
    }

    fun clear() {
        subscription.clear()
    }

    override fun fetchMovie(id: String) {
        movieDetailSubject.onNext(id)
    }


    interface View : BasePresenterView {
        fun showMovie(item: Movie)
    }
}