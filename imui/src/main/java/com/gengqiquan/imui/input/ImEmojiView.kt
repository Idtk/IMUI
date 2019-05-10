package com.gengqiquan.imui.input

import android.content.Context
import android.gesture.GestureOverlayView
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gengqiquan.imui.interfaces.ISelectListener
import com.gengqiquan.imui.ui.singleClick
import org.jetbrains.anko.bottomPadding
import org.jetbrains.anko.dip
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.viewPager
import android.os.SystemClock
import android.util.Log
import android.view.*
import com.gengqiquan.imui.model.Emoji
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor


class ImEmojiView(context: Context) : LinearLayout(context) {
    class Holder(textView: TextView) : RecyclerView.ViewHolder(textView)

    private val uiAdapter by lazy {
        object : RecyclerView.Adapter<Holder>() {
            override fun getItemViewType(position: Int): Int {

                return 1
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                val tv_emoji = TextView(context).apply {
                    layoutParams = LayoutParams(dip(50), dip(50))
                    textSize = 20f
                    textColor = Color.BLACK
                    gravity = Gravity.CENTER
//                    padding=dip(15)
                }
                return Holder(tv_emoji)
            }

            override fun getItemCount() = data.size

            override fun onBindViewHolder(holder: Holder, position: Int) {
                val tv_emoji = holder.itemView as TextView
                tv_emoji.text = data[position]
                tv_emoji.singleClick {
                    selectedListener?.select(data[position])
                }
            }
        }
    }

    var selectedListener: ISelectListener? = null
    private var data: MutableList<String> = arrayListOf()
    val listUI: RecyclerView
    var num = 7
    var spanCount = 3

    val gridLayoutManager = GridLayoutManager(context, spanCount, HORIZONTAL, false)
//    val gestureDetector: GestureDetector

    init {
        data.addAll(Emoji.list.split(" "))
        listUI = recyclerView {
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = gridLayoutManager
            adapter = uiAdapter
            bottomPadding = dip(15)
        }
//        gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
//            override fun onShowPress(e: MotionEvent?) {
//            }
//
//            override fun onSingleTapUp(e: MotionEvent?): Boolean {
//                return false
//            }
//
//            override fun onDown(e: MotionEvent?): Boolean {
//                return true
//            }
//
//            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
//                return true
//            }
//
//            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
//                listUI.scrollBy(distanceX.toInt(), 0)
//                return true
//            }
//
//            override fun onLongPress(e: MotionEvent?) {
//            }
//        })
//        listUI.setOnTouchListener { v, event ->
//            gestureDetector.onTouchEvent(event)
//            if (event.action == MotionEvent.ACTION_UP) {
//                val first = gridLayoutManager.findFirstCompletelyVisibleItemPosition() / spanCount
//                if (first % num < num.toFloat() / 2) {
//                    gridLayoutManager.scrollToPositionWithOffset(first / num * spanCount, 0)
//                } else {
//                    gridLayoutManager.scrollToPositionWithOffset(((first / num) + 1) * num * spanCount, 0)
//                }
//            }
//            true
//        }
    }


    var BLUE = "DragEvent"
}