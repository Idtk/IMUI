package com.gengqiquan.imui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import org.jetbrains.anko.*

class ImText( context: Context, parent: ViewGroup) : IimView(context,parent) {

    var itemView: View? = null
    var tv_header: TextView? = null
    var tv_content: TextView? = null
    var v_arrow_left: View? = null
    var v_arrow_right: View? = null
    override fun get(): View {
        itemView = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                topMargin = dip(15)
            }
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
            v_arrow_left = view {
                backgroundColor = Color.YELLOW
                layoutParams = FrameLayout.LayoutParams(dip(5), dip(9)).apply {
                    leftMargin = dip(58)
                    topMargin = dip(15)
                    gravity = Gravity.LEFT
                }
            }
            v_arrow_right = view {
                backgroundColor = Color.YELLOW
                layoutParams = FrameLayout.LayoutParams(dip(5), dip(9)).apply {
                    rightMargin = dip(58)
                    topMargin = dip(15)
                    gravity = Gravity.RIGHT
                }
            }
            tv_content = textView {
                textColor = Color.BLACK
                textSize = 18f
                includeFontPadding=false
//                padding = dip(10)
//                backgroundColor = Color.YELLOW
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    leftMargin = dip(63)
                    rightMargin = dip(63)
//                    gravity = Gravity.LEFT
                }

            }
        }
        return itemView!!
    }

    override fun decorator(item: IimMsg) {
        val msg = item.realData<TXMsg>()
        tv_header?.text = "小明"
        tv_content?.text = msg.text
        if (item.isSelf()) {
            v_arrow_left?.visibility = View.GONE
            v_arrow_right?.visibility = View.VISIBLE
//            tv_content?.gravity = Gravity.RIGHT
            (tv_content?.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.RIGHT
                rightMargin = tv_content!!.dip(63)
            }
            (tv_header?.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.RIGHT
            tv_content?.background = context.resources.getDrawable(R.drawable.im_text_self)
        } else {
            v_arrow_left?.visibility = View.VISIBLE
            v_arrow_right?.visibility = View.GONE
//            tv_content?.gravity = Gravity.LEFT
            (tv_content?.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.LEFT
                leftMargin = tv_content!!.dip(63)
            }
            (tv_header?.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.LEFT
            tv_content?.background = context.resources.getDrawable(R.drawable.im_text)
        }
    }
}