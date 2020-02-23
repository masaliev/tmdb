package com.github.masaliev.tmdb.ui.movie.list

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.masaliev.tmdb.App
import com.github.masaliev.tmdb.R
import com.github.masaliev.tmdb.data.model.Movie
import com.github.masaliev.tmdb.databinding.ActivityMovieListBinding
import com.github.masaliev.tmdb.ui.base.BaseActivity
import com.github.masaliev.tmdb.ui.movie.details.MovieDetailsActivity
import com.github.masaliev.tmdb.utils.views.EndlessRecyclerViewScrollListener
import com.github.masaliev.tmdb.utils.views.RecyclerViewItemPaddingDecorator
import javax.inject.Inject

class MovieListActivity : BaseActivity<ActivityMovieListBinding, MovieListContract.Presenter>(),
    MovieListContract.View {

    @Inject
    lateinit var mPresenter: MovieListContract.Presenter

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
            override fun onClick(movie: Movie) {
                startActivity(MovieDetailsActivity.getStartIntent(this@MovieListActivity, movie.id))
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

        if (mAdapter.itemCount > 0) {
            viewDataBinding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.menu_movie_list, it)

            // Associate searchable configuration with the SearchView
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = it.findItem(R.id.action_search).actionView as SearchView

            searchView.setQuery(mPresenter.getSavedQuery(), true)

            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Integer.MAX_VALUE
            searchView.queryHint = getString(R.string.search)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    mPresenter.publishSearchQuery(newText ?: "")
                    return true
                }

            })


        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        //noinspection SimplifiableIfStatement
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun getPresenter(): MovieListContract.Presenter = mPresenter


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

    override fun clearMovies() {
        mAdapter.setItems(null)
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