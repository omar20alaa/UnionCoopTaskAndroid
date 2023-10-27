package app.android_challenge.app.network

import app.android_challenge.util.AppConstants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Networking {
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient = OkHttpClient.Builder().addInterceptor(logging)

    private val gson = GsonBuilder()
        .setLenient()
        .create()

     val nyTimesService: Api.NYTimesService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient.build())
            .build()
            .create(Api.NYTimesService::class.java)
    }


}