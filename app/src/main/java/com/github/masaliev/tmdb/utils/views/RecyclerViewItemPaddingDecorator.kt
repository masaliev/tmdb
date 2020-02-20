package com.github.masaliev.tmdb.utils.views


import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemPaddingDecorator(
    private val spaceInPixels: Int,
    @RecyclerView.Orientation private val orientation: Int? = RecyclerView.VERTICAL
) :
    RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        if (orientation == null){
            outRect.bottom = spaceInPixels
            outRect.top = spaceInPixels
            outRect.left = spaceInPixels
            outRect.right = spaceInPixels
        }else if (orientation == RecyclerView.VERTICAL) {
            outRect.bottom = spaceInPixels
            outRect.top = spaceInPixels
        } else {
            outRect.left = spaceInPixels
            outRect.right = spaceInPixels
        }
    }
}