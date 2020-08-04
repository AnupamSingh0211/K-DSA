package com.hala.k_dsa.ui.home

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hala.k_dsa.R
import kotlinx.android.synthetic.main.fragment_home.*


class WebViewFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private var webView: WebView? = null


    companion object {
        val TAG = "WebViewFragment"


        fun getInstance(url: String): WebViewFragment {

            val fragment = WebViewFragment()

            val bundle = Bundle()
            bundle.putString("url", url)
            fragment.arguments = bundle
            return fragment

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = webView1
        var url = arguments?.getString("url")
        if (TextUtils.isEmpty(url))
            url = "https://androidiots.github.io/K-DSA/DataStructure/Trees/Traversal/Inorder.html"

        webView?.getSettings()?.setJavaScriptEnabled(true);
        webView?.loadUrl(url)
    }
}
