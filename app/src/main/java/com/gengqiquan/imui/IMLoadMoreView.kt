package com.gengqiquan.imui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.gengqiquan.imui.interfaces.IimMsg
import com.tencent.imsdk.TIMVideoElem
import org.jetbrains.anko.*

class IMLoadMoreView(val context: Context, parent: ViewGroup) : ImView(parent) {
    var itemView: LinearLayout? = null
    override fun get(): View {
        itemView = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(matchParent, dip(50))
            gravity = Gravity.CENTER
            addView(CircleImageView(context, Color.parseColor("#ffffff")).apply {
                layoutParams = ViewGroup.LayoutParams(dip(30), dip(30))
                setImageDrawable(CircularProgressDrawable(context).apply {
                    start()
                    setStyle(CircularProgressDrawable.DEFAULT)
                    setStartEndTrim(0f, 0.9f)
                    arrowScale = 0.5f
                    arrowEnabled = true
                    strokeWidth = dip(3).toFloat()
                    setColorSchemeColors(Color.parseColor("#2a8cff"))
                })
            })

        }
        return itemView!!
    }

    override fun decorator(item: IimMsg) {
    }
}