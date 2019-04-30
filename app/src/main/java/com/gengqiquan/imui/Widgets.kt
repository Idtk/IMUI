package com.gengqiquan.imui

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView

/**
 * Created by gengqiquan on 2017/6/15.
 */

/**
 *清除图片显示
 *@author gengqiquan
 *@date 2018/3/12 上午9:36
 */
fun ImageView.clear(): Unit = this.setImageDrawable(BitmapDrawable())


fun View.show(): Unit {
    this.visibility = View.VISIBLE
}

fun View.gone(): Unit {
    this.visibility = View.GONE
}

fun View.hide(): Unit {
    this.visibility = View.INVISIBLE
}

fun View.isShow(show: Boolean): Unit {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }

}

fun View.singleClick(listener: (v: View) -> Unit): Unit {
    var enable = true
    this.setOnClickListener {
        if (enable) {
            enable = false
            listener(it)
            this.postDelayed({ enable = true }, 1500)
        }
    }
}


