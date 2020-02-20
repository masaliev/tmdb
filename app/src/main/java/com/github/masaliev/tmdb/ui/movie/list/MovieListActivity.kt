package com.github.masaliev.tmdb.ui.movie.list

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.masaliev.tmdb.App
import com.github.masaliev.tmdb.R
import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.databinding.ActivityMovieListBinding
import com.github.masaliev.tmdb.ui.base.BaseActivity
import com.github.masaliev.tmdb.utils.views.EndlessRecyclerViewScrollListener
import com.github.masaliev.tmdb.utils.views.RecyclerViewItemPaddingDecorator
import javax.inject.Inject

class MovieListActivity : BaseActivity<ActivityMovieListBinding, MovieListPresenter>(),
    MovieListContract.View {

    @Inject
    lateinit var mPresenter: MovieListPresenter

    @Inject
    lateinit var mAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()

        mPresenter.attachView(this)
        mPresenter.viewIsReady()

    }

    private fun initViews() {

        mAdapter.onItemClickListener = object : MovieAdapter.OnItemClickListener {
            override fun onClick(post: Movie) {
                //@TODO open movie details page
            }
        }

        val layoutManager: RecyclerView.LayoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            } else {
                GridLayoutManager(this, 2)
            }

        viewDataBinding.recyclerView.apply {
            this.layoutManager = layoutManager
            addItemDecoration(
                RecyclerViewItemPaddingDecorator(
                    (8 * resources.displayMetrics.density).toInt(), //8dp in pixels
                    null
                )
            )
            adapter = mAdapter
            addOnScrollListener(
                object : EndlessRecyclerViewScrollListener(
                    layoutManager,
                    2
                ) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                        mPresenter.fetchNextPage()
                    }
                }
            )
        }
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
        if (mAdapter.itemCount == 0) {
            viewDataBinding.progress.visibility = View.VISIBLE
            viewDataBinding.recyclerView.visibility = View.GONE
        } else {
            mAdapter.setLoading()
        }
    }

    override fun hideLoading() {
        viewDataBinding.progress.visibility = View.GONE
        mAdapter.setLoading(false)
    }

    override fun populateMovies(movies: List<Movie>) {
        mAdapter.setItems(movies)
        checkMovieCount()
    }

    override fun addMovies(movies: List<Movie>) {
        mAdapter.addItems(movies)
        checkMovieCount()
    }

    private fun checkMovieCount() {
        if (mAdapter.itemCount == 0) {
            viewDataBinding.emptyResult.visibility = View.VISIBLE
            viewDataBinding.recyclerView.visibility = View.GONE
        } else {
            viewDataBinding.emptyResult.visibility = View.GONE
            viewDataBinding.recyclerView.visibility = View.VISIBLE
        }
    }

}