package com.example.my_app.ui.service
import com.example.my_intern_project.models.Release
import com.example.my_intern_project.models.Media
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit API interface
interface ApiService {

    // Fetch the list of releases
    @GET("releases/")
    fun fetchReleases(
        @Query("apiKey") apiKey: String
    ): Call<List<Release>>

    // Fetch details of a particular title by ID
    @GET("title/{id}/details/")
    fun fetchDetail(
        @Path("id") id: String,
        @Query("apiKey") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "sources"
    ): Call<Media>
}

annotation class GET(val value: String)

// Retrofit client
object RetrofitClient {

    private const val BASE_URL = "https://api.watchmode.com/v1/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

// ApiService Implementation (Fetching data)
class ApiServiceImpl {

    private val apiKey = "tYdKCnbyxSvnUgBV3vGOlxhxpdjUDY9BK3HIR8sN"
    private val apiService = RetrofitClient.apiService

    // Fetch the list of releases
    fun fetchReleases(callback: (List<Release>?, String?) -> Unit) {
        apiService.fetchReleases(apiKey).enqueue(object : Callback<List<Release>> {
            override fun onResponse(call: Call<List<Release>>, response: Response<List<Release>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(it, null)
                    }
                } else {
                    callback(null, "Failed to load releases")
                }
            }

            override fun onFailure(call: Call<List<Release>>, t: Throwable) {
                callback(null, "Error: ${t.message}")
            }
        })
    }

    // Fetch media details
    fun fetchDetail(id: String, callback: (Media?, String?) -> Unit) {
        apiService.fetchDetail(id, apiKey).enqueue(object : Callback<Media> {
            override fun onResponse(call: Call<Media>, response: Response<Media>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(it, null)
                    }
                } else {
                    callback(null, "Failed to load details: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Media>, t: Throwable) {
                callback(null, "Error: ${t.message}")
            }
        })
    }
}
