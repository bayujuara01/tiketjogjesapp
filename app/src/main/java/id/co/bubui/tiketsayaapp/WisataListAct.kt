package id.co.bubui.tiketsayaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class WisataListAct : AppCompatActivity() {

    private lateinit var wisata_place : RecyclerView
    private var wisatalist : ArrayList<WisataList> = arrayListOf()

    private lateinit var wisataAdapter: WisataListAdapter
    private lateinit var nama_kategori: TextView
    private lateinit var btn_back_home : Button

    private lateinit var reference2 : DatabaseReference

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wisata_list)
        wisata_place = findViewById(R.id.wisata_place)
        wisata_place.layoutManager = LinearLayoutManager(this)
        nama_kategori = findViewById(R.id.nama_kategori)
        btn_back_home = findViewById(R.id.btn_back_home)

        val mBundle: Bundle? = intent.extras
        val jenis_wisata_baru: String? = mBundle?.getString(JENIS_WISATA)

        getUsernameLocal()

        nama_kategori.text = jenis_wisata_baru
        nama_wisata = jenis_wisata_baru.toString()

        reference2 = FirebaseDatabase.getInstance().reference.child("Wisata").child(jenis_wisata_baru.toString())
        reference2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (datasnapshot1 : DataSnapshot in dataSnapshot.children) {
                    var p : WisataList = datasnapshot1.getValue(WisataList::class.java) as WisataList
                    wisatalist.add(p)
                }
                wisataAdapter = WisataListAdapter(wisatalist)
                wisata_place.adapter = wisataAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        btn_back_home.setOnClickListener{
            val backk = Intent(this,HomeActivity::class.java)
            startActivity(backk)
        }

    }

    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }

    companion object {
        const val JENIS_WISATA = "jenis_wisata"
        var nama_wisata = ""
    }
}
