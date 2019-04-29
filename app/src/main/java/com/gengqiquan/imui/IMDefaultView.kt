package com.gengqiquan.imui

import android.content.Context
import android.view.View
import android.widget.TextView

class IMDefaultView(val context: Context) : IimView {

    companion object {
        val TEXT = 1
        val IMG = 2
        val VIDEO = 3
        val AUDIO = 4
        val SHARE = 5

    }

    var itemView: TextView? = null
    override fun get(): View {
        itemView = TextView(context)
        return itemView!!
    }

    override fun decorator(item: IimMsg) {
        itemView?.text = "不支持该消息类型"
    }
}