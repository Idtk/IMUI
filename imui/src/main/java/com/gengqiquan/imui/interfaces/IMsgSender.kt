package com.gengqiquan.imui.interfaces

import androidx.annotation.IntDef

interface IMsgSender {
    companion object {
        val SendWait = 0
        val Sending = 1
        val SendSucc = 2
        val SendFail = 3

        @IntDef(0, 1, 2, 3)
        annotation class SendType
    }

    fun send(@SendType type: Int, msg: Any, senderListener: ISenderListener)

}