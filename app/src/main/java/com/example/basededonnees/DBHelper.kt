package com.example.basededonnees

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase.db"
        const val TABLE_USERS = "users"

        const val COLUMN_ID = "id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NOM = "nom"
        const val COLUMN_PRENOM = "prenom"
        const val COLUMN_DOB = "dob"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_INTERESTS = "interests"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_LOGIN TEXT,"
                + "$COLUMN_PASSWORD TEXT,"
                + "$COLUMN_NOM TEXT,"
                + "$COLUMN_PRENOM TEXT,"
                + "$COLUMN_DOB TEXT,"
                + "$COLUMN_PHONE TEXT,"
                + "$COLUMN_EMAIL TEXT,"
                + "$COLUMN_INTERESTS TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertUser(login: String, pass: String, nom: String, prenom: String, dob: String, phone: String, email: String, interests: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LOGIN, login)
            put(COLUMN_PASSWORD, pass)
            put(COLUMN_NOM, nom)
            put(COLUMN_PRENOM, prenom)
            put(COLUMN_DOB, dob)
            put(COLUMN_PHONE, phone)
            put(COLUMN_EMAIL, email)
            put(COLUMN_INTERESTS, interests)
        }
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }
}