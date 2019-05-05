package com.gengqiquan.imui

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

    }

    override fun create(parent: ViewGroup, viewType: Int): ImView? {
        return when (viewType) {
            TEXT -> {
                ImTextView(context, parent)
            }
            IMG -> {
                ImImageView(context, parent)
            }

            AUDIO -> {
                IMVideoView(context, parent)
            }
            VIDEO -> {
                IMVideoView(context, parent)
            }
            else -> null
        }
    }

}