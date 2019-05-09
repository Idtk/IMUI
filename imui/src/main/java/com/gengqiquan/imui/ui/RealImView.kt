package com.gengqiquan.imui.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.gengqiquan.imui.R
import com.gengqiquan.imui.help.LongPressHelp
import com.gengqiquan.imui.interfaces.IimMsg
import com.gengqiquan.imui.model.MenuAction
import org.jetbrains.anko.*

abstract class RealImView(val context: Context, parent: ViewGroup) : ImView(parent) {
    private var itemView: View? = null
    private var tv_header: TextView? = null
    private var tv_time: TextView? = null
    override fun get(): View {
        itemView = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent).apply {
                topPadding = dip(15)
            }
            tv_time = textView {
                background = resources.getDrawable(R.drawable.im_time_back)
                textColor = Color.WHITE
                textSize = 12f
                gravity = Gravity.CENTER
                includeFontPadding = false
                leftPadding = dip(6)
                rightPadding = dip(6)
                layoutParams = LinearLayout.LayoutParams(wrapContent, dip(20)).apply {
                    bottomMargin = dip(20)
                    gravity = Gravity.CENTER_HORIZONTAL
                }

            }
            frameLayout {
                tv_header = textView {
                    textColor = Color.WHITE
                    textSize = 15f
                    gravity = Gravity.CENTER
                    background = resources.getDrawable(R.drawable.im_header_back)
                    layoutParams = FrameLayout.LayoutParams(dip(41), dip(41)).apply {
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }
                createItemView(this)
            }

        }
        return itemView!!
    }

    abstract fun createItemView(contentView: FrameLayout): View

    override fun decorator(item: IimMsg) {
        tv_header?.layoutParams = (tv_header?.layoutParams as FrameLayout.LayoutParams).apply {
            gravity = if (item.isSelf()) Gravity.RIGHT else Gravity.LEFT
        }
        tv_header?.text = "小明"
        tv_time?.isShow(!item.time().isNullOrEmpty())
        tv_time?.text = item.time() ?: ""
        decoratorItemView(item)
        floatBaseView().setOnLongClickListener {

            LongPressHelp.showPopAction(
                context, item.realData(),
                getMenuAction(LongPressHelp.getActions().filter { !it.isOnlySelf || item.isSelf() } as MutableList<MenuAction>),
                parent.rootView,
                floatBaseView()
            )
            false
        }
    }

    open fun getMenuAction(actions: MutableList<com.gengqiquan.imui.model.MenuAction>): MutableList<com.gengqiquan.imui.model.MenuAction> {
        return actions
    }

    fun dip(value: Int) = itemView!!.dip(value)
    abstract fun decoratorItemView(item: IimMsg)
    abstract fun floatBaseView(): View
}