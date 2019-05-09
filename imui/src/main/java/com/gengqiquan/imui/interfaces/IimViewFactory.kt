package com.gengqiquan.imui.interfaces

import android.view.ViewGroup
import com.gengqiquan.imui.ui.ImView

interface IimViewFactory {
    /**
     *viewType大于1000的类型均为特殊类型，常规类型不要使用
     *@author gengqiquan
     *@date 2019-05-09 15:01
     */
    fun create(parent: ViewGroup, viewType: Int): ImView?
}