package com.gengqiquan.imlib.audio

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.gengqiquan.imui.R
import com.gengqiquan.imui.help.ToastHelp
import com.gengqiquan.imui.interfaces.IimMsg
import com.gengqiquan.imui.ui.RealImView
import com.gengqiquan.imui.ui.isShow
import com.gengqiquan.imui.ui.singleClick
import org.jetbrains.anko.*

class ImAudioView(context: Context, parent: ViewGroup) : RealImView(context, parent) {
    var fl_content: FrameLayout? = null
    var tv_time: TextView? = null
    var tv_time_self: TextView? = null
    var iv_play: ImageView? = null

    override fun floatBaseView() = fl_content!!
    override fun createItemView(contentView: FrameLayout): View {
        return contentView.apply {
            linearLayout {
                gravity = Gravity.RIGHT
                layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                    leftMargin = dip(63)
                    rightMargin = dip(63)
                }
                tv_time_self = textView {
                    textColor = Color.BLACK
                    textSize = 18f
                    includeFontPadding = false
                }
                fl_content = frameLayout {
                    iv_play = imageView {
                        layoutParams = FrameLayout.LayoutParams(dip(20), dip(20)).apply {
                            gravity = Gravity.RIGHT
                        }
                    }
                }
                tv_time = textView {
                    textColor = Color.BLACK
                    textSize = 18f
                    includeFontPadding = false
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
        var length = getLength(0, item.duration())
        (fl_content?.layoutParams as LinearLayout.LayoutParams).apply {
            width = length
        }
        tv_time_self?.isShow(item.isSelf())
        tv_time?.isShow(!item.isSelf())
        iv_play?.setImageResource(R.drawable.im_voice_msg_playing_3)
        if (item.isSelf()) {
            tv_time_self?.text = item.duration().toString() + "\""
            tv_time_self?.apply {
                layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
            }
            tv_time?.text = ""
            tv_time?.apply {
                layoutParams = LinearLayout.LayoutParams(0, wrapContent).apply {
                    weight = 1f
                }
            }
            fl_content?.background = context.resources.getDrawable(R.drawable.im_text_self)

            iv_play?.apply {
                layoutParams = FrameLayout.LayoutParams(dip(20), dip(20)).apply {
                    gravity = Gravity.RIGHT
                }
            }
        } else {
            tv_time_self?.text = ""
            tv_time_self?.apply {
                layoutParams = LinearLayout.LayoutParams(0, wrapContent).apply {
                    weight = 1f
                }
            }
            tv_time?.text = item.duration().toString() + "\""
            tv_time?.apply {
                layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
            }
            fl_content?.background = context.resources.getDrawable(R.drawable.im_text)
            iv_play?.apply {
                layoutParams = FrameLayout.LayoutParams(dip(20), dip(20)).apply {
                    gravity = Gravity.LEFT
                }
            }
        }
        fl_content?.singleClick {
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