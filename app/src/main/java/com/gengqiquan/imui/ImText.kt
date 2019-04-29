package com.gengqiquan.imui

import android.content.Context
import android.view.View
import android.widget.TextView

class ImText(val context: Context) : IimView {

    var itemView: TextView? = null
    override fun get(): View {
        itemView = TextView(context)
        return itemView!!
    }

    override fun decorator(item: IimMsg) {
        val msg = item.realData<TXMsg>()
        itemView?.text = item.toString()
    }
}