package id.co.bubui.tiketsayaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlin.random.Random

class TicketCheckoutActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val JENIS_WISATA = "jenis_wisata"
    }

    private lateinit var btnTicketPlus: Button
    private lateinit var btnTicketMinus: Button
    private  lateinit var btnBack : LinearLayout
    private lateinit var tvTicketAmount: TextView
    private lateinit var tvTicketTotalPrice: TextView
    private lateinit var btnBuyTicket : Button
    private lateinit var tvTotalBalance : TextView
    private lateinit var notice_uang : ImageView
    private lateinit var nama_wisata : TextView
    private lateinit var lokasi : TextView
    private lateinit var ketentuan : TextView

    private lateinit var reference: DatabaseReference
    private lateinit var reference2 : DatabaseReference
    private lateinit var reference3 : DatabaseReference
    private lateinit var reference4 : DatabaseReference

    private var ticketAmount: Int = 1
    private var myBalance : Int = 0
    private var totalHarga : Int = 0
    private var hargaTiket : Int = 0
    private var sisa_balance : Int = 0
    private var date_wisata = ""
    private var time_wisata = ""
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private var nomor_transaksi = Random.nextInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_checkout)
        getUsernameLocal()

        val mBundle: Bundle? = intent.extras
        val jenisTiket: String? = mBundle?.getString(JENIS_WISATA)

        btnTicketPlus = findViewById(R.id.btn_ticket_plus)
        btnTicketMinus = findViewById(R.id.btn_ticket_minus)

        tvTicketAmount = findViewById(R.id.tv_ticket_amount)
        tvTicketTotalPrice = findViewById(R.id.tv_ticket_total_price)
        tvTotalBalance = findViewById(R.id.totalBalance)
        nama_wisata = findViewById(R.id.nama_wisata)
        lokasi = findViewById(R.id.lokasi)
        ketentuan = findViewById(R.id.ketentuan)

        btnBack = findViewById(R.id.btn_back)
        btnBuyTicket = findViewById(R.id.btn_buy_ticket_ok)

        notice_uang = findViewById(R.id.notice_uang)

        btnTicketPlus.setOnClickListener(this)
        btnTicketMinus.setOnClickListener(this)
        btnBack.setOnClickListener(this)

        tvTicketAmount.text = ticketAmount.toString()


        btnTicketMinus.animate().alpha(0f).setDuration(300).start()
        btnBuyTicket.isEnabled = false
        notice_uang.visibility = View.GONE

        //mengambil user balance dari firebase
        reference2 = FirebaseDatabase.getInstance()
            .reference
            .child("Users")
            .child(username_key_new)

        reference2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                myBalance =Integer.valueOf(dataSnapshot.child("user_balance").value.toString())
                tvTotalBalance.text = "Rp ${myBalance}K"
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        //mengambil data tempat wisata dari firebase
        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Wisata")
            .child(jenisTiket.toString())

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                nama_wisata.text = dataSnapshot.child("nama_wisata").value.toString()
                lokasi.text = dataSnapshot.child("lokasi").value.toString()
                ketentuan.text = dataSnapshot.child("ketentuan").value.toString()
                date_wisata = dataSnapshot.child("date_wisata").value.toString()
                time_wisata = dataSnapshot.child("time_wisata").value.toString()
                hargaTiket =Integer.valueOf(dataSnapshot.child("harga_tiget").value.toString())
                totalHarga = ticketAmount*hargaTiket
                tvTicketTotalPrice.text = "Rp ${totalHarga}K"
                if(totalHarga > myBalance) {
                    btnBuyTicket.animate().translationY(250f).alpha(0f).setDuration(350).start()
                    btnBuyTicket.isEnabled = false
                    tvTotalBalance.setTextColor(Color.parseColor("#D1206B"))
                    notice_uang.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        //menyimpan data user ke firebase dan membuat tabel baru (MyTickets)

        btnBuyTicket.setOnClickListener(View.OnClickListener {
            reference3 = FirebaseDatabase.getInstance()
                .reference
                .child("MyTickets")
                .child(username_key_new)
                .child(nama_wisata.text.toString() + nomor_transaksi)

            reference3.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    reference3.ref.child("id_tiket").setValue(nama_wisata.text.toString() + nomor_transaksi)
                    reference3.ref.child("nama_wisata").setValue(nama_wisata.text.toString())
                    reference3.ref.child("lokasi").setValue(lokasi.text.toString())
                    reference3.ref.child("ketentuan").setValue(ketentuan.text.toString())
                    reference3.ref.child("jumlah_tiket").setValue(ticketAmount.toString())
                    reference3.ref.child("date_wisata").setValue(date_wisata)
                    reference3.ref.child("time_wisata").setValue(time_wisata)
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

            reference4 = FirebaseDatabase.getInstance()
                .reference
                .child("Users")
                .child(username_key_new)
            reference4.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    sisa_balance = myBalance - totalHarga
                    reference4.ref.child("user_balance").setValue(sisa_balance)
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })


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

                totalHarga = ticketAmount*hargaTiket
                tvTicketTotalPrice.text = "Rp ${totalHarga}K"
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

                    totalHarga = ticketAmount*hargaTiket
                    tvTicketTotalPrice.text = "Rp ${totalHarga}K"
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

    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }
}
