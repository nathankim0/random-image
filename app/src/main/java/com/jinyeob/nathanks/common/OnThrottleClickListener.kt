package com.jinyeob.nathanks.common

import android.util.Log
import android.view.View
import com.jinyeob.nathanks.MainApplication.Companion.TAG

// REFERENCE: https://blog.yena.io/studynote/2019/12/26/Android-Kotlin-ClickListener.html
/**
 * ThrottleClick (더블클릭 방지)
 */
fun View.onThrottleClick(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener))
}

/**
 * ThrottleClick (더블클릭 방지) with interval setting
 */
fun View.onThrottleClick(action: (v: View) -> Unit, interval: Long) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(listener, interval))
}

/**
 * 이 listener 를 사용하면 더블클릭을 방지할 수 있다.
 */
internal class OnThrottleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 500
) :
    View.OnClickListener {
    private var clickable = true

    override fun onClick(v: View?) {
        if (clickable) {
            clickable = false
            v?.run {
                postDelayed(
                    {
                        clickable = true
                    },
                    interval
                )
                clickListener.onClick(v)
            }
        } else {
            Log.d(TAG, "waiting for a while")
        }
    }
}
