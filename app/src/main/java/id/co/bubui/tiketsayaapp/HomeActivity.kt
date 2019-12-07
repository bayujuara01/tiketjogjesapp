package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

class HomeActivity : AppCompatActivity() {

    private lateinit var btnTicketPisa: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnTicketPisa = findViewById(R.id.btn_ticket_pisa)

        // pergi ke detail activity
        btnTicketPisa.setOnClickListener {
            val gotoTicketIntent = Intent(this, TicketDetailActivity::class.java)
            startActivity(gotoTicketIntent)
        }
    }
}
