package abika.sinau.myfirstfirebase.login

import abika.sinau.myfirstfirebase.MainActivity
import abika.sinau.myfirstfirebase.R
import abika.sinau.myfirstfirebase.register.RegisterActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val TAG = "LoginActivity"

    // TODO 12: Tambahkan variable global untuk Google Sign In
    private var client: GoogleSignInClient? = null
    private val signIn = 101

    companion object {
        private const val RC_SIGN_IN = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initFirebase()
        initGmail()
        initView()
    }

    // TODO 5: Initialize Firebase Auth
    private fun initFirebase() {
//        mAuth = Firebase.auth
        mAuth = FirebaseAuth.getInstance()

        // TODO 8: Menambahkan current user sebagai pengganti shared preferences
//        if (mAuth.currentUser?.email?.isNotEmpty() == true) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
    }

    private fun initView() {
        btnLogin.setOnClickListener {
            val email = etEmailLogin.text.toString()
            val password = etPasswordLogin.text.toString()

            when {
                email.isEmpty() -> {
                    etEmailLogin.error = "Email tidak boleh kosong!"
                }
                password.isEmpty() -> {
                    etPasswordLogin.error = "Password tidak boleh kosong!"
                }
                else -> {
                    actionLogin(email, password)
                }
            }
        }

        btn_sign_in_gmail.setOnClickListener {
            signIn()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    /** Fungsi ini digunakan untuk mengirimkan data login ke firebase project **/
    private fun actionLogin(email: String?, password: String?) {
        mAuth.signInWithEmailAndPassword(email ?: "", password ?: "")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    //                    val user = mAuth.currentUser
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
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

    private fun initGmail() {
        // TODO 11: Tambahkan konfigurasi berikut untuk konek ke gmail
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        client = GoogleSignIn.getClient(this, gso)
    }

    // TODO 13: Masukkan fungsi berikut untuk memamnggil activity Result dari Gmail
    private fun signIn() {
        val signInIntent = client?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // TODO 14: Tambahkan onActivityResult untuk mendapatkan umpan balik pada code sebelumnya
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                }
            } else {
                Log.w(TAG, "Google sign in failed", exception)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Login Gagal!", Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
}