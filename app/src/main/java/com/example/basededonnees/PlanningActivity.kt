package com.example.basededonnees

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PlanningActivity : AppCompatActivity() {

    private lateinit var db: MaBaseOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning)

        db = MaBaseOpenHelper(this)

        // Récupération de l'ID de l'utilisateur connecté
        val userId = intent.getLongExtra("USER_ID", -1L)

        val etSlot1 = findViewById<EditText>(R.id.etSlot1)
        val etSlot2 = findViewById<EditText>(R.id.etSlot2)
        val etSlot3 = findViewById<EditText>(R.id.etSlot3)
        val etSlot4 = findViewById<EditText>(R.id.etSlot4)
        val btnSave = findViewById<Button>(R.id.btnSavePlanning)

        btnSave.setOnClickListener {
            val slot1 = etSlot1.text.toString()
            val slot2 = etSlot2.text.toString()
            val slot3 = etSlot3.text.toString()
            val slot4 = etSlot4.text.toString()

            if (userId != -1L) {
                // Sauvegarde en base de données
                val planId = db.insertPlanning(userId, slot1, slot2, slot3, slot4)

                if (planId > -1) {
                    Toast.makeText(this, "Planning sauvegardé", Toast.LENGTH_SHORT).show()

                    // Passage à l'activité de synthèse en lui passant les données
                    val intent = Intent(this, PlanningSyntheseActivity::class.java)
                    intent.putExtra("SLOT1", slot1)
                    intent.putExtra("SLOT2", slot2)
                    intent.putExtra("SLOT3", slot3)
                    intent.putExtra("SLOT4", slot4)
                    startActivity(intent)
                    finish() // Ferme l'activité de saisie

                } else {
                    Toast.makeText(this, "Erreur lors de la sauvegarde en base", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Erreur: Utilisateur non identifié", Toast.LENGTH_SHORT).show()
            }
        }
    }
}