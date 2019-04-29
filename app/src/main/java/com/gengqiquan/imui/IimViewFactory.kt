package com.gengqiquan.imui

import android.view.ViewGroup

interface IimViewFactory {

    fun create(parent: ViewGroup, viewType: Int): IimView?
}