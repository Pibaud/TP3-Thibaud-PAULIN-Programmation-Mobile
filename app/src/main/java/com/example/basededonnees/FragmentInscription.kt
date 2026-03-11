package com.example.basededonnees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentInscription : Fragment() {

    private lateinit var dbHelper: DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inscription, container, false)

        dbHelper = DBHelper(requireContext())

        val etLogin = view.findViewById<EditText>(R.id.etLogin)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etNom = view.findViewById<EditText>(R.id.etNom)
        val etPrenom = view.findViewById<EditText>(R.id.etPrenom)
        val etDob = view.findViewById<EditText>(R.id.etDob)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)

        val cbSport = view.findViewById<CheckBox>(R.id.cbSport)
        val cbMusic = view.findViewById<CheckBox>(R.id.cbMusic)
        val cbLecture = view.findViewById<CheckBox>(R.id.cbLecture)

        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            // 1. Récupération des centres d'intérêt
            val interests = mutableListOf<String>()
            if (cbSport.isChecked) interests.add("Sport")
            if (cbMusic.isChecked) interests.add("Musique")
            if (cbLecture.isChecked) interests.add("Lecture")
            val interestsString = interests.joinToString(", ")

            // 2. Sauvegarde dans la base de données
            val insertedId = dbHelper.insertUser(
                etLogin.text.toString(),
                etPassword.text.toString(),
                etNom.text.toString(),
                etPrenom.text.toString(),
                etDob.text.toString(),
                etPhone.text.toString(),
                etEmail.text.toString(),
                interestsString
            )

            if (insertedId > -1) {
                // 3. Passage au Fragment 2 avec l'ID de l'utilisateur
                val fragment2 = FragmentAffichage()
                val bundle = Bundle()
                bundle.putLong("USER_ID", insertedId)
                fragment2.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment2)
                    .addToBackStack(null) // Permet de revenir en arrière avec le bouton retour
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}