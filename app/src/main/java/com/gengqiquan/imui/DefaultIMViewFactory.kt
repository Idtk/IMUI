package com.gengqiquan.imui

import android.content.Context
import android.view.ViewGroup

class DefaultIMViewFactory(val context: Context) : IimViewFactory {
    companion object {
        val TEXT = 1
        val IMG = 2
        val VIDEO = 3
        val AUDIO = 4
        val SHARE = 5

    }

    override fun create(parent: ViewGroup, viewType: Int): IimView? {
        return when (viewType) {
            TEXT -> {
                ImText(context,parent)
            }
            IMG -> {
                ImImageView(context,parent)
            }
            VIDEO -> {
                IMVideoView(context,parent)
            }
            else -> null
        }
    }

}