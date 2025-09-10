package com.dany.dnyaneshscassignment.domain.model

import com.dany.dnyaneshscassignment.data.model.Contact

data class ContactState(val isLoading: Boolean = false, val contactList: List<Contact> = emptyList(), val error: String? = null)