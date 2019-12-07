package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_get_started.*

class GetStartedActivity : AppCompatActivity() {

    //baybayu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        val btn_sign_in = findViewById<Button>(R.id.btn_sign_in)
        val btn_new_account_create = findViewById<Button>(R.id.btn_new_account_create)

        //menuju halaman sign in
        btn_sign_in.setOnClickListener(View.OnClickListener {
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        })

        btn_new_account_create.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegisterOneActivity::class.java)
            startActivity(intent)
        })
    }
    //by ghoffar
}
