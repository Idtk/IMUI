package com.gengqiquan.imui.help

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.gengqiquan.imui.R
import com.gengqiquan.imui.model.MenuAction
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

object LongPressHelp {
    private var actions: MutableList<MenuAction> = mutableListOf()
    fun getActions() = actions
    fun init(newActions: MutableList<MenuAction>) {
        actions = newActions
    }

    fun showPopAction(context: Context, data: Any, list: List<MenuAction>, windowView: View, itemView: View) {
        val width = itemView.dip(50)
        val height = itemView.dip(40)
        val popup =
            PopupWindow(WindowManager.LayoutParams.WRAP_CONTENT, height)
        val content = LinearLayout(context).apply {
            //            layoutParams = LinearLayout.LayoutParams(wrapContent, height)
            background = resources.getDrawable(R.drawable.im_pop_back)
            list.mapIndexed { index, menuAction ->
                if (index != 0) {
                    view {
                        backgroundColor = Color.WHITE
                        layoutParams = LinearLayout.LayoutParams(dip(1), matchParent)
                    }
                }
                textView {
                    text = menuAction.text
                    textSize = 16f
                    textColor = Color.WHITE
                    gravity = Gravity.CENTER
                    layoutParams = LinearLayout.LayoutParams(width, matchParent)
                    onClick {
                        popup.dismiss()
                        menuAction.action.action(data)
                    }
                }
            }
        }
        val location = intArrayOf(0, 0)
        itemView.getLocationOnScreen(location)
        var x = location[0] + itemView.measuredWidth / 2 - width * list.size / 2
        var y = location[1] - height


        popup.contentView = content
        popup.isOutsideTouchable = true
        popup.isFocusable = true
//        content.rootView.setOnTouchListener { v, event ->
//            Log.d("setOnTouchListener", "    Log.d(\"setOnTouchListener\", inPop.toString())")
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                var inPop = event.x in x until x + width * list.size && event.y in y until y + height
//                Log.d("setOnTouchListener", inPop.toString())
//                if (!inPop) {
//                    popup.dismiss()
//                }
//            }
//            false
//        }
        popup.showAtLocation(windowView, Gravity.LEFT or Gravity.TOP, x, y)
    }
}