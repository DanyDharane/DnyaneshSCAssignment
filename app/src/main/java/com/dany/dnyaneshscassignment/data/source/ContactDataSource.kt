package com.dany.dnyaneshscassignment.data.source

import com.dany.dnyaneshscassignment.data.model.Contact
import retrofit2.Response

interface ContactDataSource {

    suspend fun getContacts(): Response<List<Contact>>
}