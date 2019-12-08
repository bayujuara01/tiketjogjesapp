package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class TicketDetailActivity : AppCompatActivity() {

    private lateinit var btnBuyTicket: Button
    private lateinit var btnback: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_detail)

        btnBuyTicket = findViewById(R.id.btn_buy_ticket)

        btnBuyTicket.setOnClickListener {
            val ticketCheckoutIntent = Intent(this@TicketDetailActivity, TicketCheckoutActivity::class.java)
            startActivity(ticketCheckoutIntent)
        }

        btnback = findViewById(R.id.btn_back)
        btnback.setOnClickListener{
            val backk = Intent(this,HomeActivity::class.java)
            startActivity(backk)
        }
    }
}
