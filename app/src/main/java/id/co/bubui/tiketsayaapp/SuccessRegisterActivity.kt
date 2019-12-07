package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class SuccessRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_register)

        val btn_explore = findViewById<Button>(R.id.btn_explore)

        btn_explore.setOnClickListener(View.OnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        })
    }
}
