package com.gengqiquan.imlib

import android.content.Context
import android.view.ViewGroup
import com.gengqiquan.imui.interfaces.IimViewFactory
import com.gengqiquan.imui.ui.*

class TIMViewFactory(val context: Context) : IimViewFactory {


    override fun create(parent: ViewGroup, viewType: Int): ImView? {
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