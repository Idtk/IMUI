package com.gengqiquan.imui

import android.widget.ImageView

interface ImImageDisplayer {
    fun display(url: String, imageView: ImageView ,after:(width: Int,height: Int)->Unit)
}