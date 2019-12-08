package id.co.bubui.tiketsayaapp

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.fragment.app.FragmentActivity


class RegisterTwoActivity : AppCompatActivity() {

    private lateinit var btnBack: LinearLayout
    private lateinit var btnContinue: Button
    private lateinit var imgPicPhotoUser: ImageView
    private lateinit var btnAddPhoto: Button
    private lateinit var edtNamaLengkap: EditText
    private lateinit var edtBio: EditText

    private lateinit var reference: DatabaseReference
    private lateinit var storage: StorageReference

    private lateinit var photo_location: Uri
    private var PHOTO_MAX: Int = 1

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_two)

        getUsernameLocal()

        btnBack = findViewById(R.id.btn_back)
        btnContinue = findViewById(R.id.btn_continue)
        btnAddPhoto = findViewById(R.id.btn_add_photo)
        imgPicPhotoUser = findViewById(R.id.pict_photo_register_user)
        edtNamaLengkap = findViewById(R.id.edt_nama_lengkap)
        edtBio = findViewById(R.id.edt_bio)


        btnBack.setOnClickListener {
            val gotoPrevIntent = Intent(this, RegisterOneActivity::class.java)
            startActivity(gotoPrevIntent)
        }

        btnContinue.setOnClickListener {
            // ubah state menjadi loading
            btnContinue.isEnabled = false
            btnContinue.setText("Loading ...")
            //menyimpan ke database
            reference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(username_key_new)

            storage = FirebaseStorage
                .getInstance()
                .getReference()
                .child("Photousers")
                .child(username_key_new)

            if(photo_location != null){
                val mStorageReference: StorageReference = storage.child( System.currentTimeMillis().toString() + "." + getFileExtension(photo_location))

                mStorageReference.putFile(photo_location)
                    .addOnFailureListener {
                        //failure upload
                    }
                    .addOnSuccessListener {

                        reference.ref.child("nama_lengkap").setValue(edtNamaLengkap.text.toString())
                        reference.ref.child("bio").setValue(edtBio.text.toString())

                        mStorageReference.downloadUrl.addOnSuccessListener { uri ->
                            //Toast.makeText(this, "URL : $uri", Toast.LENGTH_LONG).show()

                            reference.ref.child("url_photo_profile").setValue(uri.toString())
                        }
                    }.addOnCompleteListener {
                        val gotoSuccessIntent = Intent(this, SuccessRegisterActivity::class.java)
                        startActivity(gotoSuccessIntent)
                    }
            }
        }

        btnAddPhoto.setOnClickListener {
            findPhoto()
        }
    }

    //function find photo
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
                .into(imgPicPhotoUser)

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
