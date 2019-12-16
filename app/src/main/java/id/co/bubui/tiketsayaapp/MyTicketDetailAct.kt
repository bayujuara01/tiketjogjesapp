package id.co.bubui.tiketsayaapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.*

class MyTicketDetailAct : AppCompatActivity() {

    private lateinit var btn_kembali: LinearLayout
    private lateinit var xnama_wisata : TextView
    private lateinit var xlokasi : TextView
    private lateinit var xdate_wisata : TextView
    private lateinit var xtime_wisata : TextView
    private lateinit var xketentuan : TextView

    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ticket_detail)

        val mBundle: Bundle? = intent.extras
        val nama_wisata_baru: String? = mBundle?.getString("nama_wisata")

        xnama_wisata = findViewById(R.id.xnama_wisata)
        xlokasi = findViewById(R.id.xlokasi)
        xdate_wisata = findViewById(R.id.xdate_wisata)
        xtime_wisata = findViewById(R.id.xtime_wisata)
        xketentuan = findViewById(R.id.xketentuan)

        btn_kembali = findViewById(R.id.btn_back)

        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Wisata")
            .child(nama_wisata_baru.toString())

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                xnama_wisata.text = dataSnapshot.child("nama_wisata").value.toString()
                xlokasi.text = dataSnapshot.child("lokasi").value.toString()
                xketentuan.text = dataSnapshot.child("ketentuan").value.toString()
                xdate_wisata.text = dataSnapshot.child("date_wisata").value.toString()
                xtime_wisata.text = dataSnapshot.child("time_wisata").value.toString()
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        btn_kembali.setOnClickListener {
            val gotoprofile= Intent(this,MyprofilAct::class.java)
            startActivity(gotoprofile)
        }
    }
}
