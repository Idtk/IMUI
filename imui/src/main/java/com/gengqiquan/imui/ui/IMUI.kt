package com.gengqiquan.imui.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.gengqiquan.imui.interfaces.IMoreOldMsgListener
import com.gengqiquan.imui.interfaces.IimMsg
import com.gengqiquan.imui.interfaces.IimViewFactory
import com.gengqiquan.imui.interfaces.ImImageDisplayer
import org.jetbrains.anko.bottomPadding
import org.jetbrains.anko.dip
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onFocusChange

class IMUI(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private val uiAdapter by lazy {
        object : RecyclerView.Adapter<ImHolder>() {
            override fun getItemViewType(position: Int): Int {
                if (allInit == 1 && position == 0) {
                    return DefaultIMViewFactory.MORE_REFRESH
                }
                return data[position - allInit].uiType()
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImHolder {
                return ImHolder.get(imViewFactors, parent, viewType)
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
    fun oldMsgs(oldData: MutableList<IimMsg>, init: Boolean = false) {
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

    fun newMsgs(newData: MutableList<IimMsg>) {
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

    private fun scrollToNeed(position: Int) {
        listUI.scrollToPosition(position)
    }

    val listUI: RecyclerView

    init {
        listUI = recyclerView {
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = LinearLayoutManager(context)
            adapter = uiAdapter
            bottomPadding = dip(15)
            onFocusChange { v, hasFocus ->
                if (!hasFocus) {
                    scrollToNeed(data.size - 1)
                }
            }
            setOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!mIsLoadMore && allInit == 1 && (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_SETTLING)) {
                        if (isLastItemVisible(recyclerView)) {
                            mIsLoadMore = true
                            moreOldMsgListener?.more()
                        }
                    }
                }
            })

        }
    }

    var mIsLoadMore = false
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

    private fun isLastItemVisible(recyclerView: RecyclerView): Boolean {
        val adapter = recyclerView.getAdapter()

        if (null == adapter || adapter!!.getItemCount() == 0) {
            return true
        }

        val lastVisiblePosition =
            (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition();

        if (lastVisiblePosition == 0) {
            val lastVisibleChild = recyclerView.getChildAt(0)
            if (lastVisibleChild != null) {
                return lastVisibleChild.getTop() <= recyclerView.getTop()
            }
        }
        return false
    }

    fun addImViewFactory(imViewFactory: IimViewFactory) {
        imViewFactors.add(0, imViewFactory)
    }

    private var imViewFactors = mutableListOf<IimViewFactory>(DefaultIMViewFactory(context))

    companion object {
        private var displayer: ImImageDisplayer? = null
        fun setDisplayer(displayer: ImImageDisplayer) {
            Companion.displayer = displayer
        }

        fun display(url: String, imageView: ImageView, after: (width: Int, height: Int) -> Unit) {
            displayer?.display(url, imageView, after)
        }
    }
}
