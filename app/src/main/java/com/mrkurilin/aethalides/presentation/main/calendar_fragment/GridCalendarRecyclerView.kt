package com.mrkurilin.aethalides.presentation.main.calendar_fragment

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridCalendarRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)

        if (adapter is DaysAdapter) {
            if (layoutManager is LinearLayoutManager) {
                val first = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition().toLong()
                val last = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition().toLong()
                val middle = (last + first) / 2
                (adapter as DaysAdapter).setMiddleVisiblePosition(middle.toInt())
            }
        }
    }
}