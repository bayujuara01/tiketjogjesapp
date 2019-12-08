package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MyprofilAct : AppCompatActivity() {

    private lateinit var btn_edit_profile:Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofil)

        btn_edit_profile = findViewById(R.id.btn_editprofile);
        btn_edit_profile.setOnClickListener {
            val gotoeditprofile=Intent(this,MyTicketDetailAct::class.java)
            startActivity(gotoeditprofile);
        }
    }
}
