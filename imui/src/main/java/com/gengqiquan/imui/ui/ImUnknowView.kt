package com.gengqiquan.imui.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.gengqiquan.imui.interfaces.IimMsg
import org.jetbrains.anko.dip
import org.jetbrains.anko.textColor
import org.jetbrains.anko.wrapContent

class ImUnknowView(val context: Context, parent: ViewGroup) : ImView(parent) {
    override fun get(): View {

        return TextView(context).apply {
            textColor = Color.BLACK
            textSize = 18f
            gravity = Gravity.CENTER
            includeFontPadding = false
            text = "当前版本暂不支持此消息"
            layoutParams = FrameLayout.LayoutParams(wrapContent, dip(40)).apply {
                leftMargin = dip(63)
                rightMargin = dip(63)
            }
        }
    }

    override fun decorator(item: IimMsg) {
    }


}