package com.example.basededonnees

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var db: MaBaseOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = MaBaseOpenHelper(this)

        val etLogin = findViewById<EditText>(R.id.etLoginConnect)
        val etPassword = findViewById<EditText>(R.id.etPasswordConnect)
        val btnLogin = findViewById<Button>(R.id.btnLoginSubmit)

        btnLogin.setOnClickListener {
            val login = etLogin.text.toString().trim()
            val pass = etPassword.text.toString()

            val userId = db.checkUserCredentials(login, pass)

            if (userId != -1L) {
                Toast.makeText(this, "Connexion réussie !", Toast.LENGTH_SHORT).show()
                // On passe à l'activité Planning en lui donnant l'ID de l'utilisateur
                val intent = Intent(this, PlanningActivity::class.java)
                intent.putExtra("USER_ID", userId)
                startActivity(intent)
                finish() // Ferme la page de login
            } else {
                Toast.makeText(this, "Login ou mot de passe incorrect", Toast.LENGTH_LONG).show()
            }
        }
    }
}