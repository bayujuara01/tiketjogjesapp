package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MyTicketDetailAct : AppCompatActivity() {

    private lateinit var btn_kembali: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ticket_detail)
        btn_kembali = findViewById(R.id.btn_back)
        btn_kembali.setOnClickListener {
            val gotoprofile= Intent(this,MyprofilAct::class.java)
            startActivity(gotoprofile)
        }
    }
}
