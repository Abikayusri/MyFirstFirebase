package abika.sinau.myfirstfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()
        initView()
    }

    private fun initFirebase() {
        auth = Firebase.auth
    }

    private fun initView() {
        // TODO 9: InitFirebase untuk mendapatkan data email yang sedang digunakan
        val user = auth.currentUser

        user.let {
            tv_main.text = "Email: ${user?.email}"
        }
    }
}