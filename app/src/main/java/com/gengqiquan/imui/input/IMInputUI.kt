package com.gengqiquan.imui.input

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.gengqiquan.imui.*
import com.gengqiquan.imui.audio.InputHandler
import com.gengqiquan.imui.audio.UIKitAudioArmMachine
import com.gengqiquan.imui.help.MsgHelp
import com.gengqiquan.imui.interfaces.OtherProxy
import com.gengqiquan.imui.model.ButtonInfo
import com.tencent.imsdk.TIMMessage
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class IMInputUI(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val uiAdapter by lazy {
        object : BaseAdapter() {

            override fun getItem(position: Int) = data[position]

            override fun getItemId(position: Int) = position.toLong()

            override fun getCount() = data.size

            override fun getItemViewType(position: Int): Int {
                return data[position].type
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val info = getItem(position)
                return ButtonFactory.create(context, parent).apply {
                    decorator(info)
                    onClick {
                        otherProxy?.proxy(info.type) {
                            send.invoke(info.type, it)
                        }
                    }
                }
            }
        }
    }

    private var otherProxy: OtherProxy? = null
    fun otherProxy(proxy: OtherProxy) {
        otherProxy = proxy
    }

    private var data: MutableList<ButtonInfo> = arrayListOf()
    //    fun appendMaddsgs(oldData: MutableList<IimMsg>) {
//        data.addAll(0, oldData)
//        uiAdapter.notifyItemRangeInserted(0, oldData.size)
//    }
//
//    fun newMsgs(oldData: MutableList<IimMsg>) {
//        val start = data.size
//        data.addAll(oldData)
//        uiAdapter.notifyItemRangeInserted(start, oldData.size)
//    }
//
//    fun newMsg(msg: IimMsg) {
//        data.add(msg)
//        uiAdapter.notifyItemInserted(data.size)
//    }
    private var tv_send: TextView? = null
    private var tv_audio: TextView? = null
    private var et_text: EditText? = null
    private var iv_other: ImageView? = null
    private var gv_button: View? = null
    private var inAudio = false

    init {
        backgroundColor = 0xffefefef.toInt()
        orientation = VERTICAL
        view {
            layoutParams = LayoutParams(matchParent, dip(0.5f))
            backgroundColor = 0xffe1e1e1.toInt()
        }
        linearLayout {
            layoutParams = LayoutParams(matchParent, wrapContent).apply {
                //                topMargin = dip(10)
//                bottomMargin = dip(10)
            }
            imageView {
                onClick {
                    inAudio = !inAudio
                    tv_audio?.isShow(inAudio)
                    et_text?.isShow(!inAudio)
                    background = resources.getDrawable(if (inAudio) R.drawable.im_keyboard else R.drawable.im_voice)
                }
                background = resources.getDrawable(R.drawable.im_voice)
            }.lparams(dip(26), dip(26)) {
                leftMargin = dip(13)
                rightMargin = dip(11)
                bottomMargin = dip(14)
                gravity = Gravity.BOTTOM
            }
            et_text = editText {
                background = resources.getDrawable(R.drawable.im_edit_back)
                includeFontPadding = false
                hint = "说点什么吧..."
                hintTextColor = 0xff858585.toInt()
                textColor = Color.BLACK
                textSize = 18f
                verticalPadding = 0
                horizontalPadding = dip(11)
                minHeight = dip(38)
                gravity = Gravity.CENTER_VERTICAL
                doOnTextChanged { text, start, count, after ->
                    iv_other?.isShow(text.isNullOrBlank())
                    tv_send?.isShow(!text.isNullOrBlank())
                }
            }.lparams(0, wrapContent) {
                topMargin = dip(8)
                bottomMargin = dip(8)
                weight = 1f
            }
            audioView(this)
            imageView {
                onClick { }
                background = resources.getDrawable(R.drawable.im_face)
            }.lparams(dip(26), dip(26)) {
                leftMargin = dip(11)
                bottomMargin = dip(14)
                gravity = Gravity.BOTTOM
            }
            iv_other = imageView {
                onClick {
                    onClick {
                        gv_button?.isShow(gv_button!!.visibility == View.GONE)
                        if (gv_button!!.visibility != View.GONE) {
                            closeKeybord(et_text!!)
                        } else {
                            openKeybord(et_text!!)
                        }
                    }
                }
                background = resources.getDrawable(R.drawable.im_other)
            }.lparams(dip(26), dip(26)) {
                horizontalMargin = dip(13)
                bottomMargin = dip(14)
                gravity = Gravity.BOTTOM
            }
            tv_send = textView {
                background = resources.getDrawable(R.drawable.im_send_back)
                includeFontPadding = false
                textColor = Color.WHITE
                textSize = 16f
                gravity = Gravity.CENTER
                text = "发送"
                visibility = View.GONE
                singleClick {
                    send?.invoke(
                        DefaultIMViewFactory.TEXT, MsgHelp.buildTextMessage(et_text!!.text.toString())
                    )
                    et_text?.setText("")
                }
            }.lparams(dip(44), dip(26)) {
                horizontalMargin = dip(6)
                bottomMargin = dip(7)
                gravity = Gravity.BOTTOM
            }
        }
        view {
            layoutParams = LayoutParams(matchParent, dip(0.5f))
            backgroundColor = 0xffe1e1e1.toInt()
        }

        gv_button = gridView {
            visibility = View.GONE
            numColumns = 4
            adapter = uiAdapter
            bottomPadding = dip(20)
            layoutParams = LayoutParams(matchParent, dip(118))
        }


        data.add(
            ButtonInfo(
                "照片",
                R.drawable.im_picture,
                ButtonFactory.PICTURE
            )
        )
        data.add(
            ButtonInfo(
                "拍摄",
                R.drawable.im_camera,
                ButtonFactory.CAMERA
            )
        )
        data.add(
            ButtonInfo(
                "我的名片", R.drawable.im_identity_card,
                ButtonFactory.CARD
            )
        )
        data.add(
            ButtonInfo(
                "我的车源",
                R.drawable.im_car,
                ButtonFactory.CAR
            )
        )
    }

    private var sendAble: Boolean = false
    private var audioCancel: Boolean = false
    private var startRecordY: Float = 0.toFloat()
    var inputHandler: InputHandler? = null
    private fun audioView(parent: LinearLayout) {
        parent.apply {
            tv_audio = textView {
                background = resources.getDrawable(R.drawable.im_edit_back)
                includeFontPadding = false
                textColor = Color.BLACK
                textSize = 18f
                gravity = Gravity.CENTER
                text = "按住 说话"
                visibility = View.GONE
                setOnTouchListener(object : OnTouchListener {
                    private var start: Long = 0
                    override fun onTouch(v: View, motionEvent: MotionEvent): Boolean {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            audioCancel = true
                            startRecordY = motionEvent.getY()
                            this@textView.apply {
                                background = resources.getDrawable(R.drawable.im_audio_back)
                                text = "松开 结束"
                            }
                            inputHandler?.startRecording()
                            start = System.currentTimeMillis()
                            UIKitAudioArmMachine.getInstance().startRecord {
                                if (inputHandler != null) {
                                    if (audioCancel) {
                                        inputHandler?.stopRecording()
                                        return@startRecord
                                    }
                                    if (it < 500) {
                                        inputHandler?.tooShortRecording()
                                        return@startRecord
                                    }
                                    inputHandler?.stopRecording()
                                    this@textView.apply {
                                        background = resources.getDrawable(R.drawable.im_edit_back)
                                        text = "按住 说话"
                                    }
                                }

                                send?.invoke(
                                    DefaultIMViewFactory.AUDIO, MsgHelp.buildAudioMessage(
                                        UIKitAudioArmMachine.getInstance().recordAudioPath,
                                        it.toInt()
                                    )
                                )
                            }

                        } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                            if (motionEvent.getY() - startRecordY < -100) {
                                audioCancel = true
                                inputHandler?.cancelRecording()
                                this@textView.apply {
                                    background = resources.getDrawable(R.drawable.im_edit_back)
                                    text = "按住 说话"
                                }
                            } else {
                                audioCancel = false
                                inputHandler?.startRecording()
                                this@textView.apply {
                                    background = resources.getDrawable(R.drawable.im_audio_back)
                                    text = "松开 结束"
                                }
                            }

                        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                            audioCancel = motionEvent.getY() - startRecordY < -100
                            this@textView.apply {
                                background = resources.getDrawable(R.drawable.im_edit_back)
                                text = "按住 说话"
                            }
                            UIKitAudioArmMachine.getInstance().stopRecord()
                        }
                        return true
                    }
                })
                layoutParams = LinearLayout.LayoutParams(0, dip(38)).apply {
                    topMargin = dip(8)
                    bottomMargin = dip(8)
                    weight = 1f
                }
            }
        }

    }

    fun sendAction(action: (Int, TIMMessage) -> Unit) {
        send = action
    }


    private var send: (Int, TIMMessage) -> Unit = { type, msg -> }
    //
//    fun addIMViewFactory(imViewFactory: IimViewFactory) {
//        imViewFactors.add(0, imViewFactory)
//
//    }
//
//    private var imViewFactors = mutableListOf<IimViewFactory>(DefaultIMViewFactory(context))
//
//    companion object {
//        private var displayer: ImImageDisplayer? = null
//        fun setDisplayer(displayer: ImImageDisplayer) {
//            this.displayer = displayer
//        }
//
//        fun display(url: String, imageView: ImageView, after: (width: Int, height: Int) -> Unit) {
//            displayer?.display(url, imageView, after)
//        }
//    }
    private fun openKeybord(mEditText: EditText) {
        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    /**
     * 关闭软键盘
     *
     */
    private fun closeKeybord(mEditText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    inline fun View.dip(value: Int): Int = context.dip(value)
    inline fun View.dip(value: Float): Int = context.dip(value)
}
