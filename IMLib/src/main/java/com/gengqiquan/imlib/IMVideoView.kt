package com.gengqiquan.imlib

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.gengqiquan.imlib.video.VideoViewActivity
import com.gengqiquan.imui.help.IMHelp
import com.gengqiquan.imui.interfaces.IMediaListener
import com.gengqiquan.imui.interfaces.IimMsg
import com.gengqiquan.imui.ui.*
import com.tencent.imsdk.TIMVideoElem
import org.jetbrains.anko.*

class IMVideoView(context: Context, parent: ViewGroup) : RealImView(context, parent) {
    var iv_img: ImageView? = null
    var iv_paly: ImageView? = null
    var iv_loading: ImageView? = null
    var fl_content: FrameLayout? = null
    override fun floatBaseView() = iv_img!!

    override fun createItemView(contentView: FrameLayout): View {
        return contentView.apply {
            fl_content = frameLayout {
                layoutParams = FrameLayout.LayoutParams(dip(140), dip(140)).apply {
                    leftMargin = dip(63)
                    rightMargin = dip(63)
                }
                iv_img = imageView {
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
                }
                iv_paly = imageView {
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                    setImageResource(R.drawable.im_play)
                    visibility = View.GONE
                    layoutParams = FrameLayout.LayoutParams(dip(50), dip(50)).apply {
                        gravity = Gravity.CENTER
                    }
                }
                iv_loading = CircleImageView(context, Color.parseColor("#ffffff")).apply {
                    layoutParams = ViewGroup.LayoutParams(dip(30), dip(30))
                    visibility = View.GONE
                }
            }

        }
    }

    fun loading() {
        iv_loading?.show()
        iv_loading?.setImageDrawable(CircularProgressDrawable(context).apply {
            start()
            setStyle(CircularProgressDrawable.DEFAULT)
            arrowScale = 0.5f
            strokeWidth = dip(3).toFloat()
            setColorSchemeColors(Color.GRAY)
        })
    }

    override fun decoratorItemView(item: IimMsg) {
        fl_content?.layoutParams = (fl_content?.layoutParams as FrameLayout.LayoutParams).apply {
            gravity = if (item.isSelf()) Gravity.RIGHT else Gravity.LEFT
        }

        var imagePath: String? = null
        item.video(object : IMediaListener {
            override fun start() {

            }

            override fun error() {
            }

            override fun loading(now: Long, total: Long) {

            }

            override fun ready(path: String) {
                imagePath = path
                IMUI.display(path, iv_img!!) { w, h ->
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
                    fl_content?.layoutParams = (fl_content?.layoutParams as FrameLayout.LayoutParams).apply {
                        width = newWidth
                        height = newHeight

                    }

                }
            }
        }, object : IMediaListener {
            override fun start() {

            }

            override fun error() {

            }

            override fun loading(now: Long, total: Long) {
            }

            override fun ready(path: String) {
                iv_loading?.gone()
                iv_paly?.show()
                iv_img?.singleClick {
                    context.startActivity<VideoViewActivity>(
                        IMHelp.IMAGE_PATH to imagePath,
                        IMHelp.VIDEO_PATH to path
                    )
                }
            }
        })

    }

}