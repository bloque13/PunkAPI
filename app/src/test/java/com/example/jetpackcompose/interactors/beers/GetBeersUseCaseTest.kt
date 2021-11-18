package com.example.jetpackcompose.interactors.beers

import com.example.jetpackcompose.data.dto.Beer
import com.example.jetpackcompose.data.remote.MockWebServerResponses
import com.example.jetpackcompose.data.remote.PunkAPI
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetBeersUseCaseTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private lateinit var getBeersUseCase: GetBeersUseCase
    private lateinit var api: PunkAPI

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/v2/beers/")
        api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(PunkAPI::class.java)

        getBeersUseCase = GetBeersUseCase(api = api)
    }

    @Test
    fun getAllBeersFromNetwork(): Unit = runBlocking {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.beersResponse)
        )

        // run use case
        val beersAsFlow = getBeersUseCase.execute().toList()

        // first emission should be `loading`
        assert(beersAsFlow[0].loading)

        // second emission should be the beers
        val beers: List<Beer> = beersAsFlow[1].data as List<Beer>

        // check size, values etc
        assert(beers.size == 3)
        MatcherAssert.assertThat(beers[0], IsInstanceOf.instanceOf(Beer::class.java))

        val beer = beers[0]
        assertThat(beer.name).isEqualTo("Buzz")
        assertThat(beer.tagline).isEqualTo("A Real Bitter Experience.")
        assertThat(beer.first_brewed).isEqualTo("09/2007")
        assertThat(beer.description).isEqualTo("A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.")
        assertThat(beer.image_url).isEqualTo("https://images.punkapi.com/v2/keg.png")
        assertThat(beer.abv).isEqualTo(4.5)

        // 'loading' should be false now
        assert(!beersAsFlow[1].loading)
    }

    @Test
    fun getAllBeersFromNetworkShowError(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        )

        // run use case
        val beersAsFlow = getBeersUseCase.execute().toList()

        // first emission should be `loading`
        assert(beersAsFlow[0].loading)

        // error message displayed
        Assertions.assertTrue(beersAsFlow[1].error == "HTTP 500 Server Error")

        // 'loading' should be false now
        assert(!beersAsFlow[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

}