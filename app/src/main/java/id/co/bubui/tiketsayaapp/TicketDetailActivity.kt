package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class TicketDetailActivity : AppCompatActivity() {

    companion object {
        const val JENIS_WISATA = "jenis_wisata"
    }

    private lateinit var btnBuyTicket: Button
    private lateinit var btnback: LinearLayout
    private lateinit var tvPhotoSpot: TextView
    private lateinit var tvWifi: TextView
    private lateinit var tvFestival: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvShortDesc: TextView
    private lateinit var imgHeader: ImageView

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_detail)

        val mBundle: Bundle? = intent.extras
        val jenisTiket: String? = mBundle?.getString(JENIS_WISATA)
        val namawisata: String? = mBundle?.getString("nama_wisata")

        Toast.makeText(this, namawisata, Toast.LENGTH_SHORT).show()

        btnBuyTicket = findViewById(R.id.btn_buy_ticket)
        tvPhotoSpot = findViewById(R.id.tv_photo_spot_ticket)
        tvWifi = findViewById(R.id.tv_wifi_ticket)
        tvFestival = findViewById(R.id.tv_festival_ticket)
        tvLocation = findViewById(R.id.tv_location_ticket)
        tvTitle = findViewById(R.id.tv_title_ticket)
        tvShortDesc = findViewById(R.id.tv_short_desc)
        imgHeader = findViewById(R.id.img_bg_header)

        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Wisata")
            .child(jenisTiket.toString())
            .child(namawisata.toString())

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tvTitle.text = dataSnapshot.child("nama_wisata").value.toString()
                tvLocation.text = dataSnapshot.child("lokasi").value.toString()
                tvPhotoSpot.text = dataSnapshot.child("is_photo_spot").value.toString()
                tvWifi.text = dataSnapshot.child("is_wifi").value.toString()
                tvFestival.text = dataSnapshot.child("is_festival").value.toString()
                tvShortDesc.text = dataSnapshot.child("short_desc").value.toString()

                val urlHeader: String = dataSnapshot.child("url_thubnail").value.toString()

                //set image to imgHeader
                Picasso.get()
                    .load(urlHeader)
                    .centerCrop()
                    .fit()
                    .into(imgHeader)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        btnBuyTicket.setOnClickListener {
            val ticketCheckoutIntent = Intent(this@TicketDetailActivity, TicketCheckoutActivity::class.java)
            ticketCheckoutIntent.putExtra("jenis_wisata", jenisTiket)
            ticketCheckoutIntent.putExtra(TicketCheckoutActivity.JENIS_WISATA, namawisata)
            startActivity(ticketCheckoutIntent)
        }

        btnback = findViewById(R.id.btn_back)
        btnback.setOnClickListener{
            val backk = Intent(this,WisataListAct::class.java)
            backk.putExtra("jenis_wisata", jenisTiket)
            startActivity(backk)
        }
    }
}
