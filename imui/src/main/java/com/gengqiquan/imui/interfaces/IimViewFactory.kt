package com.gengqiquan.imui.interfaces

import android.view.ViewGroup
import com.gengqiquan.imui.ui.ImView

interface IimViewFactory {

    fun create(parent: ViewGroup, viewType: Int): ImView?
}