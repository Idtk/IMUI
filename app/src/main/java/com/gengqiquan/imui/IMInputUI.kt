package com.gengqiquan.imui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick

class IMInputUI(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    //    private val uiAdapter by lazy {
//        object : RecyclerView.Adapter<ImHolder>() {
//            override fun getItemViewType(position: Int): Int {
//                return data[position].uiType()
//            }
//
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImHolder {
//                return ImHolder.get(imViewFactors, parent, viewType)
//            }
//
//            override fun getItemCount() = data.size
//
//            override fun onBindViewHolder(holder: ImHolder, position: Int) {
//                holder.imView.decorator(data[position])
//            }
//        }
//    }
//
//
//    private var data: MutableList<IimMsg> = arrayListOf()
//    fun appendMsgs(oldData: MutableList<IimMsg>) {
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
    private var iv_other: ImageView? = null

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
                onClick { }
                background = resources.getDrawable(R.drawable.ic_voice)
            }.lparams(dip(26), dip(26)) {
                leftMargin = dip(13)
                rightMargin = dip(11)
                bottomMargin = dip(14)
                gravity = Gravity.BOTTOM
            }
            var et_text = editText {
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
            imageView {
                onClick { }
                background = resources.getDrawable(R.drawable.ic_face)
            }.lparams(dip(26), dip(26)) {
                leftMargin = dip(11)
                bottomMargin = dip(14)
                gravity = Gravity.BOTTOM
            }
            iv_other = imageView {
                onClick { }
                background = resources.getDrawable(R.drawable.ic_other)
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
                    send?.invoke(et_text.text.toString()) {
                        et_text.setText("")
                    }
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
//        recyclerView {
//            layoutManager = LinearLayoutManager(context)
//            adapter = uiAdapter
//        }
    }

    fun sendAction(action: (String, success: () -> Unit) -> Unit) {
        send = action
    }

    fun otherAction(action: (Int, String) -> Unit) {
        other = action
    }

    private var send: (String, success: () -> Unit) -> Unit = { text, a -> }
    private var other: (Int, String) -> Unit = { type, data -> }
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

    inline fun View.dip(value: Int): Int = context.dip(value)
    inline fun View.dip(value: Float): Int = context.dip(value)
}
