package com.gengqiquan.imui.interfaces

import androidx.annotation.IntDef

interface ISenderListener {
    companion object {
        @IntDef(0, 1, 2, 3)
        annotation class SendType
    }


    fun statusChange(@SendType type: Int)

}