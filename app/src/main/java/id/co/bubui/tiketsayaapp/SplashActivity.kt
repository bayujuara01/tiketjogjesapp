package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Membuat handler
        handler = Handler()
        //setting timer untuk 1 detik auto Intent ke GetStartedActivity
        handler.postDelayed({
                intent = Intent(this, GetStartedActivity::class.java)
                startActivity(intent)
                finish()
        }, 1000) // 1000 Millis -> 1 Detik
    }
}
