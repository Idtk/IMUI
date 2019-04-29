package com.gengqiquan.imui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.recyclerview.v7.recyclerView

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
    fun appendMsgs(oldData: MutableList<IimMsg>) {
        data.addAll(0, oldData)
        uiAdapter.notifyItemRangeInserted(0, oldData.size)
    }

    fun newMsg(msg: IimMsg) {
        data.add(msg)
        uiAdapter.notifyItemInserted(data.size)
    }

    init {
        recyclerView {
            layoutManager = LinearLayoutManager(context)
            adapter = uiAdapter
        }
    }

    fun addIMViewFactory(imViewFactory: IimViewFactory) {
        imViewFactors.add(0, imViewFactory)

    }

    private var imViewFactors = mutableListOf<IimViewFactory>(DefaultIMViewFactory(context))

}
