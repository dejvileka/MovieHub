package com.dejvidleka.moviehub.ui.home

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout

class VideoHandler(
    private val nonVideoLayout: View, private val videoLayout: FrameLayout,
    private val webView: WebView
) : WebChromeClient() {

    private var customView: View? = null
    private var customViewCallback: CustomViewCallback? = null
    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {

        if (view is FrameLayout) {
            customView = view
            customView?.let {
                it.visibility = View.VISIBLE
                customViewCallback = callback
                videoLayout.visibility = View.VISIBLE
                videoLayout.addView(customView)
                if (webView.context is Activity) {
                    val activity = webView.context as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
            }
        }
    }

    override fun onHideCustomView() {
        videoLayout.removeView(customView)
        if (customView != null) {
            videoLayout.visibility = View.GONE
            nonVideoLayout.visibility = View.VISIBLE
            customViewCallback?.onCustomViewHidden()
            customView = null

        }
    }
}

