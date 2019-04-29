package com.gengqiquan.imui

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.tencent.imsdk.TIMImageElem
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.wrapContent

class ImImageView(context: Context, parent: ViewGroup) : RealImView(context, parent) {
    var iv_img: ImageView? = null
    override fun createItemView(contentView: FrameLayout): View {
        return contentView.apply {
            iv_img = imageView {
                scaleType = ImageView.ScaleType.CENTER_CROP
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
            if (h <= 0) {
                return@display
            }
            var newWidth = w
            var newHeight = h
            val per = h / w.toFloat()

            if (per < 1) {
                newWidth = dip(140)
                newHeight = wrapContent
            } else if (per > 3) {
                newWidth = dip(56)
                newHeight = dip(140)
            } else {
                newWidth = wrapContent
                newHeight = dip(140)
            }
            (iv_img?.layoutParams as FrameLayout.LayoutParams).apply {
                gravity = if (item.isSelf()) Gravity.RIGHT else Gravity.LEFT
                width = newWidth
                height = newHeight

            }
        }
    }


}