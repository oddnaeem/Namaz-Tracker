package com.prospectbook.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanyDao {
    @Query("SELECT * FROM companies ORDER BY createdAt DESC")
    fun getAll(): Flow<List<Company>>

    @Insert
    suspend fun insert(company: Company): Long

    @Update
    suspend fun update(company: Company)

    @Delete
    suspend fun delete(company: Company)
}
