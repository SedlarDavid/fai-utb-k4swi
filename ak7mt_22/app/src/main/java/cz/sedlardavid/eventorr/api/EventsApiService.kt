package cz.sedlardavid.eventorr.api

import cz.sedlardavid.eventorr.Event
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val CLIENT_ID =
    "MjkxNjgyMTR8MTY2MzM5NjgwMi4zNTU1NzEz"
private const val BASE_URL =
    "https://api.seatgeek.com/2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface EventsApiService {
    @GET("events?client_id=$CLIENT_ID")
    suspend fun getEvents():
            Event
}

object EventsApi {
    val retrofitService: EventsApiService by lazy {
        retrofit.create(EventsApiService::class.java)
    }
}