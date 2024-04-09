package com.example.contactsbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var photo: String?,
    val name: String,
    val phoneNumber: String
)
