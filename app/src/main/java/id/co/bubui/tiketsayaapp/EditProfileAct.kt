package id.co.bubui.tiketsayaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EditProfileAct : AppCompatActivity() {
    private lateinit var btn_lanjut: Button;
    private lateinit var btn_kembali: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        btn_lanjut = findViewById(R.id.btn_continue);
        btn_kembali = findViewById(R.id.btn_back);
        btn_lanjut.setOnClickListener {
            val gotohome= Intent(this,HomeActivity::class.java)
            startActivity(gotohome);
        }
        btn_kembali.setOnClickListener {
            val gotoprofile= Intent(this,EditProfileAct::class.java)
            startActivity(gotoprofile);
        }
    }
}
