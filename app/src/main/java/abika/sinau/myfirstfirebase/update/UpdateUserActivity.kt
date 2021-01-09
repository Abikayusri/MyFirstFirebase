package abika.sinau.myfirstfirebase.update

import abika.sinau.myfirstfirebase.R
import abika.sinau.myfirstfirebase.model.User
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_user.*

class UpdateUserActivity : AppCompatActivity() {
    // declare firebase
    private var db: FirebaseDatabase? = null
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        getParcel()
        initFirebase()
        initView()
    }

    private fun getParcel() {
        user = intent.getSerializableExtra("data") as User

        // bind ke view
        etNama.setText(user?.name)
        etPekerjaan.setText(user?.pekerjaan)
        etHandphone.setText(user?.hp)
        etAlamat.setText(user?.alamat)
    }

    private fun initFirebase() {
        db = FirebaseDatabase.getInstance()
    }

    private fun initView() {
        // klik btn tambah langsung insert ke database
        btnUpdateUser.setOnClickListener {
            // get inputan user
            val nama = etNama.text.toString()
            val pekerjaan = etPekerjaan.text.toString()
            val hp = etHandphone.text.toString()
            val alamat = etAlamat.text.toString()

            // masukkan ke model terlebih dahulu
            val user2 = User(nama, pekerjaan, hp, alamat)

            // pengecekan kosong apa gak
            val myRef = db?.getReference("Users")

            // get key
//            val key = myRef?.push()?.key

            // insert to database with auto increment
            myRef?.child(user?.key ?: "")?.setValue(user2)

            finish()
        }
    }
}