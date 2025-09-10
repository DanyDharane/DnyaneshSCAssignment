package com.dany.dnyaneshscassignment.data.api

import com.dany.dnyaneshscassignment.data.model.Contact
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getContacts(): Response<List<Contact>>

}