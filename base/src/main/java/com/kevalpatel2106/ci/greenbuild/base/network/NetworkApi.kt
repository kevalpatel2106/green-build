package com.kevalpatel2106.ci.greenbuild.base.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kevalpatel2106.ci.greenbuild.base.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
/**
 * Created by Keval Patel on 10/09/17.
 * This class deals with the API and network calls using the RxJava and retrofit.
 *
 * @author 'https://github.com/kevalpatel2106'
 */
class NetworkApi(private val token: String? = null) {

    /**
     * OkHttp instance. New instances will be shallow copy of this instance.
     *
     * @see .getOkHttpClientBuilder
     */
    internal val okHttpClient: OkHttpClient

    /**
     * Gson instance with custom gson deserializers.
     */
    private val sGson: Gson = GsonBuilder()
            .setLenient()
            .create()

    internal fun prepareOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
                .readTimeout(NetworkConfig.READ_TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(NetworkConfig.WRITE_TIMEOUT, TimeUnit.MINUTES)
                .connectTimeout(NetworkConfig.CONNECTION_TIMEOUT, TimeUnit.MINUTES)

        //Add debug interceptors
        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
            )
        }

        return httpClientBuilder
                .addInterceptor(NWInterceptor(token))  /* Add the interceptor. */
                .build()
    }

    init {
        okHttpClient = prepareOkHttpClient()
    }

    /**
     * Get the retrofit client instance for given base URL.
     *
     * @param baseUrl Base url of the api.
     */
    fun getRetrofitClient(baseUrl: String): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(NWResponseConverter.create(sGson))
            .build()
}
