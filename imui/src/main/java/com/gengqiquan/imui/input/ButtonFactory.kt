package com.gengqiquan.imui.input

import android.content.Context
import android.view.ViewGroup

class ButtonFactory {
    companion object {
        val PICTURE = 1
        val CAMERA = 2
        val CARD = 3
        val CAR = 4
        fun create(context: Context, parent: ViewGroup): ImButton {
            return ImButton(context)
        }
    }


}