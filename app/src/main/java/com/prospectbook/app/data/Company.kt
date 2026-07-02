package com.prospectbook.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class Status(val label: String) {
    WATCHING("নজরে আছে"),
    CONTACTED("যোগাযোগ হয়েছে"),
    IN_TALKS("আলোচনা চলছে"),
    CLOSED("সম্পন্ন")
}

@Entity(tableName = "companies")
data class Company(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val company: String,
    val problem: String = "",
    val contactName: String = "",
    val phone: String = "",
    val email: String = "",
    val status: Status = Status.WATCHING,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
