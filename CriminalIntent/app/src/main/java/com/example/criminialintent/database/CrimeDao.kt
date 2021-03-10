package com.example.criminialintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.criminialintent.Crime
import java.util.*


@Dao
interface CrimeDao {

    @Query("SELECT * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>
//    fun getCrimes():List<Crime>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime?>
//    fun getCrime(id: UUID):Crime?

    @Update
    fun updateCrime(crime: Crime)

    @Insert
    fun addCrime(crime: Crime)

}