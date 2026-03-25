package com.example.basededonnees

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlanningSyntheseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning_synthese)

        val slot1 = intent.getStringExtra("SLOT1") ?: "Non renseigné"
        val slot2 = intent.getStringExtra("SLOT2") ?: "Non renseigné"
        val slot3 = intent.getStringExtra("SLOT3") ?: "Non renseigné"
        val slot4 = intent.getStringExtra("SLOT4") ?: "Non renseigné"

        val tvSlot1 = findViewById<TextView>(R.id.tvSlot1)
        val tvSlot2 = findViewById<TextView>(R.id.tvSlot2)
        val tvSlot3 = findViewById<TextView>(R.id.tvSlot3)
        val tvSlot4 = findViewById<TextView>(R.id.tvSlot4)
        val btnReturnHome = findViewById<Button>(R.id.btnReturnHome)

        tvSlot1.text = "08h - 10h : $slot1"
        tvSlot2.text = "10h - 12h : $slot2"
        tvSlot3.text = "14h - 16h : $slot3"
        tvSlot4.text = "16h - 18h : $slot4"

        btnReturnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}