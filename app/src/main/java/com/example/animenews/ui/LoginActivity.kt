package com.example.animenews.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.animenews.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            fnLogin()
        }
    }

    private fun fnLogin() {
        if ((binding.txtUser.text.toString() == "bayron") &&
            (binding.txtPassword.text.toString() == "torres")
        ) {
            Toast.makeText(this, "Ingreso correcto", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Ingreso fallido", Toast.LENGTH_LONG).show()
        }
    }

}