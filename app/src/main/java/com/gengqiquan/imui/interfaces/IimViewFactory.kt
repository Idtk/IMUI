package com.gengqiquan.imui.interfaces

import android.view.ViewGroup
import com.gengqiquan.imui.ImView

interface IimViewFactory {

    fun create(parent: ViewGroup, viewType: Int): ImView?
}