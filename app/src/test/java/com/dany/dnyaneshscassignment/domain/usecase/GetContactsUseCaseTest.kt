package com.dany.dnyaneshscassignment.domain.usecase

import com.dany.dnyaneshscassignment.data.MockData
import com.dany.dnyaneshscassignment.data.model.Contact
import com.dany.dnyaneshscassignment.data.model.ResponseState
import com.dany.dnyaneshscassignment.domain.repo.ContactRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetContactsUseCaseTest {

    @Mock
    private lateinit var mockRepository: ContactRepository

    private lateinit var getContactsUseCase: GetContactsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getContactsUseCase = GetContactsUseCase(mockRepository)
    }

    @Test
    fun `invoke WHEN repository returns Success THEN emit ContactState with contactList`() = runTest {
        // Given
        val mockContacts = MockData.mockContacts
        val successResponse = ResponseState.Success(mockContacts)
        `when`(mockRepository.getContacts()).thenReturn(flowOf(successResponse))

        // When
        val result = getContactsUseCase.invoke().first()

        // Then
        assertEquals(mockContacts, result.contactList)
        assertNull(result.error)
    }

    @Test
    fun `invoke WHEN repository returns Success with null data THEN emit ContactState with emptyList`() = runTest {
        // Given
        val successResponse = ResponseState.Success<List<Contact>>(null)
        `when`(mockRepository.getContacts()).thenReturn(flowOf(successResponse))

        // When
        val result = getContactsUseCase.invoke().first()

        // Then
        assertTrue(result.contactList.isEmpty())
        assertNull(result.error)
    }

    @Test
    fun `invoke WHEN repository returns Error THEN emit ContactState with error message`() = runTest {
        // Given
        val errorMessage = "Network error"
        val errorResponse = ResponseState.Error<List<Contact>>(errorMessage)
        `when`(mockRepository.getContacts()).thenReturn(flowOf(errorResponse))

        // When
        val result = getContactsUseCase.invoke().first()

        // Then
        assertTrue(result.contactList.isEmpty())
        assertEquals(errorMessage, result.error)
    }

    @Test
    fun `invoke WHEN repository returns Loading THEN emit ContactState with current data (or emptyList)`() = runTest {
        // Given
        val loadingResponse = ResponseState.Loading<List<Contact>>()
        `when`(mockRepository.getContacts()).thenReturn(flowOf(loadingResponse))

        // When
        val result = getContactsUseCase.invoke().first()

        // Then
        assertEquals(emptyList<Contact>(), result.contactList) // Or assertTrue(result.contactList.isEmpty()) if loading with null
        assertNull(result.error) // Error should be null during loading
    }

    @Test
    fun `invoke WHEN repository returns Loading with null data THEN emit ContactState with emptyList`() = runTest {
        // Given
        val loadingResponse = ResponseState.Loading<List<Contact>>()
        `when`(mockRepository.getContacts()).thenReturn(flowOf(loadingResponse))

        // When
        val result = getContactsUseCase.invoke().first()

        // Then
        assertTrue(result.contactList.isEmpty())
        assertNull(result.error)
    }
}
