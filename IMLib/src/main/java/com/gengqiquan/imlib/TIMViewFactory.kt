package com.gengqiquan.imlib

import android.content.Context
import android.view.ViewGroup
import com.gengqiquan.imui.interfaces.IimViewFactory
import com.gengqiquan.imui.ui.*

class TIMViewFactory(val context: Context) : IimViewFactory {

    /**
     *viewType大于1000的类型均为特殊类型，常规类型不要使用
     *@author gengqiquan
     *@date 2019-05-09 15:01
     */
    override fun create(parent: ViewGroup, viewType: Int): ImView? {
        if (viewType > 999) {
            when (viewType / 1000) {
                1 -> {
                    ImPreSendView(context, parent)
                }
                else -> null
            }
        }
        return when (viewType) {
            DefaultIMViewFactory.AUDIO -> {
                ImAudioView(context, parent)
            }
            DefaultIMViewFactory.VIDEO -> {
                IMVideoView(context, parent)
            }
            DefaultIMViewFactory.SHARE -> {
                ImShareView(context, parent)
            }
            else -> null
        }
    }

}