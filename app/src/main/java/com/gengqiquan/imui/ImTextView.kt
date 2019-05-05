package com.gengqiquan.imui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.gengqiquan.imui.interfaces.IimMsg
import org.jetbrains.anko.*

class ImTextView(context: Context, parent: ViewGroup) : RealImView(context, parent) {
    var tv_content: TextView? = null
    override fun createItemView(contentView: FrameLayout): View {
        return contentView.apply {
            tv_content = textView {
                textColor = Color.BLACK
                textSize = 18f
                includeFontPadding = false
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    leftMargin = dip(63)
                    rightMargin = dip(63)
                }
            }
        }
    }


    override fun decoratorItemView(item: IimMsg) {
        tv_content?.text = item.text()
        if (item.isSelf()) {
            (tv_content?.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.RIGHT
//                rightMargin = tv_content!!.dip(63)
            }
            tv_content?.background = context.resources.getDrawable(R.drawable.im_text_self)
        } else {
            (tv_content?.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = Gravity.LEFT
//                leftMargin = tv_content!!.dip(63)
            }
            tv_content?.background = context.resources.getDrawable(R.drawable.im_text)
        }
    }


}