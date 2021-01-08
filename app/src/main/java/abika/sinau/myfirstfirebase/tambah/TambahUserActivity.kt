package abika.sinau.myfirstfirebase.tambah

import abika.sinau.myfirstfirebase.R
import abika.sinau.myfirstfirebase.model.User
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tambah_user.*

class TambahUserActivity : AppCompatActivity() {
    // declare firebase
    private var db: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_user)

        initFirebase()
        initView()
    }

    private fun initFirebase() {
        db = FirebaseDatabase.getInstance()
    }

    private fun initView() {
        // klik btn tambah langsung insert ke database
        btnTambahUser.setOnClickListener {
            // get inputan user
            val nama = etNama.text.toString()
            val pekerjaan = etPekerjaan.text.toString()
            val hp = etHandphone.text.toString()
            val alamat = etAlamat.text.toString()

            // masukkan ke model terlebih dahulu
            val user = User(nama, pekerjaan, hp, alamat)

            // pengecekan kosong apa gak
            val myRef = db?.getReference("Users")

            // insert ke database
            myRef?.setValue(user)
        }
    }
}