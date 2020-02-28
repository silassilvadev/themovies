package com.silas.themovies.data.remote.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Startup class of servers that will be used throughout the app.
 *
 * @property API_KEY API key for certain service calls
 * @author silas.silva in 23/02/2020
 * */

class ClientService {

    companion object {
        const val API_KEY = "0d8f3abc66280bdb43792ed1b2719409"

        internal inline fun <reified T> createNewService(url: String): T {
            return ClientService().setUpService(url)
        }
    }

    /**
     * Method where services are configured
     *
     * @param T type that specifies the service to be configured
     * @param url url base for service flames
     * @return return of requested service type already configured
     * */
    internal inline fun <reified T> setUpService(url: String): T {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(setUpClientHttp())
            .addConverterFactory(configureFactory())
            .build()
            .create(T::class.java)
    }

    /**
     * Here is configured the http client as well as an interceptor for debug logging
     *
     * @return returns client of type OkHttpClient
     * */
    internal fun setUpClientHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    /**
     * In this method the class responsible for factoring the JSONs received
     * by the server is configured.
     *
     * @return returns GsonConverterFactory converter instance
     * */
    private fun configureFactory(): GsonConverterFactory {
        val factoryBuilder = GsonBuilder().create()
        return GsonConverterFactory.create(factoryBuilder)
    }
}