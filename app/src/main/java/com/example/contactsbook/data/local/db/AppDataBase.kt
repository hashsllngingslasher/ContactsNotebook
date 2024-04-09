package com.example.contactsbook.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactsbook.data.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}