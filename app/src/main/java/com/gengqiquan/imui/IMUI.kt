package com.gengqiquan.imui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                return data[position].uiType()
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImHolder {
                return ImHolder.get(imViewFactors, parent, viewType)
            }

            override fun getItemCount() = data.size

            override fun onBindViewHolder(holder: ImHolder, position: Int) {
                holder.imView.decorator(data[position])
            }
        }
    }


    private var data: MutableList<IimMsg> = arrayListOf()
    fun oldMsgs(oldData: MutableList<IimMsg>) {
        data.addAll(0, oldData)
        uiAdapter.notifyItemRangeInserted(0, oldData.size)
        scrollToNeed(oldData.size - 1)
    }

    fun newMsgs(oldData: MutableList<IimMsg>) {
        val start = data.size
        data.addAll(oldData)
        uiAdapter.notifyItemRangeInserted(start, oldData.size)
        scrollToNeed(data.size - 1)
    }

    fun newMsg(msg: IimMsg) {
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
            layoutManager = LinearLayoutManager(context)
            adapter = uiAdapter
            bottomPadding=dip(15)
            onFocusChange { v, hasFocus ->
                if (!hasFocus){
                    scrollToNeed(data.size-1)
                }
            }
        }
    }

    fun addImViewFactory(imViewFactory: IimViewFactory) {
        imViewFactors.add(0, imViewFactory)

    }

    private var imViewFactors = mutableListOf<IimViewFactory>(DefaultIMViewFactory(context))

    companion object {
        private var displayer: ImImageDisplayer? = null
        fun setDisplayer(displayer: ImImageDisplayer) {
            this.displayer = displayer
        }

        fun display(url: String, imageView: ImageView, after: (width: Int, height: Int) -> Unit) {
            displayer?.display(url, imageView, after)
        }
    }
}
