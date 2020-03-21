package com.example.sqlite_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sqlite_01.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    lateinit var  handler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        handler = DatabaseHandler(this)

        registerBtn.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            finish()
        }
    }

//    ***
//    fungsi login
//    ***

    fun login(view: View) {
        val emailLogin = emailLog!!.text.toString()
        val passLogin = passLog!!.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

//        ***
//        fungsi cek akun login
//        ***

        if (databaseHandler!!.userCheck(emailLogin.trim{ it <= ' '}, passLogin.trim{ it <= ' '})) {
            startActivity(Intent(this, MainActivity::class.java))
            emailLog.setText(null)
            passLog.setText(null)
            Toast.makeText(this, "Login berhasil", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this, "Login gagal", Toast.LENGTH_LONG).show()
        }
    }
}
