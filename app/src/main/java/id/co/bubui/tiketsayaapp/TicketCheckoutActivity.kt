package id.co.bubui.tiketsayaapp

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.firebase.database.*
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class TicketCheckoutActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val JENIS_WISATA = "nama_wisata"
        var nama_kategori = ""
        var nama_wisata_baru = ""
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
    private lateinit var edtDate: EditText

    private lateinit var reference: DatabaseReference
    private lateinit var reference2 : DatabaseReference
    private lateinit var reference3 : DatabaseReference
    private lateinit var reference4 : DatabaseReference

    private var ticketAmount: Int = 1
    private var myBalance : Int = 0
    private var totalHarga : Int = 0
    private var hargaTiket : Int = 0
    private var sisa_balance : Int = 0
    private var order_id : String = ""

    private var date_wisata = ""
    private var time_wisata = ""
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private var nomor_transaksi = Random.nextInt()
    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_checkout)
        getUsernameLocal()

        val mBundle: Bundle? = intent.extras
        val kategori: String? = mBundle?.getString("jenis_wisata")
        val jenisTiket: String? = mBundle?.getString(JENIS_WISATA)
        nama_kategori = kategori.toString()
        nama_wisata_baru = jenisTiket.toString()

        btnTicketPlus = findViewById(R.id.btn_ticket_plus)
        btnTicketMinus = findViewById(R.id.btn_ticket_minus)

        tvTicketAmount = findViewById(R.id.tv_ticket_amount)
        tvTicketTotalPrice = findViewById(R.id.tv_ticket_total_price)
        tvTotalBalance = findViewById(R.id.totalBalance)
        nama_wisata = findViewById(R.id.nama_wisata)
        lokasi = findViewById(R.id.lokasi)
        ketentuan = findViewById(R.id.ketentuan)
        edtDate = findViewById(R.id.edt_date_ticket)

        btnBack = findViewById(R.id.btn_back)
        btnBuyTicket = findViewById(R.id.btn_buy_ticket_ok)

        notice_uang = findViewById(R.id.notice_uang)

        btnTicketPlus.setOnClickListener(this)
        btnTicketMinus.setOnClickListener(this)
        btnBack.setOnClickListener(this)


        tvTicketAmount.text = ticketAmount.toString()


        btnTicketMinus.animate().alpha(0f).setDuration(300).start()
        //btnBuyTicket.isEnabled = false
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
            .child(kategori.toString())
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

        btnBuyTicket.setOnClickListener {
            order_id = nama_wisata.text.toString() + nomor_transaksi
            reference3 = FirebaseDatabase.getInstance()
                    .reference
                    .child("MyTickets")
                    .child(username_key_new)
                    .child(order_id)



            reference3.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    reference3.ref.child("id_tiket").setValue(order_id)
                    reference3.ref.child("nama_wisata").setValue(nama_wisata.text.toString())
                    reference3.ref.child("lokasi").setValue(lokasi.text.toString())
                    reference3.ref.child("ketentuan").setValue(ketentuan.text.toString())
                    reference3.ref.child("jumlah_tiket").setValue(ticketAmount.toString())
                    reference3.ref.child("date_wisata").setValue(edtDate.text.toString())
                    reference3.ref.child("time_wisata").setValue(time_wisata)
                    reference3.ref.child("total_harga").setValue(totalHarga)
                    reference3.ref.child("kategori").setValue(WisataListAct.nama_wisata)
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

            var random = Random.nextInt(999, 9999999)

            /*Make JSON*/
            var dataPaymentJson: JSONObject = JSONObject()

            var dataPaymentTransaction: JSONObject = JSONObject()
            dataPaymentTransaction.put("order_id", order_id)
            dataPaymentTransaction.put("gross_amount", totalHarga*1000)

            var dataPaymentDetailTransaction: JSONObject = JSONObject()
            dataPaymentDetailTransaction.put("id", order_id)
            dataPaymentDetailTransaction.put("price", totalHarga*1000)
            dataPaymentDetailTransaction.put("quantity", 1)
            dataPaymentDetailTransaction.put("name", "Tiket")

            var dataPaymentSecureCard: JSONObject = JSONObject()
            dataPaymentSecureCard.put("secure", true)

            var dataPaymentDetailArray: JSONArray = JSONArray()
            dataPaymentDetailArray.put(dataPaymentDetailTransaction)

            dataPaymentJson.put("transaction_details", dataPaymentTransaction)
            dataPaymentJson.put("credit_card", dataPaymentSecureCard)
            dataPaymentJson.put("item_details" ,dataPaymentDetailArray)

            /*Get from Midtrans*/
            val loading = ProgressDialog(this@TicketCheckoutActivity)
            loading.setTitle("Please Wait...")
            loading.show()

            AndroidNetworking.post(MidtransService.SERVER_URL)
                    .addHeaders("Authorization", MidtransService.SERVER_KEY)
                    .addJSONObjectBody(dataPaymentJson)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(object: JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject?) {

                            Toast.makeText(this@TicketCheckoutActivity, "URL : ${response?.getString("redirect_url")}", Toast.LENGTH_SHORT).show()
                            //add to database
                            reference3.addListenerForSingleValueEvent(object: ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    reference3.ref.child("url").setValue(response?.getString("redirect_url"))
                                    loading.dismiss()

                                    val intentPayment = Intent(this@TicketCheckoutActivity, PaymentWebViewActivity::class.java)
                                    intentPayment.putExtra(PaymentWebViewActivity.URL_KEY, response?.getString("redirect_url"))
                                    startActivity(intentPayment)
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    loading.dismiss()
                                }
                            })
                        }

                        override fun onError(anError: ANError?) {
                            loading.dismiss()
                            Toast.makeText(this@TicketCheckoutActivity, "Error : ${anError?.errorDetail} : ${anError?.errorBody} : ${anError?.response.toString()}", Toast.LENGTH_LONG).show()
                        }

                    })

//            intent = Intent(this, SuccessBuyTicketAct::class.java)
//            startActivity(intent)
//            val intentPayment = Intent(this, PaymentWebViewActivity::class.java)
//            startActivity(intentPayment)
        }

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            updateDateInView()
        }

        edtDate.setOnClickListener {
            DatePickerDialog(this@TicketCheckoutActivity, dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

    }

    private fun updateDateInView() {
        val myFormat = "dd MMMM, yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        edtDate.setText(sdf.format(calendar.time))
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
                kembaliIntent.putExtra("jenis_wisata", nama_kategori)
                kembaliIntent.putExtra("nama_wisata", nama_wisata_baru)
                startActivity(kembaliIntent)
            }
        }
    }

    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }
}
