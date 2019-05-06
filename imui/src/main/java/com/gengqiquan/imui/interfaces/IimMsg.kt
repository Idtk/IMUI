package com.gengqiquan.imui.interfaces

import com.gengqiquan.imui.model.ImImage

open interface IimMsg {
    fun uiType(): Int
    fun text(): String
    fun time(): String?
    fun video(): String
    fun sound(): String
    fun img(): ImImage
    fun isSelf(): Boolean
    fun duration(): Long
    fun <T> realData(): T
}
