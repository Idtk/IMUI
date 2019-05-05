package com.gengqiquan.imui.audio

interface InputHandler {
     fun popupAreaShow()

     fun popupAreaHide()

     fun startRecording()

     fun stopRecording()

     fun tooShortRecording()

     fun cancelRecording()
}