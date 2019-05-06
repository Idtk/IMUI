package com.gengqiquan.imui.interfaces


interface OtherProxy {
    fun proxy(type: Int, send: (Any) -> Unit)
}