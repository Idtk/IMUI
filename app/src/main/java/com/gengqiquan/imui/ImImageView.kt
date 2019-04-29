package com.gengqiquan.imui

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView

class ImImageView(val context: Context) : IimView {
    var itemView: ImageView? = null
    override fun get(): View {
        itemView = ImageView(context)
        return itemView!!
    }

    override fun decorator(item: IimMsg) {
        val msg = item.realData<TXMsg>()
        itemView?.setBackgroundColor(Color.parseColor(msg.url))
    }
}