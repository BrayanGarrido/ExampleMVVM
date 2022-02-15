package com.bornidea.room

import com.bornidea.room.db.Subscriber
import com.bornidea.room.db.SubscriberDao

class SuscriberRepository(private val dao: SubscriberDao) {

    /**Se ejectará en el hilo principal*/
    val subscribers = dao.getAllSubscribers()

    /**Funciones que se ejecutarán con corrutinas*/
    suspend fun insert(subscriber: Subscriber): Long = dao.insertSubscriber(subscriber)


    suspend fun update(subscriber: Subscriber): Int = dao.updateSubscriber(subscriber)


    suspend fun delete(subscriber: Subscriber): Int = dao.deleteSubscriber(subscriber)


    suspend fun deleteAll(): Int = dao.deleteAll()

}