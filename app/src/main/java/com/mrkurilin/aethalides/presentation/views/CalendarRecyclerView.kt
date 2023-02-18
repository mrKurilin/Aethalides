package com.mrkurilin.aethalides.presentation.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrkurilin.aethalides.data.util.CalendarDaysAdapter

class CalendarRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)

        if (adapter is CalendarDaysAdapter) {
            if (layoutManager is LinearLayoutManager) {
                val first =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition().toLong()
                val last =
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition().toLong()
                val middle = (last + first) / 2
                (adapter as CalendarDaysAdapter).setMiddleVisiblePosition(middle.toInt())
            }
        }
    }
}