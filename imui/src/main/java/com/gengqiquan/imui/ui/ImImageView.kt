package com.gengqiquan.imui.ui

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.gengqiquan.imui.interfaces.IimMsg
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.wrapContent

class ImImageView(context: Context, parent: ViewGroup) : RealImView(context, parent) {
    var iv_img: ImageView? = null
    override fun floatBaseView() = iv_img!!

    override fun createItemView(contentView: FrameLayout): View {
        return contentView.apply {
            iv_img = imageView {
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                layoutParams = FrameLayout.LayoutParams(dip(140), wrapContent).apply {
                    leftMargin = dip(63)
                    rightMargin = dip(63)
                }
            }
        }
    }

    override fun decoratorItemView(item: IimMsg) {
        val url = item.img().url ?: return
        IMUI.display(url, iv_img!!) { w, h ->
            val max = dip(140)
            if (h <= 0) {
                return@display
            }
            var newWidth = w
            var newHeight = h
            val per = h / w.toFloat()
            Log.d("display", w.toString() + ":" + h.toString() + "++" + per)
            if (per < 1) {
                newWidth = max
                newHeight = (h * max / w.toFloat()).toInt()
                iv_img?.scaleType = ImageView.ScaleType.CENTER_INSIDE
            } else if (per > 3) {
                newWidth = dip(56)
                newHeight = max
                iv_img?.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                newWidth = (w * max / h.toFloat()).toInt()
                newHeight = max
                iv_img?.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            iv_img?.layoutParams = (iv_img?.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = if (item.isSelf()) Gravity.RIGHT else Gravity.LEFT
                width = newWidth
                height = newHeight

            }
        }
    }


}