package com.gengqiquan.imlib

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gengqiquan.imui.interfaces.IimMsg
import com.gengqiquan.imui.ui.ImView
import com.tencent.imsdk.TIMVideoElem

class IMVideoView(val context: Context,parent: ViewGroup) : ImView(parent) {
    var itemView: TextView? = null
    override fun get(): View {
        itemView = TextView(context)
        return itemView!!
    }

    override fun decorator(item: IimMsg) {
        val msg = item.realData<TIMVideoElem>()
        itemView?.text="暂不支持"
    }
}