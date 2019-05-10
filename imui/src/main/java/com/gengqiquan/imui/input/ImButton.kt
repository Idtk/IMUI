package com.gengqiquan.imui.input

import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gengqiquan.imui.model.ButtonInfo
import org.jetbrains.anko.*

class ImButton(context: Context) : LinearLayout(context) {
    var tv_text: TextView? = null
    var iv_img: ImageView? = null

    init {
        layoutParams = LinearLayout.LayoutParams(matchParent, matchParent)
        topPadding = dip(15)
        gravity = Gravity.CENTER_HORIZONTAL
        orientation= VERTICAL
        iv_img = imageView {
            scaleType = ImageView.ScaleType.FIT_XY
            layoutParams = LinearLayout.LayoutParams(dip(64), dip(64)).apply {
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }
        tv_text = textView {
            includeFontPadding = false
            textColor = 0xff666666.toInt()
            textSize = 12f
            layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent).apply {
                topMargin = dip(7)
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }
    }

    fun decorator(item: ButtonInfo) {
        tv_text?.text = item.text
        iv_img?.setImageDrawable(context.resources.getDrawable(item.res))
    }


}