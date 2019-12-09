package id.co.bubui.tiketsayaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*

class SignInActivity : AppCompatActivity() {

    private lateinit var btnNewAccount: TextView
    private lateinit var btnSignIn: Button
    private lateinit var username: EditText
    private lateinit var password: EditText

    private lateinit var reference: DatabaseReference
    private var USERNAME_KEY = "username_key"
    private var username_key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnNewAccount = findViewById(R.id.btn_new_account)
        btnSignIn = findViewById(R.id.btn_sign_in)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)

        //menuju balaman resgister
        btnNewAccount.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegisterOneActivity::class.java)
            startActivity(intent)
         })

        //menuju dashboard
        btnSignIn.setOnClickListener(View.OnClickListener {

            val dUsername: String = username.text.toString()
            val dPassword: String = password.text.toString()

            reference = FirebaseDatabase.getInstance()
                .reference
                .child("Users")
                .child(dUsername)

            reference.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists() && dUsername != ""){

//                        Toast.makeText(this@SignInActivity, "Username Ditemukan", Toast.LENGTH_SHORT).show()
                        //Ambil data dari firebase (password)
                        val passwordFromFirebase: String = dataSnapshot.child("password").value.toString()

                        //validasi password firebase
                        if(dPassword == passwordFromFirebase){
                            //menyimpan username ke local
                            val sharedPreferences: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString(username_key, username.text.toString()).apply()

                            //berpindah activity
                            val gotoHomeIntent = Intent(this@SignInActivity, HomeActivity::class.java)
                            startActivity(gotoHomeIntent)
                        } else {
                            Toast.makeText(this@SignInActivity, "Password Salah", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this@SignInActivity, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        })
    }

}
