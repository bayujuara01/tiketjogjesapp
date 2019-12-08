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
import kotlinx.android.synthetic.main.activity_get_started.*

class GetStartedActivity : AppCompatActivity() {

    //baybayu
    private lateinit var btn_sign_in : Button
    private lateinit var btn_new_account_create : Button
    private lateinit var ttb : Animation
    private lateinit var emblem_app : ImageView
    private lateinit var intro_app : TextView
    private lateinit var btt : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        //load element
        btn_sign_in = findViewById(R.id.btn_sign_in)
        btn_new_account_create = findViewById(R.id.btn_new_account_create)
        emblem_app = findViewById(R.id.emblem_app)
        intro_app = findViewById(R.id.intro_app)

        //load animation
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        btt = AnimationUtils.loadAnimation(this, R.anim.btt)

        //start animation
        emblem_app.startAnimation(ttb)
        intro_app.startAnimation(ttb)
        btn_sign_in.startAnimation(btt)
        btn_new_account_create.startAnimation(btt)

        //menuju halaman sign in
        btn_sign_in.setOnClickListener(View.OnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        })

        //menuju halaman new account / register
        btn_new_account_create.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegisterOneActivity::class.java)
            startActivity(intent)
        })
    }
}
