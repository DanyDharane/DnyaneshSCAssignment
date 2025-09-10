package com.dany.dnyaneshscassignment.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dany.dnyaneshscassignment.data.model.Contact
import com.dany.dnyaneshscassignment.domain.usecase.GetContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase
) : ViewModel() {

    private val _contactList = mutableStateListOf<Contact>()
    var errorMessage: String by mutableStateOf("")
    val contactList: List<Contact>
        get() = _contactList

    fun getContacts() {
        viewModelScope.launch {
            try {
                val response = getContactsUseCase.invoke()
                response.collect {
                    _contactList.clear()
                    _contactList.addAll(it.contactList)
                }


            } catch (e: Exception) {
                // Handle error
            }
        }
    }

}