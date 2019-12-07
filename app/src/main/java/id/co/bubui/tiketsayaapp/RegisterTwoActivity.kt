package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

class RegisterTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_two)

        val btn_back = findViewById<LinearLayout>(R.id.btn_back)
        val btn_continue = findViewById<Button>(R.id.btn_continue)

        btn_back.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegisterOneActivity::class.java)
            startActivity(intent)
        })

        btn_continue.setOnClickListener(View.OnClickListener {
            intent = Intent(this, SuccessRegisterActivity::class.java)
            startActivity(intent)
        })
    }
}
