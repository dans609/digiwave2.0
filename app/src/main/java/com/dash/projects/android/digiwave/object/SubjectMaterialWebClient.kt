package com.dash.projects.android.digiwave.`object`

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import com.dash.projects.android.digiwave.R

class SubjectMaterialWebClient private constructor(mContext: Context?) : WebViewClient() {
    private val context: Context?

    init {
        context = mContext
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        if (view == null) return false
        if (request == null) return false

        view.loadUrl(request.url.toString())
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    override fun onReceivedError(
        wv: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        if (wv == null) return

        try {
            wv.stopLoading()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (wv.canGoBack()) wv.goBack()

        wv.loadUrl("about:blank")

        val alertDialog = context?.let { AlertDialog.Builder(it).create() }
        alertDialog?.setTitle(R.string.text_error_message)
        alertDialog?.setMessage(context?.resources?.getString(R.string.text_check_internet))
        alertDialog?.show()
        super.onReceivedError(wv, request, error)
    }

    companion object {
        @Volatile
        @SuppressLint("StaticFieldLeak")
        private var instance: SubjectMaterialWebClient? = null

        fun getInstance(mContext: Context?): SubjectMaterialWebClient =
            instance ?: synchronized(this) {
                instance ?: SubjectMaterialWebClient(mContext).apply {
                    instance = this
                }
            }
    }
}
