package com.example.basededonnees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Button

class FragmentAffichage : Fragment() {

    private lateinit var dbHelper: DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_affichage, container, false)
        dbHelper = DBHelper(requireContext())

        val tvSummary = view.findViewById<TextView>(R.id.tvSummary)

        // Récupération de l'ID passé par le Fragment 1
        val userId = arguments?.getLong("USER_ID") ?: -1L

        if (userId != -1L) {
            displayUserData(userId, tvSummary)
        }

        val btnBack = view.findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            // Cette commande dépile le fragment actuel et renvoie au formulaire
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }

    private fun displayUserData(id: Long, textView: TextView) {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.COLUMN_ID} = ?", arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            val login = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOGIN))
            val nom = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NOM))
            val prenom = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRENOM))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EMAIL))
            val interests = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INTERESTS))

            val summary = """
                ** SYNTHÈSE DE L'INSCRIPTION **
                
                Login : $login
                Nom : $nom
                Prénom : $prenom
                Email : $email
                Centres d'intérêt : $interests
                
                Données sauvegardées avec succès !
            """.trimIndent()

            textView.text = summary
        }
        cursor.close()
        db.close()
    }
}