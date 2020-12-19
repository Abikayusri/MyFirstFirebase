package abika.sinau.myfirstfirebase.register

import abika.sinau.myfirstfirebase.R
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    // TODO 5: Inisialisasi firebasee
    private lateinit var auth: FirebaseAuth
    private var TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initFirebase()
        initView()
    }

    // TODO 6: Initialize Firebase Auth
    private fun initFirebase() {
        auth = Firebase.auth
    }

    // TODO 7: Inisialisasi function
    private fun initView() {

        btnRegister.setOnClickListener {
            val email = etEmailRegister.text.toString()
            val password = etPasswordRegister.text.toString()
            val confPassword = etConfirmationPasswordRegister.text.toString()

            when {
                email.isEmpty() -> {
                    etEmailRegister.error = "Email tidak boleh kosong!"
                }
                password.isEmpty() -> {
                    etPasswordRegister.error = "Password tidak boleh kosong"
                }
                // password length tidak boleh kurang dari 6
                password.length < 6 -> {
                    etPasswordRegister.error = "Password tidak boleh kurang dari 6"
                }
                confPassword.isEmpty() -> {
                    etConfirmationPasswordRegister.error =
                        "Confirmation Password tidak boleh kosong"
                }
                password != confPassword -> {
                    etConfirmationPasswordRegister.error = "Confirmation Password tidak cocok!"
                }
                else -> {
                    actionRegister(email, password)
                }
            }
        }
    }

    /** Fungsi ini digunakan untuk mengirimkan data register ke firebase project **/
    private fun actionRegister(email: String?, password: String?) {

        auth.createUserWithEmailAndPassword(email ?: "", password ?: "")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    finish()
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
            }
    }
}