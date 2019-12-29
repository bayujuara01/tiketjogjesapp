package id.co.bubui.tiketsayaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnTicketPisa: LinearLayout
    private lateinit var btnTicketTorri: LinearLayout
    private lateinit var btnTicketPagoda: LinearLayout
    private lateinit var btnTicketSphinx: LinearLayout
    private lateinit var btnTicketCandi: LinearLayout
    private lateinit var btnTicketMonas: LinearLayout

    private lateinit var btnprofile: View
    private lateinit var tvNamaLengkap: TextView
    private lateinit var tvBio: TextView
    private lateinit var tvUserBalance: TextView
    private lateinit var imgPhotoProfile: CircleImageView

    private lateinit var reference: DatabaseReference

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getUsernameLocal()

        btnTicketPisa = findViewById(R.id.btn_ticket_pisa)
        btnTicketCandi = findViewById(R.id.btn_ticket_candi)
        btnTicketMonas = findViewById(R.id.btn_ticket_monas)
        btnTicketPagoda = findViewById(R.id.btn_ticket_pagoda)
        btnTicketTorri = findViewById(R.id.btn_ticket_torri)
        btnTicketSphinx = findViewById(R.id.btn_ticket_sphinx)

        btnprofile = findViewById(R.id.btn_to_profile)
        tvNamaLengkap = findViewById(R.id.tv_nama_lengkap_home)
        tvBio = findViewById(R.id.tv_bio_home)
        tvUserBalance = findViewById(R.id.tv_user_balance)
        imgPhotoProfile = findViewById(R.id.pict_photo_home_user)

        //Database firebase get Data Home
        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Users")
            .child(username_key_new)

        reference.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tvNamaLengkap.text = dataSnapshot.child("nama_lengkap").value.toString()
                tvBio.text = dataSnapshot.child("bio").value.toString()
                tvUserBalance.text = "Rp " + dataSnapshot.child("user_balance").value.toString() + "K"

                // mendapatkan alamat photo profil dari firebase
                val photoLink: String = dataSnapshot.child("url_photo_profile").value.toString()

                //load photo dari url/link
                Picasso.get()
                    .load(photoLink)
                    .centerCrop()
                    .fit()
                    .into(imgPhotoProfile)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        // pergi ke detail activity
        btnTicketPisa.setOnClickListener(this)
        btnTicketSphinx.setOnClickListener(this)
        btnTicketTorri.setOnClickListener(this)
        btnTicketPagoda.setOnClickListener(this)
        btnTicketMonas.setOnClickListener(this)
        btnTicketCandi.setOnClickListener(this)

        // pergi ke MyprofileAct
        btnprofile.setOnClickListener {
            val gotoprofileIntent = Intent(this, MyprofilAct::class.java)
            startActivity(gotoprofileIntent)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            btnTicketPisa.id -> {
                val gotoTicketIntent = Intent(this, TicketDetailActivity::class.java)
                gotoTicketIntent.putExtra(TicketDetailActivity.JENIS_WISATA, "Hutan")
                startActivity(gotoTicketIntent)
            }
            btnTicketCandi.id -> {
                val gotoTicketIntent = Intent(this, TicketDetailActivity::class.java)
                gotoTicketIntent.putExtra(TicketDetailActivity.JENIS_WISATA, "Candi")
                startActivity(gotoTicketIntent)
            }
            btnTicketMonas.id ->{
                val gotoTicketIntent = Intent(this, TicketDetailActivity::class.java)
                gotoTicketIntent.putExtra(TicketDetailActivity.JENIS_WISATA, "Benteng")
                startActivity(gotoTicketIntent)
            }
            btnTicketPagoda.id -> {
                val gotoTicketIntent = Intent(this, TicketDetailActivity::class.java)
                gotoTicketIntent.putExtra(TicketDetailActivity.JENIS_WISATA, "Merapi")
                startActivity(gotoTicketIntent)
            }
            btnTicketSphinx.id -> {
                val gotoTicketIntent = Intent(this, TicketDetailActivity::class.java)
                gotoTicketIntent.putExtra(TicketDetailActivity.JENIS_WISATA, "Pantai")
                startActivity(gotoTicketIntent)
            }
            btnTicketTorri.id -> {
                val gotoTicketIntent = Intent(this, TicketDetailActivity::class.java)
                gotoTicketIntent.putExtra(TicketDetailActivity.JENIS_WISATA, "Jalan")
                startActivity(gotoTicketIntent)
            }
        }
    }

    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }
}
