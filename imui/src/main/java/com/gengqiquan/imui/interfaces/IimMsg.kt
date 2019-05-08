package com.gengqiquan.imui.interfaces

import com.gengqiquan.imui.model.ImImage

open interface IimMsg {
    fun uiType(): Int
    fun sender(): Any
    fun text(): String
    fun time(): String?
    fun video(imgReady: IMediaListener, videoReady: IMediaListener)
    fun sound(): String
    fun img(): ImImage
    fun isSelf(): Boolean
    fun duration(): Long
    fun <T> realData(): T

}
