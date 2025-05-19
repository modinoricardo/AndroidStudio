package com.example.proyectofinalricardomitienda.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinalricardomitienda.databinding.ActivityMainBinding
import android.widget.Toast
import com.example.proyectofinalricardomitienda.model.LoginViewModel
import com.example.proyectofinalricardomitienda.R
import com.example.proyectofinalricardomitienda.util.Util


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            loginViewModel.code.observe(this@MainActivity) {
                Log.i("accessToken", loginViewModel.code.value!!.toString())
                if (loginViewModel.code.value == 200) {
                    val myIntent = Intent(this@MainActivity, PaginaPrincipal::class.java)

                    Util.username = editTextTextUser.text.toString()
                    Util.password = editTextTextPassword.text.toString()

                    startActivity(myIntent)
                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Usuario o contraseña incorrectos",
                        Toast.LENGTH_SHORT
                    ).show()
                    editTextTextUser.setText("")
                    editTextTextPassword.setText("")
                }
            }

            buttonStarSesion.setOnClickListener {
                val user = editTextTextUser.text.toString()
                val password = editTextTextPassword.text.toString()

                loginViewModel.login(user,password)
            }
        }
    }
}