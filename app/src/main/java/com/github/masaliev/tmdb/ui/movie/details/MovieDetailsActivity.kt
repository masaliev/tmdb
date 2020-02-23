package com.github.masaliev.tmdb.ui.movie.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.masaliev.tmdb.App
import com.github.masaliev.tmdb.R
import com.github.masaliev.tmdb.databinding.ActivityMovieDetailsBinding
import com.github.masaliev.tmdb.ui.base.BaseActivity
import javax.inject.Inject

class MovieDetailsActivity :
    BaseActivity<ActivityMovieDetailsBinding, MovieDetailsContract.Presenter>(),
    MovieDetailsContract.View {

    @Inject
    lateinit var mPresenter: MovieDetailsContract.Presenter

    private var mLikeMenuItem: MenuItem? = null
    private var mDislikeMenuItem: MenuItem? = null

    companion object {
        fun getStartIntent(context: Context, movieId: Int) =
            Intent(context, MovieDetailsActivity::class.java).also {
                it.putExtra("movieId", movieId)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieId: Int = intent.getIntExtra("movieId", 0)

        if (movieId == 0) {
            finish()
            return
        }

        mPresenter.attachView(this)
        mPresenter.setMovieId(movieId)
        mPresenter.viewIsReady()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movie_details, menu)
        menu?.let {
            mLikeMenuItem = it.findItem(R.id.action_like)
            mDislikeMenuItem = it.findItem(R.id.action_dislike)

            mPresenter.getMovie().get()?.let {
                isLiked(it.isLiked)
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_like -> mPresenter.like()
            R.id.action_dislike -> mPresenter.dislike()
        }
        return false
    }

    override fun getPresenter(): MovieDetailsContract.Presenter = mPresenter

    override fun getLayoutId(): Int = R.layout.activity_movie_details

    override fun performDependencyInjection() {
        App[this].getMovieDetailsComponent().inject(this)
    }

    override fun releaseActivityComponent() {
        App[this].clearMovieDetailsComponent()
    }

    override fun setTitle(title: String) {
        supportActionBar?.apply {
            this.title = title
        }
    }

    override fun showLoading() {
        viewDataBinding.progress.visibility = View.VISIBLE
        viewDataBinding.scrollView.visibility = View.GONE
    }

    override fun hideLoading() {
        viewDataBinding.progress.visibility = View.GONE
        viewDataBinding.scrollView.visibility = View.VISIBLE
    }

    override fun isLiked(isLiked: Boolean) {
        mLikeMenuItem?.isVisible = !isLiked
        mDislikeMenuItem?.isVisible = isLiked
    }

}