package com.dany.dnyaneshscassignment.data.repo

import com.dany.dnyaneshscassignment.data.MockData
import com.dany.dnyaneshscassignment.data.model.Contact
import com.dany.dnyaneshscassignment.data.model.ResponseState
import com.dany.dnyaneshscassignment.data.source.ContactDataSource
import com.dany.dnyaneshscassignment.domain.repo.ContactRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response


class ContactRepositoryImplTest {

    // Mock the ContactDataSource
    @Mock
    private lateinit var mockDataSource: ContactDataSource

    // The class we are testing
    private lateinit var contactRepository: ContactRepository

    @Before
    fun setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this)
        // Create an instance of ContactRepositoryImpl with the mock DataSource
        contactRepository = ContactRepositoryImpl(mockDataSource)
    }

    @Test
    fun `getContacts returns success with data when dataSource is successful`() = runTest {

        val fakeContacts = MockData.mockContacts
        val successfulResponse: Response<List<Contact>> = Response.success(fakeContacts)

        // Stub the dataSource.getContacts() method to return the successfulResponse
        `when`(mockDataSource.getContacts()).thenReturn(successfulResponse)

        // 2. Act
        val resultFlow = contactRepository.getContacts()
        val resultState = resultFlow.first() // Collect the first emission

        // 3. Assert
        assertTrue(resultState is ResponseState.Success)
        assertEquals(fakeContacts, (resultState as ResponseState.Success).data)
    }

    @Test
    fun `getContacts returns success with empty list when dataSource is successful with null body`() =
        runTest {
            // 1. Arrange
            val successfulResponseWithNullBody: Response<List<Contact>> = Response.success(null)

            `when`(mockDataSource.getContacts()).thenReturn(successfulResponseWithNullBody)

            // 2. Act
            val resultFlow = contactRepository.getContacts()
            val resultState = resultFlow.first()

            // 3. Assert
            assertTrue(resultState is ResponseState.Success)
            assertTrue((resultState as ResponseState.Success).data!!.isEmpty())
        }

    @Test
    fun `getContacts returns success with empty list when dataSource is successful with empty list body`() =
        runTest {
            // 1. Arrange
            val emptyContactList = emptyList<Contact>()
            val successfulResponseWithEmptyList: Response<List<Contact>> =
                Response.success(emptyContactList)

            `when`(mockDataSource.getContacts()).thenReturn(successfulResponseWithEmptyList)

            // 2. Act
            val resultFlow = contactRepository.getContacts()
            val resultState = resultFlow.first()

            // 3. Assert
            assertTrue(resultState is ResponseState.Success)
            assertTrue((resultState as ResponseState.Success).data!!.isEmpty())
        }


    @Test
    fun `getContacts returns error when dataSource is unsuccessful`() = runTest {
        // 1. Arrange
        val errorCode = 500
        val errorMessage = "Internal Server Error"
        // Create an error Response from Retrofit
        val errorResponse: Response<List<Contact>> = Response.error(
            errorCode,
            "{\"error\":\"$errorMessage\"}".toResponseBody(null) // Mock error body
        )

        `when`(mockDataSource.getContacts()).thenReturn(errorResponse)

        // 2. Act
        val resultFlow = contactRepository.getContacts()
        val resultState = resultFlow.first()

        // 3. Assert
        assertTrue(resultState is ResponseState.Error)
        assertEquals("Error Fetching Data", (resultState as ResponseState.Error).message)
    }
}
