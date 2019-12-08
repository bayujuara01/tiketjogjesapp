package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class SuccessBuyTicketAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_buy_ticket)

        val btnViewTicket = findViewById<Button>(R.id.btn_view_ticket)
        btnViewTicket.setOnClickListener(View.OnClickListener {
            intent = Intent(this, MyTicketDetailAct::class.java)
            startActivity(intent)
        })

        val btnMyDashboard = findViewById<Button>(R.id.btn_my_dashboard)
        btnMyDashboard.setOnClickListener(View.OnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        })
    }
}
