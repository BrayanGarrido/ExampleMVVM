package com.bornidea.room.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDao {

    /**Retorna el id*/
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber): Long

    /**Numero de filas actualizadas*/
    @Update
    suspend fun updateSubscriber(subscriber: Subscriber): Int

    /**Retorna el nmero de filas eliminadas de la base de datos*/
    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber): Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): LiveData<List<Subscriber>>
}