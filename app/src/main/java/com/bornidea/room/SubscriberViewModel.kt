package com.bornidea.room

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.bornidea.room.db.Subscriber
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class SubscriberViewModel(private val repository: SuscriberRepository) : ViewModel(), Observable {

    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    /**Estas serán las vistas de los editext*/
    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllButtonText = MutableLiveData<String>()

    /**Para obtener los suscriptores solo se necesita obtener la informacion del repositorio*/
    val subscribers = repository.subscribers

    /**Al cambiar de estado el viewmodel debe de informar sobre el cambio con un mensaje*/
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = statusMessage

    /**Valores por default del texto de los botones*/
    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllButtonText.value = "Clear All"
    }

    /**Funciones relacionadas con los botones*/
    fun saveOrUpdate() {
        /**Verificar que exista informacion en los inputs*/
        if (inputName.value == null) {
            statusMessage.value = Event("Please enter subscriber's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter subscriber's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            /**VALIDAR EL FORMATO DEL CORREO*/
            statusMessage.value = Event("Please enter correct email address")
        } else {
            if (isUpdateOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insert(Subscriber(0, name, email))
                inputEmail.value = null
                inputName.value = null
            }
        }

    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    /**Funciones que interactuan con el repositorio*/
    fun insert(subscriber: Subscriber) = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if (newRowId > -1) {
            statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Ocurred")
        }
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch {
        val numOfRow = repository.update(subscriber)
        if (numOfRow > 0) {
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllButtonText.value = "Clear All"
            statusMessage.value = Event("$numOfRow Subscriber Updated Successfully")
        } else {
            statusMessage.value = Event("Error Ocurred")
        }

    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch {
        val numOfRowsDeleted = repository.delete(subscriber)
        if (numOfRowsDeleted > 0) {
            repository.delete(subscriber)
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllButtonText.value = "Clear All"
            statusMessage.value = Event("$numOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Ocurred")
        }

    }

    fun clearAll() = viewModelScope.launch {
        val numOfRowsDeleted = repository.deleteAll()
        if (numOfRowsDeleted > 0) {
            statusMessage.value = Event("$numOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Ocurred")
        }
    }

    /**Fnucion dentro del recyclerView*/
    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}

class SubscriberViewModelFactory(private val repository: SuscriberRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}