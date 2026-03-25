package com.example.basededonnees

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home) // Layout avec 2 boutons

        val btnInscription = findViewById<Button>(R.id.btnHomeInscription)
        val btnConnexion = findViewById<Button>(R.id.btnHomeConnexion)

        btnInscription.setOnClickListener {
            // Lance la MainActivity qui gère les fragments d'inscription
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnConnexion.setOnClickListener {
            // Lance la page de connexion
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}