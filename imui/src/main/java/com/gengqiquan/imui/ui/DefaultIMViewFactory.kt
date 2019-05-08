package com.gengqiquan.imui.ui

import android.content.Context
import android.view.ViewGroup
import com.gengqiquan.imui.interfaces.IimViewFactory

class DefaultIMViewFactory(val context: Context) : IimViewFactory {
    companion object {
        val TEXT = 1
        val IMG = 2
        val VIDEO = 3
        val AUDIO = 4
        val SHARE = 5
        val MORE_REFRESH = 0xF007F
    }

    override fun create(parent: ViewGroup, viewType: Int): ImView? {
        return when (viewType) {
            TEXT -> {
                ImTextView(context, parent)
            }
            IMG -> {
                ImImageView(context, parent)
            }
            MORE_REFRESH -> {
                IMLoadMoreView(context, parent)
            }
            else -> {
                ImUnknowView(context, parent)
            }
        }
    }

}