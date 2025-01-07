package com.kitepea.currency_converter

import com.kitepea.currency_converter.helper.Resource
import com.kitepea.currency_converter.model.ApiResponse
import com.kitepea.currency_converter.model.Rates
import com.kitepea.currency_converter.network.ApiDataSource
import com.kitepea.currency_converter.network.ApiService
import com.kitepea.currency_converter.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response
import java.nio.file.Files
import java.nio.file.Path

@ExperimentalCoroutinesApi
class MainRepositoryTest {

    private lateinit var apiDataSource: ApiDataSource
    private lateinit var repository: MainRepository
    private lateinit var apiService: ApiService
    private lateinit var tempDir: Path

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java)
        apiDataSource = ApiDataSource(apiService)
        repository = MainRepository(apiDataSource)

        tempDir = Files.createTempDirectory("testCache")
    }

    @After
    fun tearDown() {
        Files.deleteIfExists(tempDir)
    }

    @Test
    fun `convertCurrency should return conversion result`(): Unit = runTest {
        val accessKey = "0bfd637792920e7267c3dbb735c431c0ade8f450"
        val from = "USD"
        val to = "EUR"
        val amount = 100.0
        val apiResponse = ApiResponse(
            base_currency_code = from,
            base_currency_name = "United States Dollar",
            amount = amount.toString(),
            updated_date = "2025-01-07",
            rates = hashMapOf(
                to to Rates(
                    currency_name = "Euro",
                    rate = "0.9647",
                    rate_for_amount = 96.4664
                )
            ),
            status = "success"
        )
        `when`(
            apiService.convertCurrencyCall(
                accessKey,
                from,
                to,
                amount
            )
        ).thenReturn(Response.success(apiResponse))

        val flow = repository.getConvertedData(accessKey, from, to, amount)
        flow.collect { resource ->
            assertNotNull(resource)
            when (resource.status) {
                Resource.Status.SUCCESS -> assertEquals(apiResponse, resource.data)
                Resource.Status.ERROR -> fail("Expected Success but got Error: ${resource.message}")
                Resource.Status.LOADING -> {}
            }
        }
    }
}