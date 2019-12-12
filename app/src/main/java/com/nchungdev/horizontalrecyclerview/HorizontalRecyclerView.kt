package com.nchungdev.horizontalrecyclerview

import android.content.Context
import android.util.AttributeSet
import android.util.SparseIntArray
import android.view.View
import android.view.ViewGroup
import androidx.core.util.containsKey
import androidx.core.util.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HorizontalRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    private val childHeightMap = SparseIntArray()
    private val positionHashSet = mutableSetOf<Int>()
    private var runnable: Runnable? = null

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        setupFixHeightItemView()
    }

    private fun setupFixHeightItemView() {
        val layout = layoutManager as? LinearLayoutManager
        if (layout == null || layout.orientation == VERTICAL) {
            return
        }
        layout.isMeasurementCacheEnabled = false
        addOnChildAttachStateChangeListener(object : OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                removeRequestLayoutCallback()
                if (stopRequestLayout()) {
                    layout.isMeasurementCacheEnabled = true
                    return
                }
                runnable = requestLayout(view)
                post(runnable)
            }

            override fun onChildViewDetachedFromWindow(view: View) = Unit

            private fun stopRequestLayout() = positionHashSet.size == adapter?.itemCount
        })
    }

    private fun getChildrenFromView(view: View): List<View> {
        if (view is ViewGroup) {
            val result = ArrayList<View>()
            for (i in 0 until view.childCount) {
                val viewArrayList = ArrayList<View>().apply {
                    add(view)
                    addAll(getChildrenFromView(view.getChildAt(i)))
                }
                result.addAll(viewArrayList)
            }
            return result
        }
        return ArrayList<View>().apply { add(view) }
    }

    private fun isChildViewHeightChanged(views: List<View>): Boolean {
        var isViewChanged = false
        views.forEach { view ->
            val layoutParams = view.layoutParams as MarginLayoutParams
            val currentHeight = view.height + layoutParams.topMargin + layoutParams.bottomMargin

            val lastHeight = childHeightMap.get(view.id)
            if (lastHeight >= currentHeight) {
                return@forEach
            }
            if (isItemLayoutChanged(view.id)) {
                isViewChanged = true
            }
            if (lastHeight > 0) {
                isViewChanged = true
            }
            childHeightMap.put(view.id, currentHeight)
        }
        return isViewChanged
    }

    private fun isItemLayoutChanged(viewId: Int) =
        childHeightMap.isNotEmpty() && !childHeightMap.containsKey(viewId)

    private fun requestLayout(view: View) = Runnable {
        positionHashSet.add(getChildAdapterPosition(view))
        if (isChildViewHeightChanged(getChildrenFromView(view))) {
            requestLayout()
        }
    }

    private fun removeRequestLayoutCallback() {
        if (runnable == null) {
            removeCallbacks(runnable)
        }
    }
}
