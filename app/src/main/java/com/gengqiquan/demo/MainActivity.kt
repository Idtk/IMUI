package com.gengqiquan.demo

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.tencent.imsdk.*
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import com.tencent.imsdk.TIMCallBack
import com.tencent.imsdk.TIMManager
import com.gengqiquan.imui.help.IMHelp
import com.gengqiquan.imui.help.LongPressHelp
import com.gengqiquan.imui.input.ButtonFactory
import com.gengqiquan.imui.interfaces.IMoreOldMsgListener
import com.gengqiquan.imui.interfaces.IimMsg
import com.gengqiquan.imui.interfaces.ImImageDisplayer
import com.gengqiquan.imui.interfaces.OtherProxy
import com.gengqiquan.imlib.RealMsg
import com.gengqiquan.imlib.TIMViewFactory
import com.gengqiquan.imlib.audio.TIMAudioRecorder
import com.gengqiquan.imlib.audio.TIMMsgBuilder
import com.gengqiquan.imui.model.MenuAction
import com.gengqiquan.imui.ui.IMUI
import com.gengqiquan.qqresult.QQResult
import com.tencent.imsdk.TIMMessage
import com.tencent.imsdk.ext.message.TIMConversationExt
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt
import com.xhe.photoalbum.PhotoAlbum
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

//        val list = arrayListOf<RealMsg>()
//        list.add(RealMsg(TXMsg("你好", "", TXMsg.Type.TEXT), true))
//        list.add(RealMsg(TXMsg("你好防水防汗首付开上飞洒放松放松,你好防水防汗首付开上飞洒放松放松", "", TXMsg.Type.TEXT), false))
//        list.add(
//            RealMsg(
//                TXMsg(
//                    "",
//                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556532526983&di=c6d64102aaa1d612e260eb8176366b93&imgtype=0&src=http%3A%2F%2Fs11.sinaimg.cn%2Fmw690%2F006hikKrzy7slvzPwSKba%26690",
//                    TXMsg.Type.IMG
//                )
//                , true
//            )
//        )
//        list.add(
//            RealMsg(
//                TXMsg(
//                    "",
//                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556532526982&di=1f14a4ba8e5ad52d27b99299fa3b6803&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201512%2F12%2F20151212193107_ujGZV.jpeg",
//                    TXMsg.Type.IMG
//                ), false
//            )
//        )
//        list.add(RealMsg(TXMsg("", "", TXMsg.Type.VIDEO), true))
//        list.add(RealMsg(TXMsg("", "", TXMsg.Type.VIDEO), false))
        IMHelp.init(applicationContext, TIMAudioRecorder(), TIMMsgBuilder())
        im_ui.addImViewFactory(TIMViewFactory(this))
        IMUI.setDisplayer(object : ImImageDisplayer {
            override fun display(url: String, imageView: ImageView, after: (width: Int, height: Int) -> Unit) {
                Glide.with(this@MainActivity)
                    .load(url)
                    .asBitmap()
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                            after(resource!!.width, resource.height)
                            imageView.setImageBitmap(resource)
                        }
                    })
            }
        })
//        im_ui.appendMsgs(list.toMutableList())
        TIMManager.getInstance().init(
            applicationContext, TIMSdkConfig(1400205051)
                .enableLogPrint(true)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/")
        )
        val userConfig = TIMUserConfig().setGroupEventListener { }
            .setUserStatusListener(object : TIMUserStatusListener {
                override fun onUserSigExpired() {

                }

                override fun onForceOffline() {
                }
            })
            .setConnectionListener(object : TIMConnListener {
                override fun onConnected() {

                }

                override fun onWifiNeedAuth(p0: String?) {
                }

                override fun onDisconnected(p0: Int, p1: String?) {
                }
            })
            .setGroupEventListener { }
        userConfig.refreshListener = object : TIMRefreshListener {
            override fun onRefreshConversation(msgs: MutableList<TIMConversation>) {

            }

            override fun onRefresh() {
            }
        }

        TIMManager.getInstance().userConfig = TIMUserConfigMsgExt(userConfig)
            .setMessageReceiptListener {
                // TODO: 2019-05-07 监听撤回消息 
            }
        TIMManager.getInstance().addMessageListener(object : TIMMessageListener {
            override fun onNewMessages(msgs: MutableList<TIMMessage>): Boolean {
                val list = mutableListOf<IimMsg>()
                msgs.forEach {
                    list.addAll(RealMsg.create(it))
                }
                list.reverse()
                im_ui.newMsgs(list)
                return true
            }
        })
        TIMManager.getInstance().login(indent, sig, object : TIMCallBack {
            override fun onError(code: Int, desc: String) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.d(tag, "login failed. code: $code errmsg: $desc")

            }

            override fun onSuccess() {
                Log.d(tag, "getLocalMessage login succ")
                afterLogin()

            }
        })


    }

    fun afterLogin() {
        val con = TIMManager.getInstance().getConversation(
            TIMConversationType.C2C,    //会话类型：单聊
            tel
        )
        val conversationExt = TIMConversationExt(con)
        fun loadMore() {
            val count = 10

            conversationExt.getLocalMessage(count, lastMsg, object : TIMValueCallBack<List<TIMMessage>> {
                override fun onSuccess(msgs: List<TIMMessage>) {
                    Log.e(tag, "getLocalMessage" + msgs.size.toString())
                    if (msgs.size < count) {
                        im_ui.allInit()
                    }
                    if (msgs.isEmpty()) {
                        return
                    }
                    lastMsg = msgs.last()
                    val list = mutableListOf<IimMsg>()
                    msgs.forEach {
                        list.addAll(RealMsg.create(it))
                    }
                    list.reverse()
                    im_ui.oldMsgs(list)

                }

                override fun onError(p0: Int, p1: String?) {
                    Log.e(tag, "getLocalMessage" + p0.toString() + ":" + p1)
                }
            })
        }
        loadMore()
        im_ui.setMoreOldmoreOldMsgListener(object : IMoreOldMsgListener {
            override fun more() {
                loadMore()
            }
        })
        im_input.sendAction { type, msg ->
            con.sendMessage(msg as TIMMessage, object : TIMValueCallBack<TIMMessage> {
                override fun onSuccess(msg: TIMMessage) {
                    Log.e(tag, "onSuccess" + msg.toString())
                    val list = mutableListOf<IimMsg>()
                    list.addAll(RealMsg.create(msg))

//                    list.reverse()
                    im_ui.newMsgs(list)
                }

                override fun onError(p0: Int, p1: String?) {
                    Log.e(tag, "onError" + p0.toString() + ":" + p1)
                }
            })
        }
        im_input.otherProxy(object : OtherProxy {
            override fun proxy(type: Int, send: (Any) -> Unit) {
                if (type == ButtonFactory.PICTURE) {
                    QQResult.startActivityWith(
                        this@MainActivity,
                        PhotoAlbum(this@MainActivity).setLimitCount(4).albumIntent
                    )
                        .result {
                            send(IMHelp.getMsgBuildPolicy().buildImgMessage(PhotoAlbum.parseResult(it)) as TIMMessage)
                        }
                }

            }

        })
        var list = mutableListOf<MenuAction>()
        list.add(MenuAction("撤回") {
            conversationExt.revokeMessage(it as TIMMessage, object : TIMCallBack {
                override fun onSuccess() {

                }

                override fun onError(p0: Int, p1: String?) {
                }
            })
        })
        list.add(MenuAction("删除") {

        })
        LongPressHelp.init(list)
    }

    var lastMsg: TIMMessage? = null
    val tag = "immmmmm"
    val tel = "129a14e8a4b3a123"
    val indent = "8849cc559d324811"
    val sig =
        "eJxlj81qg0AYRfc*hbi1lHH0M2MhCw0x2KYpRQ3FjcjMqNNY488oltJ3L7UNsfRuz*Fe7oeiqqoW7cPbjNLzUMtUvjdcU*9UDWk3V9g0gqWZTM2O-YN8akTH0yyXvJuhAQAYoaUjGK*lyMWvQYjlUArgMBNbxDAWZs9O6Tz3U2UhhBEg*KOIYoaP2*dN4E*hlwewFyj0rVYfS-5Qea1*2AR2krlH27N3q4q5OzyBK7xRH2IZl1ExbF-a5ET9Y-FqHe7jpy7We70CzyRR2fpFmBTr9WJSijd**WYDdkyyWtCRd70417OAkQEGNtF3NOVT*QKew13t"
}
