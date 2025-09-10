package com.dany.dnyaneshscassignment.domain.usecase

import com.dany.dnyaneshscassignment.data.model.ResponseState
import com.dany.dnyaneshscassignment.domain.model.ContactState
import com.dany.dnyaneshscassignment.domain.repo.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(private val repository: ContactRepository)  {

    operator fun invoke(): Flow<ContactState> = flow {
          repository.getContacts().collect { response ->
              when (response) {
                  is ResponseState.Success -> {
                      emit(ContactState(contactList = response.data ?: emptyList()))
                  }

                  is ResponseState.Error -> {
                      emit(ContactState(contactList = emptyList(), error = response.message))
                  }

                  is ResponseState.Loading -> {
                      emit(ContactState(contactList = response.data ?: emptyList()))
                  }

              }
          }}

}