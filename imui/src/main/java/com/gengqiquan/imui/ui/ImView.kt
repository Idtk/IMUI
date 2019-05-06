package com.gengqiquan.imui.ui

import android.view.View
import android.view.ViewGroup
import com.gengqiquan.imui.interfaces.iDecorator

abstract class ImView(val parent: ViewGroup) : iDecorator {
    abstract fun get(): View
}