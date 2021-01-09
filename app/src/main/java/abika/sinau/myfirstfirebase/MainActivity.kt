package abika.sinau.myfirstfirebase

import abika.sinau.myfirstfirebase.adapter.UserAdapter
import abika.sinau.myfirstfirebase.model.User
import abika.sinau.myfirstfirebase.tambah.TambahUserActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        initFirebase()
        initView()

        // set real-time database
//        realTimeDatabase()

        // getData Users
        getDataUsers()
    }

    private fun getDataUsers() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Users") // mengambil data dari DB Users

        val dataUser = ArrayList<User>()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                val value = snapshot.getValue(User::class.java)
//                Log.d(TAG, "Value is: $value")

                for(datas in snapshot.children){
                    // get per masing2 field
                    val nama = datas.child("name").value.toString()
                    val pekerjaan = datas.child("pekerjaan").value.toString()
                    val hp = datas.child("hp").value.toString()
                    val alamat = datas.child("alamat").value.toString()

                    val user = User(nama, pekerjaan, hp, alamat)

                    dataUser.add(user)
                    showDataUser(dataUser)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showDataUser(dataUser: java.util.ArrayList<User>) {
        val adapter = UserAdapter(dataUser)

        rvListUser.adapter = adapter
    }

    private fun realTimeDatabaseMessage() {
        // insert data real-time database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message") // message akan masuk ke dalam realtimeDB di firebase

        myRef.setValue("Hello, world!")

        // get data real-time database
        // jika menggunakan listener di bawah ini, data tidak akan diupdate secara real-time
//        myRef.addListenerForSingleValueEvent()

        // jika menggunakan listener di bawah ini, data akan diupdate secara real-time
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue<String>()

                val value = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: $value")

                // bind data ke UI
//                tv_main.text = value

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }

    private fun initFirebase() {
        auth = Firebase.auth
    }

    private fun initView() {
        // TODO 9: InitFirebase untuk mendapatkan data email yang sedang digunakan
        btnAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, TambahUserActivity::class.java)
            startActivity(intent)
        }

//        val user = auth.currentUser

//        user.let {
//            tv_main.text = "Email: ${user?.email}"
//        }
    }
}