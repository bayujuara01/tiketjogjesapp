package id.co.bubui.tiketsayaapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class TicketCheckoutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnTicketPlus: Button
    private lateinit var btnTicketMinus: Button
    private  lateinit var btnBack : LinearLayout
    private lateinit var tvTicketAmount: TextView
    private lateinit var tvTicketTotalPrice: TextView
    private lateinit var btnBuyTicket : Button
    private lateinit var tvTotalBalance : TextView
    private lateinit var notice_uang : ImageView

    private var ticketAmount: Int = 1
    private var myBalance : Int = 1000
    private var totalHarga : Int = 0
    private var hargaTiket : Int = 150

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_checkout)

        btnTicketPlus = findViewById(R.id.btn_ticket_plus)
        btnTicketMinus = findViewById(R.id.btn_ticket_minus)

        tvTicketAmount = findViewById(R.id.tv_ticket_amount)
        tvTicketTotalPrice = findViewById(R.id.tv_ticket_total_price)
        tvTotalBalance = findViewById(R.id.totalBalance)

        btnBack = findViewById(R.id.btn_back)
        btnBuyTicket = findViewById(R.id.btn_buy_ticket_ok)

        notice_uang = findViewById(R.id.notice_uang)

        btnTicketPlus.setOnClickListener(this)
        btnTicketMinus.setOnClickListener(this)
        btnBack.setOnClickListener(this)

        tvTicketAmount.text = ticketAmount.toString()
        totalHarga = ticketAmount*hargaTiket
        tvTicketTotalPrice.text = "Rp ${ticketAmount*hargaTiket}K"
        tvTotalBalance.text = "Rp ${myBalance}K"


        btnTicketMinus.animate().alpha(0f).setDuration(300).start()
        btnBuyTicket.isEnabled = false
        notice_uang.visibility = View.GONE

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

                if(ticketAmount > 1) {
                    btnTicketMinus.animate().alpha(1f).setDuration(300).start()
                    btnBuyTicket.isEnabled = true
                }

                tvTicketTotalPrice.text = "Rp ${ticketAmount*hargaTiket}K"
                totalHarga = ticketAmount*hargaTiket
                if(totalHarga > myBalance) {
                    btnBuyTicket.animate().translationY(250f).alpha(0f).setDuration(350).start()
                    btnBuyTicket.isEnabled = false
                    tvTotalBalance.setTextColor(Color.parseColor("#D1206B"))
                    notice_uang.visibility = View.VISIBLE
                }
            }

            btnTicketMinus.id -> {
                if(ticketAmount != 1) {
                    ticketAmount--
                    tvTicketAmount.text = ticketAmount.toString()

                    if(ticketAmount < 2) {
                        btnTicketMinus.animate().alpha(0f).setDuration(300).start()
                        btnBuyTicket.isEnabled = false
                    }

                    tvTicketTotalPrice.text = "Rp ${ticketAmount*hargaTiket}K"
                    totalHarga = ticketAmount*hargaTiket
                    if(totalHarga < myBalance) {
                        btnBuyTicket.animate().translationY(0f).alpha(1f).setDuration(350).start()
                        btnBuyTicket.isEnabled = true
                        tvTotalBalance.setTextColor(Color.parseColor("#203DD1"))
                        notice_uang.visibility = View.GONE
                    }
                }
            }

            btnBack.id -> {
                val kembaliIntent = Intent(this, TicketDetailActivity::class.java)
                startActivity(kembaliIntent)
            }
        }
    }
}
