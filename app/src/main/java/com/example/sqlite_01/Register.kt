package com.example.sqlite_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sqlite_01.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        backBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

//    ***
//    fungsi melakukan registrasi
//    ***

    fun register(view: View) {
        val nameReg = nameReg!!.text.toString()
        val emailReg = emailReg!!.text.toString()
        val passReg = passReg!!.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        startActivity(Intent(this, Login::class.java))
        finish()
    }
}
