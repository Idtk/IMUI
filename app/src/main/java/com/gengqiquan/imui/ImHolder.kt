package com.gengqiquan.imui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ImHolder(val imView: IimView) : RecyclerView.ViewHolder(imView.get()) {

    companion object {

        fun get(imViewFactors: MutableList<IimViewFactory>, parent: ViewGroup, viewType: Int): ImHolder {
            return ImHolder(create(imViewFactors, parent, viewType))
        }

        fun create(imViewFactors: MutableList<IimViewFactory>, parent: ViewGroup, viewType: Int): IimView {
            var imView: IimView? = null
            for (factor in imViewFactors) {
                imView = factor.create(parent, viewType)
                if (imView != null)
                    break
            }
            return imView!!
        }
    }


}