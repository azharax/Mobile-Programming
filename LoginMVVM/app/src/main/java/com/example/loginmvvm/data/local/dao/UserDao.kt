package com.example.loginmvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loginmvvm.data.local.entity.User
@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query(
        "SELECT * FROM users WHERE username = :username AND password =:password"
    )
    suspend fun login(
        username: String,
        password: String
    ): User?
}