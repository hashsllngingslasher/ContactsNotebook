package com.example.contactsbook

import android.app.Application
import androidx.room.Room
import com.example.contactsbook.data.local.db.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "contacts"
        ).allowMainThreadQueries().build()
    }

    companion object {
        lateinit var db: AppDatabase
    }
}