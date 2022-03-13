package com.perfect.islamyat.ui.quran

import android.app.AlertDialog
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.perfect.islamyat.MainActivity
import com.perfect.islamyat.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.quran_fragment.*
import java.io.IOException
import java.net.InetAddress


class QuranFragment : Fragment() {

    companion object {
        fun newInstance() = QuranFragment()
    }

    private lateinit var viewModel: QuranViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root : View = inflater.inflate(R.layout.quran_fragment, container, false)
        val quranWv : WebView = root.findViewById(R.id.quran_wv)
        quranWv.settings.setJavaScriptEnabled(true)
        quranWv.settings.setDomStorageEnabled(true)
        quranWv.settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

//        quranWv.settings.allowContentAccess
        (activity as MainActivity).nav_view.visibility = View.VISIBLE
        (activity as MainActivity).nav_view.getMenu().getItem(0).setChecked(true)

        quranWv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler,
                error: SslError?
            ) {
                when (error!!.primaryError) {
                    SslError.SSL_UNTRUSTED -> Log.v("","SslError : The certificate authority is not trusted.")
                    SslError.SSL_EXPIRED -> Log.v("","SslError : The certificate has expired.")
                    SslError.SSL_IDMISMATCH -> Log.v("","The certificate Hostname mismatch.")
                    SslError.SSL_NOTYETVALID -> Log.v("","The certificate is not yet valid.")
                }
                handler.proceed()
                super.onReceivedSslError(view, handler, error)
            }

            override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
                super.onReceivedClientCertRequest(view, request)
            }

        }
//        quranWv.webChromeClient = WebChromeClient()
        if (isOnline()) {
            quranWv.loadUrl("http://quran.ksu.edu.sa/m.php#aya=1_1")
        } else {
            val alertDialog =
                AlertDialog.Builder(activity)
            alertDialog.setTitle("تحذير")
            alertDialog.setMessage("أرجو التأكد من الاتصال بالانترنت")
            alertDialog.setPositiveButton(
                "حسنا"
            ) { dialog, which ->
                dialog.cancel()
                (activity as MainActivity).onBackPressed()
            }
            alertDialog.show()
        }
//        quranWv.loadUrl("https://quran.ksu.edu.sa/m.php#")
//        quranWv.loadUrl("https://quran.ksu.edu.sa/index.php?l=ar")
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuranViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onPause() {
        super.onPause()
        if (quran_wv != null) quran_wv.destroy()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).nav_view.visibility = View.VISIBLE
        (activity as MainActivity).nav_view.getMenu().getItem(0).setChecked(true)
    }
    override fun onDestroy() {

        super.onDestroy()

    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }

    fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }
}