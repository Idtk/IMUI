package com.gengqiquan.imui.interfaces

import com.tencent.imsdk.TIMMessage

interface OtherProxy {
    fun proxy(type: Int, send: (TIMMessage) -> Unit)
}