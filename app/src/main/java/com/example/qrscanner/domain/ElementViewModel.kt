package com.example.qrscanner.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ElementViewModel @Inject constructor(
    private val roomRepository: ElementInteractor
) : ViewModel() {

    private val _element = MutableLiveData<List<ElementQR>>()
    val element: LiveData<List<ElementQR>> get() = _element

    fun getAll() {
        viewModelScope.launch {
            _element.value = roomRepository.getAll()
        }
    }

    fun inset(element: ElementQR) {
        viewModelScope.launch {
            roomRepository.insert(element)
        }
    }

    fun delete(element: ElementQR) {
        viewModelScope.launch {
            roomRepository.delete(element)
        }
    }
}