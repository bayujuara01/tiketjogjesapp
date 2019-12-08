package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class SuccessRegisterActivity : AppCompatActivity() {

    private lateinit var btn_explore : Button
    private lateinit var btt : Animation
    private lateinit var ttb : Animation
    private lateinit var app_splash : Animation
    private lateinit var icon_succcess : ImageView
    private lateinit var app_title : TextView
    private lateinit var app_subtitle : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_register)

        //laod element
        btn_explore = findViewById(R.id.btn_explore)
        icon_succcess = findViewById(R.id.icon_success)
        app_title = findViewById(R.id.app_title)
        app_subtitle = findViewById(R.id.app_subtitle)

        //load animation
        btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash)
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)

        //start animation
        btn_explore.startAnimation(btt)
        icon_succcess.startAnimation(app_splash)
        app_title.startAnimation(ttb)
        app_title.startAnimation(ttb)

        //menuju ke activity home
        btn_explore.setOnClickListener(View.OnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        })
    }
}
