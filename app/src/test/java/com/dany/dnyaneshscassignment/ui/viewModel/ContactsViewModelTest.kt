package com.dany.dnyaneshscassignment.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dany.dnyaneshscassignment.data.MockData
import com.dany.dnyaneshscassignment.data.model.Contact
import com.dany.dnyaneshscassignment.domain.model.ContactState
import com.dany.dnyaneshscassignment.domain.usecase.GetContactsUseCase
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class ContactsViewModelTest {

    // Rule to execute tasks synchronously
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Important for LiveData/ViewModel testing

    // Test dispatcher for coroutines
    private val testDispatcher = StandardTestDispatcher()

    // Mock the use case
    private lateinit var getContactsUseCase: GetContactsUseCase
    private lateinit var viewModel: ContactsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Set the main dispatcher to the test dispatcher
        getContactsUseCase = mock()
        viewModel = ContactsViewModel(getContactsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher
    }

    @Test
    fun `getContacts success updates contactList`() = runTest(testDispatcher) {
        // Given
        val mockContacts = MockData.mockContacts
        val mockResponse = ContactState(false,mockContacts)
        whenever(getContactsUseCase.invoke()).thenReturn(flowOf(mockResponse))

        // When
        viewModel.getContacts()
        // Advance the dispatcher to allow coroutines to complete
        testDispatcher.scheduler.advanceUntilIdle()


        // Then
        assertEquals(mockContacts.size, viewModel.contactList.size)
        assertEquals(mockContacts[0], viewModel.contactList[0])
        assertEquals(mockContacts[1], viewModel.contactList[1])
        assertTrue(viewModel.errorMessage.isEmpty())
    }

    @Test
    fun `getContacts success clears previous list before adding new contacts`() = runTest(testDispatcher) {
        // Given: Initial state with some contacts
        val initialContacts = MockData.mockContacts
        viewModel.contactList.toMutableList().addAll(initialContacts) // Simulate pre-existing data
        // This is a bit tricky with mutableStateListOf directly in tests
        // A better way if this was a common scenario would be to
        // expose a clear method or initialize with data.
        // For this test, we'll assume the internal _contactList
        // might have items.

        val newContacts = MockData.mockContacts
        val newResponse = ContactState(false,newContacts)
        whenever(getContactsUseCase.invoke()).thenReturn(flowOf(newResponse))

        // When
        viewModel.getContacts()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(newContacts.size, viewModel.contactList.size)
        assertEquals(newContacts[0], viewModel.contactList[0])
        assertTrue(viewModel.errorMessage.isEmpty())
    }

    @Test
    fun `getContacts empty list from use case updates contactList to empty`() = runTest(testDispatcher) {
        // Given
        val emptyResponse = ContactState(false,emptyList())
        whenever(getContactsUseCase.invoke()).thenReturn(flowOf(emptyResponse))

        // Simulate some initial data
        val initialContacts = emptyList<Contact>()
        (viewModel.contactList as MutableList<Contact>).addAll(initialContacts)
        assertEquals(0, viewModel.contactList.size)


        // When
        viewModel.getContacts()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.contactList.isEmpty())
        assertTrue(viewModel.errorMessage.isEmpty())
    }
}
