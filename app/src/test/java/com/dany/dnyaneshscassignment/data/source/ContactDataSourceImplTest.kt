
import com.dany.dnyaneshscassignment.data.MockData
import com.dany.dnyaneshscassignment.data.api.ApiService
import com.dany.dnyaneshscassignment.data.model.Contact
import com.dany.dnyaneshscassignment.data.source.ContactDataSourceImpl
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class ContactDataSourceImplTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var contactDataSource: ContactDataSourceImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        contactDataSource = ContactDataSourceImpl(mockApiService)
    }

    @Test
    fun `getContacts returns success response`() = runBlocking {
        // Given
        val mockContacts = MockData.mockContacts
        val successResponse: Response<List<Contact>> = Response.success(mockContacts)
        `when`(mockApiService.getContacts()).thenReturn(successResponse)

        // When
        val result = contactDataSource.getContacts()

        // Then
        assertTrue(result.isSuccessful)
        assertEquals(mockContacts, result.body())
    }

    @Test
    fun `getContacts returns error response`() = runBlocking {
        // Given
        val errorResponseBody = "{\"error\":\"An error occurred\"}"
            .toResponseBody("application/json".toMediaTypeOrNull())
        val errorResponse: Response<List<Contact>> = Response.error(400, errorResponseBody)
        `when`(mockApiService.getContacts()).thenReturn(errorResponse)

        // When
        val result = contactDataSource.getContacts()

        // Then
        assertTrue(!result.isSuccessful)
        assertEquals(400, result.code())
        assertEquals(errorResponseBody, result.errorBody())
    }

    @Test
    fun `getContacts handles exception from ApiService`() = runBlocking {
        // Given
        val exception = RuntimeException("Network error")
        `when`(mockApiService.getContacts()).thenThrow(exception)

        // When & Then
        var caughtException: Exception? = null
        try {
            contactDataSource.getContacts()
        } catch (e: Exception) {
            caughtException = e
        }
        assertEquals(exception, caughtException)
    }
}
