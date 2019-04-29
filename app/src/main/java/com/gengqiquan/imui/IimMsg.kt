package com.gengqiquan.imui

interface IimMsg {
    fun uiType(): Int
    fun isSelf(): Boolean
    fun <T> realData(): T
}
