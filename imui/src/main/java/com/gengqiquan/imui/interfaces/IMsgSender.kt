package com.gengqiquan.imui.interfaces

import androidx.annotation.IntDef

interface IMsgSender {
    companion object {
        val preSend = 0
        val sent = 1

        @IntDef(0, 1)
        annotation class SendType
    }


    fun send(@SendType type: Int, msg: Any)

}