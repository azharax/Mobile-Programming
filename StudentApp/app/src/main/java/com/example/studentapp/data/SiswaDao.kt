package com.example.studentapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SiswaDao {
    @Insert
    suspend fun insertSiswa(siswa: Siswa)

    @Update
    suspend fun updateSiswa(siswa: Siswa)

    @Delete
    suspend fun deleteSiswa(siswa: Siswa)

    @Query("SELECT * FROM siswa ORDER BY id DESC")
    fun getAllSiswa(): Flow<List<Siswa>>
}