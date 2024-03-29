package com.dash.projects.android.digiwave.views.menus.materials.branch

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.SubjectMaterialWebClient
import com.dash.projects.android.digiwave.`object`.utils.Utils.callToast
import com.dash.projects.android.digiwave.databinding.FragmentMaterialNavigationBinding
import com.google.android.material.appbar.AppBarLayout

class MaterialNavigationFragment : Fragment() {
    private var _binding: FragmentMaterialNavigationBinding? = null
    private val binding get() = _binding

    private var _newTitle: String? = null
    private val newTitle get() = _newTitle

    override fun onResume() {
        super.onResume()
        if (activity != null) {
            (activity as AppCompatActivity).supportActionBar?.apply {
                if (newTitle == null) setTitle(R.string.subject_materials)
                else title = newTitle
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (activity != null) {
            (activity as AppCompatActivity).supportActionBar?.apply {
                setTitle(R.string.subject_materials)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMaterialNavigationBinding.inflate(inflater, container, false)
        container?.rootView?.findViewById<AppBarLayout>(R.id.appbar_subject)
            ?.setExpanded(true, true)
        container?.rootView?.findViewById<Button>(R.id.home)?.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        return binding?.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val context = activity?.applicationContext

            binding?.wvMaterial?.let { web ->
                if (arguments != null) {
                    val url = arguments?.getString(EXTRA_URL, "") ?: ""
                    _newTitle = arguments?.getString(EXTRA_TITLE)

                    web.loadUrl(url)
                    web.webViewClient = WebViewClient()
                    web.settings.javaScriptEnabled = true
                    web.settings.javaScriptCanOpenWindowsAutomatically = true
                    web.settings.setSupportZoom(true)
                    web.settings.mediaPlaybackRequiresUserGesture = false
                    web.settings.setPluginState(WebSettings.PluginState.ON)
                    web.clearCache(true)
                    web.clearHistory()
                    web.setDownloadListener { mUrl, _, _, _, _ ->
                        val request = DownloadManager.Request(Uri.parse(mUrl))
                        request.setNotificationVisibility(IN_COMPLETED)

                        val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE)
                        (dm as DownloadManager).enqueue(request)

                        view.callToast(R.string.text_downloading, false)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val IN_COMPLETED = DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        const val EXTRA_URL = "extra_url"
        const val EXTRA_TITLE = "extra_title"
    }
}
