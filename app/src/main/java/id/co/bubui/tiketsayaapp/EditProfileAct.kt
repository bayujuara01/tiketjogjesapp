package id.co.bubui.tiketsayaapp

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class EditProfileAct : AppCompatActivity() {

    private lateinit var btn_save: Button
    private lateinit var btn_kembali: LinearLayout
    private lateinit var photo_edit_profile : ImageView
    private lateinit var xnama_lengkap : EditText
    private lateinit var xbio : EditText
    private lateinit var xusername : EditText
    private lateinit var xpassword : EditText
    private lateinit var xemail : EditText
    private lateinit var btn_add_new_photo : Button

    private lateinit var reference: DatabaseReference
    private lateinit var storage: StorageReference

    private lateinit var photo_location: Uri
    private var PHOTO_MAX: Int = 1

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        getUsernameLocal()

        btn_save = findViewById(R.id.btn_save)
        btn_kembali = findViewById(R.id.btn_back)
        photo_edit_profile = findViewById(R.id.photo_edit_profile)
        xnama_lengkap = findViewById(R.id.xnama_lengkap)
        xbio = findViewById(R.id.xbio)
        xusername = findViewById(R.id.xusername)
        xpassword = findViewById(R.id.xpassword)
        xemail = findViewById(R.id.xemail)
        btn_add_new_photo = findViewById(R.id.btn_add_new_photo)

        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Users")
            .child(username_key_new)

        storage = FirebaseStorage
            .getInstance()
            .getReference()
            .child("Photousers")
            .child(username_key_new)

        reference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                xnama_lengkap.setText(dataSnapshot.child("nama_lengkap").value.toString())
                xbio.setText(dataSnapshot.child("bio").value.toString())
                xusername.setText(dataSnapshot.child("username").value.toString())
                xpassword.setText(dataSnapshot.child("password").value.toString())
                xemail.setText(dataSnapshot.child("email_address").value.toString())

                // mendapatkan alamat photo profil dari firebase
                val photoLink: String = dataSnapshot.child("url_photo_profile").value.toString()

                //load photo dari url/link
                Picasso.get()
                    .load(photoLink)
                    .centerCrop()
                    .fit()
                    .into(photo_edit_profile)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })


        btn_save.setOnClickListener {

            btn_save.isEnabled = false
            btn_save.setText("Loading ...")

            reference.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("nama_lengkap").setValue(xnama_lengkap.text.toString())
                    dataSnapshot.ref.child("bio").setValue(xbio.text.toString())
                    dataSnapshot.ref.child("username").setValue(xusername.text.toString())
                    dataSnapshot.ref.child("password").setValue(xpassword.text.toString())
                    dataSnapshot.ref.child("email_address").setValue(xemail.text.toString())

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

            if(photo_location != null){
                val mStorageReference: StorageReference = storage.child( System.currentTimeMillis().toString() + "." + getFileExtension(photo_location))

                mStorageReference.putFile(photo_location)
                    .addOnFailureListener {
                        //failure upload
                    }
                    .addOnSuccessListener {

                        mStorageReference.downloadUrl.addOnSuccessListener { uri ->
                            //Toast.makeText(this, "URL : $uri", Toast.LENGTH_LONG).show()

                            reference.ref.child("url_photo_profile").setValue(uri.toString())
                        }
                    }.addOnCompleteListener {
                        val gotoprofile= Intent(this,MyprofilAct::class.java)
                        startActivity(gotoprofile)
                    }
            }
        }

        btn_add_new_photo.setOnClickListener{
            findPhoto()
        }

        btn_kembali.setOnClickListener {
            val gotoprofile= Intent(this,MyprofilAct::class.java)
            startActivity(gotoprofile)
        }
    }

    fun findPhoto(){
        val pictureIntent = Intent()
        pictureIntent.setType("image/*")
        pictureIntent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(pictureIntent, PHOTO_MAX)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PHOTO_MAX && resultCode == Activity.RESULT_OK && data != null && data.data != null){

            photo_location = data.data!!
            Picasso.get()
                .load(photo_location)
                .centerCrop()
                .fit()
                .into(photo_edit_profile)

        }
    }

    fun getFileExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    fun getUsernameLocal(){
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }
}
