package id.co.bubui.tiketsayaapp


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class PaymentWebViewActivity : AppCompatActivity() {

    companion object {
        const val URL_KEY = "url_key"
    }

    private lateinit var webViewPayment: WebView
    private lateinit var btnClosePayment: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_web_view)

        webViewPayment = findViewById(R.id.webview_payment)
        btnClosePayment = findViewById(R.id.btn_pay_close)

        webViewPayment.settings.loadsImagesAutomatically = true
        webViewPayment.settings.javaScriptEnabled = true
        webViewPayment.settings.domStorageEnabled = true

        /*Setting agar bisa zoom*/
        webViewPayment.settings.setSupportZoom(false)
        /*Scroolbar Webview*/
        webViewPayment.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        //Load URL
        webViewPayment.webViewClient = WebViewClient()
        webViewPayment.loadUrl(intent.extras?.getString(URL_KEY))

        btnClosePayment.setOnClickListener {
            val intentProfile = Intent(this, MyprofilAct::class.java)
            startActivity(intentProfile)
        }
    }
}
