package com.example.basededonnees

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MaBaseOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "UserDatabase.db"

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NOM = "nom"
        const val COLUMN_PRENOM = "prenom"
        const val COLUMN_DATE = "date"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_INTERETS = "interets"

        const val TABLE_PLANNING = "planning"
        const val COLUMN_PLAN_ID = "id_plan"
        const val COLUMN_USER_ID = "id_user"
        const val COLUMN_SLOT1 = "h08_10"
        const val COLUMN_SLOT2 = "h10_12"
        const val COLUMN_SLOT3 = "h14_16"
        const val COLUMN_SLOT4 = "h16_18"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUsers = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_LOGIN TEXT,"
                + "$COLUMN_PASSWORD TEXT,"
                + "$COLUMN_NOM TEXT,"
                + "$COLUMN_PRENOM TEXT,"
                + "$COLUMN_DATE TEXT,"
                + "$COLUMN_PHONE TEXT,"
                + "$COLUMN_EMAIL TEXT,"
                + "$COLUMN_INTERETS TEXT)")
        db.execSQL(createTableUsers)

        val createTablePlanning = ("CREATE TABLE $TABLE_PLANNING ("
                + "$COLUMN_PLAN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USER_ID INTEGER,"
                + "$COLUMN_SLOT1 TEXT,"
                + "$COLUMN_SLOT2 TEXT,"
                + "$COLUMN_SLOT3 TEXT,"
                + "$COLUMN_SLOT4 TEXT)")
        db.execSQL(createTablePlanning)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PLANNING")
        onCreate(db)
    }

    fun insertUser(login: String, pass: String, nom: String, prenom: String, date: String, phone: String, email: String, interets: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LOGIN, login)
            put(COLUMN_PASSWORD, pass)
            put(COLUMN_NOM, nom)
            put(COLUMN_PRENOM, prenom)
            put(COLUMN_DATE, date)
            put(COLUMN_PHONE, phone)
            put(COLUMN_EMAIL, email)
            put(COLUMN_INTERETS, interets)
        }
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    fun checkUserExists(login: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COLUMN_LOGIN = ?", arrayOf(login))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun checkUserCredentials(login: String, pass: String): Long {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_ID FROM $TABLE_USERS WHERE $COLUMN_LOGIN = ? AND $COLUMN_PASSWORD = ?", arrayOf(login, pass))
        var id = -1L
        if (cursor.moveToFirst()) {
            id = cursor.getLong(0)
        }
        cursor.close()
        return id
    }

    fun insertPlanning(userId: Long, slot1: String, slot2: String, slot3: String, slot4: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, userId)
            put(COLUMN_SLOT1, slot1)
            put(COLUMN_SLOT2, slot2)
            put(COLUMN_SLOT3, slot3)
            put(COLUMN_SLOT4, slot4)
        }
        val id = db.insert(TABLE_PLANNING, null, values)
        db.close()
        return id
    }
}