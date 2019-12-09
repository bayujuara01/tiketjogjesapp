 package id.co.bubui.tiketsayaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

 class MyprofilAct : AppCompatActivity() {

    private lateinit var btn_edit_profile : Button
    private lateinit var btn_signout : Button
    private lateinit var item_my_ticket : LinearLayout
    private lateinit var photo_profile : ImageView
    private lateinit var nama_lengkap : TextView

    private lateinit var reference : DatabaseReference
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofil)

        getUsernameLocal()

        btn_edit_profile = findViewById(R.id.btn_editprofile)
        item_my_ticket = findViewById(R.id.ticket)
        btn_signout = findViewById(R.id.btn_sign_out)
        photo_profile = findViewById(R.id.photo_profile)
        nama_lengkap = findViewById(R.id.nama_lengkap)

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                nama_lengkap.text = dataSnapshot.child("nama_lengkap").getValue().toString()
                Picasso.get()
                    .load(dataSnapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit()
                    .into(photo_profile)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
         reference.addListenerForSingleValueEvent(postListener)

        btn_edit_profile.setOnClickListener {
            val gotoeditprofile = Intent(this, EditProfileAct::class.java)
            startActivity(gotoeditprofile);
        }

        item_my_ticket.setOnClickListener {
            intent = Intent(this, MyTicketDetailAct::class.java)
            startActivity(intent)
        }

        btn_signout.setOnClickListener {
            val sharedPreferences: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(username_key, null).apply()
            val intent_signout = Intent(this, SignInActivity::class.java)
            startActivity(intent_signout)
            finish()
        }
    }

     fun getUsernameLocal(){
         val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
         username_key_new = sharedPreference.getString(username_key, "").toString()
     }
}
