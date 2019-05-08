package com.gengqiquan.imui.ui

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gengqiquan.imui.interfaces.IimViewFactory


class ImHolder(val imView: ImView) : RecyclerView.ViewHolder(imView.get()) {

    companion object {

        fun get(imViewFactors: MutableList<IimViewFactory>, parent: ViewGroup, viewType: Int): ImHolder {
            return ImHolder(
                create(
                    imViewFactors,
                    parent,
                    viewType
                )
            )
        }

        fun create(imViewFactors: MutableList<IimViewFactory>, parent: ViewGroup, viewType: Int): ImView {
            var imView: ImView? = null
            for (factor in imViewFactors) {
                imView = factor.create(parent, viewType)
                if (imView != null)
                    break
            }
            Log.d("ViewFactors",viewType.toString())
            return imView!!
        }
    }


}