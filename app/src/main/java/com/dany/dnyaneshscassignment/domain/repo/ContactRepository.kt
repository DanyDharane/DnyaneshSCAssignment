package com.dany.dnyaneshscassignment.domain.repo

import com.dany.dnyaneshscassignment.data.model.Contact
import com.dany.dnyaneshscassignment.data.model.ResponseState
import com.dany.dnyaneshscassignment.domain.model.ContactState
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun getContacts(): Flow<ResponseState<List<Contact>>>
}