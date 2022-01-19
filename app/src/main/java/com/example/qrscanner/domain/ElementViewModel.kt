package com.example.qrscanner.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrscanner.di.ElementInteractorImpl
import com.example.qrscanner.storage.ElementEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class ElementViewModel @Inject constructor(
    private val roomRepository: ElementInteractorImpl
) : ViewModel() {

    private val _element = MutableLiveData<List<ElementEntity>>()
    val element: LiveData<List<ElementEntity>> get() = _element

    fun getAll() {
        viewModelScope.launch {
            _element.value = roomRepository.getAll()
        }
    }
}