package com.gengqiquan.imui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val list = arrayListOf<RealMsg>()
        list.add(RealMsg(TXMsg("你好", "", TXMsg.Type.TEXT), true))
        list.add(RealMsg(TXMsg("你好防水防汗首付开上飞洒放松放松,你好防水防汗首付开上飞洒放松放松", "", TXMsg.Type.TEXT), false))
        list.add(
            RealMsg(
                TXMsg(
                    "",
                    "#ff6600",
                    TXMsg.Type.IMG
                )
                , true
            )
        )
        list.add(
            RealMsg(
                TXMsg(
                    "",
                    "#2abcff",
                    TXMsg.Type.IMG
                ), false
            )
        )
        list.add(RealMsg(TXMsg("", "", TXMsg.Type.VIDEO), true))
        list.add(RealMsg(TXMsg("", "", TXMsg.Type.VIDEO), false))
        im_ui.appendMsgs(list.toMutableList())
    }
}
