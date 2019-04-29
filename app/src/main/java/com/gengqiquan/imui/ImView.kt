package com.gengqiquan.imui

import android.view.View
import android.view.ViewGroup

abstract class ImView(val parent: ViewGroup) : iDecorator {
    abstract fun get(): View
}