package com.gengqiquan.imui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.core.view.marginBottom
import org.jetbrains.anko.*

abstract class RealImView(val context: Context, parent: ViewGroup) : ImView(parent) {
    private var itemView: View? = null
    private var tv_header: TextView? = null
    private var tv_time: TextView? = null
    override fun get(): View {
        itemView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent).apply {
                topPadding = dip(15)
            }
            tv_time = textView {
                background = resources.getDrawable(R.drawable.im_time_back)
                textColor = Color.WHITE
                textSize = 12f
                gravity = Gravity.CENTER
                includeFontPadding = false
                leftPadding = dip(6)
                rightPadding = dip(6)
                layoutParams = LinearLayout.LayoutParams(wrapContent, dip(20)).apply {
                    bottomMargin = dip(20)
                    gravity = Gravity.CENTER_HORIZONTAL
                }

            }
            frameLayout {
                tv_header = textView {
                    textColor = Color.WHITE
                    textSize = 15f
                    gravity = Gravity.CENTER
                    background = resources.getDrawable(R.drawable.im_header_back)
                    layoutParams = FrameLayout.LayoutParams(dip(41), dip(41)).apply {
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }
                createItemView(this)
            }

        }
        return itemView!!
    }

    abstract fun createItemView(contentView: FrameLayout): View

    override fun decorator(item: IimMsg) {
        tv_header?.text = "小明"
        tv_time?.text = item.time()
        if (item.isSelf()) {
            (tv_header?.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.RIGHT
        } else {
            (tv_header?.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.LEFT
        }
        decoratorItemView(item)
    }

    fun dip(value: Int) = itemView!!.dip(value)
    abstract fun decoratorItemView(item: IimMsg)
}