package com.gengqiquan.imui.interfaces

import com.gengqiquan.imui.ImImage

interface IimMsg {
    fun uiType(): Int
    fun text(): String
    fun time(): String?
    fun video(): String
    fun sound(): String
    fun img(): ImImage
    fun isSelf(): Boolean
    fun <T> realData(): T
}
