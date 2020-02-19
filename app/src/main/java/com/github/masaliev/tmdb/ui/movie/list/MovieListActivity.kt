package com.github.masaliev.tmdb.ui.movie.list

import android.os.Bundle
import android.view.View
import com.github.masaliev.tmdb.App
import com.github.masaliev.tmdb.R
import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.databinding.ActivityMovieListBinding
import com.github.masaliev.tmdb.ui.base.BaseActivity
import javax.inject.Inject

class MovieListActivity : BaseActivity<ActivityMovieListBinding, MovieListPresenter>(),
    MovieListContract.View {

    @Inject
    lateinit var mPresenter: MovieListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()

        mPresenter.attachView(this)
        mPresenter.viewIsReady()

    }

    private fun initViews() {
        //@TODO set up recycler view
        //Use GridLayoutManager
        //Set column according to Activity.getResources().getConfiguration().orientation
    }

    override fun getPresenter(): MovieListPresenter = mPresenter


    override fun getLayoutId(): Int = R.layout.activity_movie_list

    override fun performDependencyInjection() {
        App[this].getMovieListComponent().inject(this)
    }

    override fun releaseActivityComponent() {
        App[this].clearMovieListComponent()
    }

    override fun showLoading() {
        //@TODO if adapter items size is zero, then change visibility of viewDataBinding.progress,
        // otherwise add loading item to adapter
    }

    override fun hideLoading() {
        viewDataBinding.progress.visibility = View.GONE
        //@TODO hide loading item in adapter
    }

    override fun populateMovies(movies: List<Movie>) {
        //@TODO set items to adapter
        //Check adapter items size
    }

    override fun addMovies(movies: List<Movie>) {
        //@TODO add items to adapter
        //Check adapter items size
    }

}