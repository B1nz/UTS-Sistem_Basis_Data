package com.example.sqlite_01.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlite_01.`object`.EmpModelClass
import com.example.sqlite_01.`object`.EmpModelUser

class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "EmployeeDatabase"
        private val TABLE_CONTACTS = "EmployeeTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"

//        ***
//        Untuk table user login
//        ***

        private val USER_TABLE = "user"
        private val USER_ID = "id"
        private val USER_NAME = "name"
        private val USER_EMAIL = "email"
        private val USER_PASSWORD = "password"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // membuat tabel beserta definisi kolomnya
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")")

        db?.execSQL(CREATE_CONTACTS_TABLE)

//        ***
//        fungsi untuk membuat table user
//        ***

        val CREATE_USERS_TABLE = ("CREATE TABLE " + USER_TABLE + "("
                + USER_ID + " INTEGER PRIMARY KEY," + USER_NAME + " VARCHAR(255),"
                + USER_EMAIL + " VARCHAR(255)," + USER_PASSWORD + " VARCHAR(255)" + ")")

        db?.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        db!!.execSQL("DROP TABLE IF EXISTS " + "user")
        onCreate(db)
    }

//    ***
//    fungsi untuk melakukan register
//    ***

    fun insertUserData(emp: EmpModelUser):Long {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put("nama", emp.nama)
        values.put("email", emp.email)
        values.put("password", emp.password)

        val tambah = db.insert("user", null, values)
        db.close()
        return tambah
    }

    fun userCheck(email: String, password: String):Boolean {
        val db = readableDatabase
        val columns = arrayOf(USER_NAME)
        val selection = "$USER_EMAIL = ? AND $USER_NAME = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null)

        if(cursor.count >0) {
            return true
        }
        return false
        cursor.close()
        db.close()
    }

    // fungsi untuk menambahkan data
    fun addEmployee(emp: EmpModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName)
        contentValues.put(KEY_EMAIL,emp.userEmail )
        // menambahkan data pada tabel
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    // fungsi untuk menampilkan data dari tabel ke UI
    fun viewEmployee():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val emp= EmpModelClass(userId = userId, userName = userName, userEmail = userEmail)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    // fungsi untuk memperbarui data pegawai
    fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName)
        contentValues.put(KEY_EMAIL,emp.userEmail )

        // memperbarui data
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.userId,null)

        // menutup koneksi ke database
        db.close()
        return success
    }
    // fungsi untuk menghapus data
    fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        // employee id dari data yang akan dihapus
        contentValues.put(KEY_ID, emp.userId)
        // query untuk menghapus ata
        val success = db.delete(TABLE_CONTACTS,"id="+emp.userId,null)

        // menutup koneksi ke database
        db.close()
        return success
    }
}