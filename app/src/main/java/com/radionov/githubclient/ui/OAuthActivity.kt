package com.radionov.githubclient.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.radionov.githubclient.BuildConfig.CLIENT_ID
import com.radionov.githubclient.BuildConfig.OAUTH_URL
import com.radionov.githubclient.utils.RESPONSES
import com.radionov.githubclient.viewmodels.AuthViewModel

class OAuthActivity : BaseActivity() {

    private lateinit var viewModel: AuthViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        val webview = WebView(this)
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                val codeFragment = "code="

                if (url.contains(codeFragment)) {
                    // the GET request contains an authorization code
                    val accessCode = url.substring(url.indexOf(codeFragment) + codeFragment.length)
                    //stop loading else redirects to callbackUrl
                    webview.stopLoading()

                    viewModel.getToken(accessCode)
                }
            }
        }
        webview.loadUrl(URL)
        setContentView(webview)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, viewModeFactory)
            .get(AuthViewModel::class.java)

        viewModel.subscribeResponse()
            .observe(this, Observer<RESPONSES> { response ->
                if (response == RESPONSES.SUCCESS) {
                    Toast.makeText(this@OAuthActivity, "Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@OAuthActivity, "Fail", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {
        var TAG = "OAuthActivity"
        private const val URL = "$OAUTH_URL?client_id=$CLIENT_ID"
    }
}