package com.dany.dnyaneshscassignment.data.repo

import com.dany.dnyaneshscassignment.data.model.Contact
import com.dany.dnyaneshscassignment.data.model.ResponseState
import com.dany.dnyaneshscassignment.data.source.ContactDataSource
import com.dany.dnyaneshscassignment.domain.repo.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
        private val dataSource: ContactDataSource
    ) : ContactRepository {
    override suspend fun getContacts(): Flow<ResponseState<List<Contact>>> {
        val response = dataSource.getContacts()
        return if (response.isSuccessful) {
            flowOf(ResponseState.Success( response.body() ?: emptyList()))
        } else {
            flowOf(ResponseState.Error( "Error Fetching Data"))
        }
    }
}