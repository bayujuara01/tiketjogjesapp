package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class TicketCheckoutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnTicketPlus: Button
    private lateinit var btnTicketMinus: Button
    private  lateinit var btnBack : LinearLayout
    private lateinit var tvTicketAmount: TextView
    private lateinit var tvTicketTotalPrice: TextView

    private var ticketAmount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_checkout)

        btnTicketPlus = findViewById(R.id.btn_ticket_plus)
        btnTicketMinus = findViewById(R.id.btn_ticket_minus)

        tvTicketAmount = findViewById(R.id.tv_ticket_amount)
        tvTicketTotalPrice = findViewById(R.id.tv_ticket_total_price)

        btnBack = findViewById(R.id.btn_back)
//
        btnTicketPlus.setOnClickListener(this)
        btnTicketMinus.setOnClickListener(this)
        btnBack.setOnClickListener(this)

        val btnBuyTicket = findViewById<Button>(R.id.btn_buy_ticket_ok)
        btnBuyTicket.setOnClickListener(View.OnClickListener {
            intent = Intent(this, SuccessBuyTicketAct::class.java)
            startActivity(intent)
        })

    }

    override fun onClick(v: View?) {
        when(v?.id){
            btnTicketPlus.id -> {
                ticketAmount++
                tvTicketAmount.text = ticketAmount.toString()

                tvTicketTotalPrice.text = "Rp ${ticketAmount*50}K"
            }

            btnTicketMinus.id -> {
                if(ticketAmount != 0) {
                    ticketAmount--
                    tvTicketAmount.text = ticketAmount.toString()

                    tvTicketTotalPrice.text = "Rp ${ticketAmount*50}K"
                }
            }

            btnBack.id -> {
                val kembaliIntent = Intent(this, TicketDetailActivity::class.java)
                startActivity(kembaliIntent)
            }
        }
    }
}
