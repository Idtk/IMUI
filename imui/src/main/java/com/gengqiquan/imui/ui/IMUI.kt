package com.gengqiquan.imui.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING
import com.gengqiquan.imui.input.ImInputUI
import com.gengqiquan.imui.interfaces.IMoreOldMsgListener
import com.gengqiquan.imui.interfaces.IimMsg
import com.gengqiquan.imui.interfaces.IimViewFactory
import org.jetbrains.anko.bottomPadding
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onFocusChange

class IMUI(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val uiAdapter by lazy {
        object : RecyclerView.Adapter<ImHolder>() {
            override fun getItemViewType(position: Int): Int {
                if (allInit == 1 && position == 0) {
                    return DefaultIMViewFactory.MORE_REFRESH
                }
                return data[position - allInit].uiType()
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImHolder {
                return ImHolder.get(parent, viewType)
            }

            override fun getItemCount() = data.size + allInit

            override fun onBindViewHolder(holder: ImHolder, position: Int) {
                if (allInit == 1 && position == 0) {
                    return
                }
                holder.imView.decorator(data[position - allInit])
            }
        }
    }


    private var data: MutableList<IimMsg> = arrayListOf()
    fun oldMsgs(oldData: List<IimMsg>, init: Boolean = false) {
        if (init) {
            data.clear()
        }
        allInit = 1
        data.addAll(0, oldData)
//        uiAdapter.notifyItemRangeInserted(0, oldData.size)
        uiAdapter.notifyDataSetChanged()
        scrollToNeed(oldData.size - 1)
        mIsLoadMore = false
    }

    fun newMsgs(newData: List<IimMsg>) {
        allInit = 1
        val start = data.size
        data.addAll(newData)
//        uiAdapter.notifyItemRangeInserted(start, newData.size)
        uiAdapter.notifyDataSetChanged()
        scrollToNeed(data.size - 1)
    }

    fun newMsg(msg: IimMsg) {
        allInit = 1
        data.add(msg)
        uiAdapter.notifyItemInserted(data.size)
        scrollToNeed(data.size - 1)
    }

    fun delete(any: Any) {
//        val index = data.indexOf(any)
//        if (index>-1){
//
//        }
        if (data.remove(any)) {
            uiAdapter.notifyDataSetChanged()
        }

    }

    fun refresh() {
        uiAdapter.notifyDataSetChanged()
        scrollToNeed(data.size - 1)
    }

    private fun scrollToNeed(position: Int) {
        linearLayoutManager.scrollToPosition(position + allInit)

    }

    val listUI: RecyclerView
    val inputUI: ImInputUI = ImInputUI(context)
    private val linearLayoutManager = LinearLayoutManager(context)

    init {
        orientation = VERTICAL
        listUI = recyclerView {
            layoutParams = LayoutParams(matchParent, 0).apply {
                weight = 1f
            }
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = linearLayoutManager
            adapter = uiAdapter
            bottomPadding = dip(15)
            onFocusChange { v, hasFocus ->
                if (!hasFocus) {
                    scrollToNeed(data.size - 1)
                }
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    if (dy < -1) {
                        inputUI.reset()
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!mIsLoadMore && allInit == 1 && (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_SETTLING)) {
                        if (!recyclerView.canScrollVertically(-1)) {
                            mIsLoadMore = true
                            moreOldMsgListener?.more()
                        }
                    }
                }
            })
        }

        addView(inputUI)
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            var lastHeight = 0
            override fun onGlobalLayout() {
                var height = listUI.height

                if (Math.abs(lastHeight - height) > dip(50)) {
                    scrollToNeed(data.size - 1)
                }
                lastHeight = height

            }
        })
    }

    private var lastListY = 0
    private var mIsLoadMore = false
    private var moreOldMsgListener: IMoreOldMsgListener? = null
    fun setMoreOldmoreOldMsgListener(listener: IMoreOldMsgListener) {
        moreOldMsgListener = listener
    }

    private var allInit = 0
    fun allInit() {
        allInit = 0
        mIsLoadMore = false
        uiAdapter.notifyDataSetChanged()
    }

}
