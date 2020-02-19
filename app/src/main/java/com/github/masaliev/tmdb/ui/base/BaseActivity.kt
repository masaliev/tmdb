package com.github.masaliev.tmdb.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.github.masaliev.tmdb.App

@SuppressLint("Registered")
abstract class BaseActivity<V : ViewDataBinding, P : MvpPresenter<*>> : AppCompatActivity() {
    lateinit var viewDataBinding: V
        private set
    private var mPresenter: P? = null

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getPresenter(): P

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDependencyInjection()
        performDataBinding()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        getPresenter().detachView()
        if (isFinishing) {
            getPresenter().destroy()
            releaseActivityComponent()
        }
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mPresenter = if (mPresenter == null) getPresenter() else mPresenter
        viewDataBinding.setVariable(getBindingVariable(), mPresenter)
        viewDataBinding.executePendingBindings()
    }

    abstract fun performDependencyInjection()

    abstract fun releaseActivityComponent()
}