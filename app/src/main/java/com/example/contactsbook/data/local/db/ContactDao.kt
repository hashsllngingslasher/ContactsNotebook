package com.example.contactsbook.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.contactsbook.data.model.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAll(): List<Contact>

    @Insert
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Update
    fun update(contact: Contact)
}