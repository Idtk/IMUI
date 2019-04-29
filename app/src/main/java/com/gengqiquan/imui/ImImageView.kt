package com.gengqiquan.imui

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.jetbrains.anko.imageView

class ImImageView(val context: Context,  parent: ViewGroup) : IimView(parent) {
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