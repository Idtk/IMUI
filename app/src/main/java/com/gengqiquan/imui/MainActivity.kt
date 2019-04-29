package com.gengqiquan.imui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = arrayListOf<TXMsg>()
        list.add(TXMsg("你好", "", TXMsg.Type.TEXT))
        list.add(TXMsg("你好", "", TXMsg.Type.TEXT))
        list.add(
            TXMsg(
                "",
                "#ff6600",
                TXMsg.Type.IMG
            )
        )
        list.add(
            TXMsg(
                "",
                "#2abcff",
                TXMsg.Type.IMG
            )
        )
        list.add(TXMsg("", "", TXMsg.Type.VIDEO))
        list.add(TXMsg("", "", TXMsg.Type.VIDEO))
        im_ui.appendMsgs(list.map { RealMsg(it) }.toMutableList())
    }
}
