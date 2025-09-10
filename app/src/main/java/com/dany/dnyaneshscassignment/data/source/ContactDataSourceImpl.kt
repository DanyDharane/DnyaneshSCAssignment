package com.dany.dnyaneshscassignment.data.source

import com.dany.dnyaneshscassignment.data.api.ApiService
import com.dany.dnyaneshscassignment.data.model.Contact
import retrofit2.Response
import javax.inject.Inject

class ContactDataSourceImpl @Inject constructor( val apiService: ApiService) : ContactDataSource {

    override suspend fun getContacts(): Response<List<Contact>> {
        return apiService.getContacts()
    }


}