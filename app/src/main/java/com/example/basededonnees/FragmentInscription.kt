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

    private lateinit var db: MaBaseOpenHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inscription, container, false)

        db = MaBaseOpenHelper(requireContext())

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
            val login = etLogin.text.toString().trim()
            val pass = etPassword.text.toString()

            if (!login.matches(Regex("^[a-zA-Z].*"))) {
                Toast.makeText(requireContext(), "Le login doit commencer par une lettre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (login.length > 10) {
                Toast.makeText(requireContext(), "Le login ne doit pas dépasser 10 caractères", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass.length < 6) {
                Toast.makeText(requireContext(), "Le mot de passe doit faire au moins 6 caractères", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (db.checkUserExists(login)) {
                Toast.makeText(requireContext(), "Ce login est déjà utilisé", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val interests = mutableListOf<String>()
            if (cbSport.isChecked) interests.add("Sport")
            if (cbMusic.isChecked) interests.add("Musique")
            if (cbLecture.isChecked) interests.add("Lecture")
            val interestsString = interests.joinToString(", ")

            val insertedId = db.insertUser(login, pass, etNom.text.toString(), etPrenom.text.toString(), etDob.text.toString(), etPhone.text.toString(), etEmail.text.toString(), interestsString)

            if (insertedId > -1) {
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