package cz.sedlardavid.eventorr.api

import com.google.gson.GsonBuilder
import cz.sedlardavid.eventorr.entities.EventsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val CLIENT_ID = "MjkxNjgyMTR8MTY2MzM5NjgwMi4zNTU1NzEz"
private const val BASE_URL = "https://api.seatgeek.com/2/"

interface EventsApi {
    @GET("events?client_id=$CLIENT_ID")
    suspend fun getEvents(): Response<EventsResponse>
}

object RetrofitHelper {
/*    val retrofitService: EventsApiService by lazy {
        retrofit.create(EventsApiService::class.java)
    }*/

    fun getInstance(): Retrofit {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}