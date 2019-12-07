package id.co.bubui.tiketsayaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.*

class RegisterOneActivity : AppCompatActivity() {

    private lateinit var btnBack: LinearLayout
    private lateinit var btnContinue: Button

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtEmail: EditText

    //Firebase RealtimeDatabase
    private lateinit var reference: DatabaseReference // penyimpanan data secara lokal storage

    private var USERNAME_KEY = "username_key"
    private var username_key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_one)

        btnBack = findViewById(R.id.btn_back)
        btnContinue = findViewById(R.id.btn_continue)

        //casting EditText
        edtUsername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)
        edtEmail = findViewById(R.id.edt_email_address)

        //kembali ke halaman sign in
        btnBack.setOnClickListener(View.OnClickListener {
            val signInActivityIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInActivityIntent)
        })

        //menuju ke halaman register two
        btnContinue.setOnClickListener(View.OnClickListener {

            //menyimpan kepada lokal storage/smartphone
            val sharedPreferences: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(username_key, edtUsername.text.toString()).apply()

            //menyimpan ke firebase database
            reference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(edtUsername.text.toString())

            reference.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("username").setValue(edtUsername.text.toString())
                    dataSnapshot.ref.child("password").setValue(edtPassword.text.toString())
                    dataSnapshot.ref.child("email_address").setValue(edtEmail.text.toString())
                    dataSnapshot.ref.child("user_balance").setValue(25)
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {

                }
            })

            Toast.makeText(this, "Simpan ke Database",Toast.LENGTH_SHORT).show()
            // berpindah activity
            val registerActivityIntent = Intent(this, RegisterTwoActivity::class.java)
            startActivity(registerActivityIntent)
        })
    }
}
