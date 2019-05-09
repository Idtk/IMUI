package com.gengqiquan.imlib

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.gengqiquan.imlib.audio.UIKitAudioArmMachine
import com.gengqiquan.imui.R
import com.gengqiquan.imui.help.ToastHelp
import com.gengqiquan.imui.interfaces.IimMsg
import com.gengqiquan.imui.ui.RealImView
import com.gengqiquan.imui.ui.isShow
import com.gengqiquan.imui.ui.singleClick
import org.jetbrains.anko.*

class ImAudioView(context: Context, parent: ViewGroup) : RealImView(context, parent) {
    var fl_voice: FrameLayout? = null
    var tv_time: TextView? = null
    //    var tv_time_self: TextView? = null
    var iv_play: ImageView? = null
    var ll_content: LinearLayout? = null

    override fun floatBaseView() = fl_voice!!
    override fun createItemView(contentView: FrameLayout): View {
        return contentView.apply {
            fl_voice = frameLayout {
                layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                    leftMargin = dip(63)
                    rightMargin = dip(63)
                }
                iv_play = imageView {
                    layoutParams = FrameLayout.LayoutParams(dip(20), dip(20)).apply {
                        gravity = Gravity.RIGHT xor Gravity.CENTER_VERTICAL
                    }
                }
                tv_time = textView {
                    textColor = Color.BLACK
                    textSize = 15f
                    includeFontPadding = false
                    singleLine = true
                    layoutParams = FrameLayout.LayoutParams(wrapContent, wrapContent).apply {
                        gravity = Gravity.RIGHT xor Gravity.CENTER_VERTICAL
                    }
                }

            }

        }
    }

    private var m = 1
    fun getLength(length: Int, duration: Long): Int {
        Log.d("getLength", length.toString())

        m = (m.toFloat() * 2 / 3).toInt()
        if (duration > 1) {
            return getLength(length + m, duration - 1)
        }
        return length
    }

    override fun decoratorItemView(item: IimMsg) {


        m = dip(50)
        var length = getLength(dip(20), item.duration())
        fl_voice?.layoutParams = (fl_voice?.layoutParams as FrameLayout.LayoutParams).apply {
            width = length
            gravity = if (item.isSelf()) Gravity.RIGHT else Gravity.LEFT
        }
        tv_time?.layoutParams = (tv_time?.layoutParams as FrameLayout.LayoutParams).apply {
            gravity = (if (item.isSelf()) Gravity.RIGHT else Gravity.LEFT) xor Gravity.CENTER_VERTICAL
            leftMargin = if (item.isSelf()) 0 else dip(30)
            rightMargin = if (!item.isSelf()) 0 else dip(30)
        }
        iv_play?.layoutParams = (iv_play?.layoutParams as FrameLayout.LayoutParams).apply {
            gravity = (if (item.isSelf()) Gravity.RIGHT else Gravity.LEFT) xor Gravity.CENTER_VERTICAL
        }
        fl_voice?.background =
            context.resources.getDrawable(if (item.isSelf()) R.drawable.im_text_self else R.drawable.im_text)
        tv_time?.text = item.duration().toString() + "\""
        iv_play?.setImageResource(R.drawable.im_voice_msg_playing_3)


        fl_voice?.singleClick {
            if (UIKitAudioArmMachine.getInstance().isPlayingRecord) {
                UIKitAudioArmMachine.getInstance().stopPlayRecord()
                return@singleClick
            }
            val audio = item.sound()
            if (TextUtils.isEmpty(audio)) {
                ToastHelp.toastLongMessage("语音文件还未下载完成")
                return@singleClick
            }
            iv_play?.setImageResource(R.drawable.im_play_voice_message)
            val animationDrawable = iv_play?.drawable as AnimationDrawable
            animationDrawable.start()
            UIKitAudioArmMachine.getInstance()
                .playRecord(audio) {
                    iv_play?.post {
                        animationDrawable.stop()
                        iv_play?.setImageResource(R.drawable.im_voice_msg_playing_3)
                    }
                }
        }


    }


}