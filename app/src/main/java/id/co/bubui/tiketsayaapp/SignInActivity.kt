package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btn_new_account = findViewById<TextView>(R.id.btn_new_account)
        val btn_sign_in = findViewById<Button>(R.id.btn_sign_in)

        //menuju balaman resgister
        btn_new_account.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegisterOneActivity::class.java)
            startActivity(intent)
         })

        //menuju dashboard
        btn_sign_in.setOnClickListener(View.OnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        })
    }

}
