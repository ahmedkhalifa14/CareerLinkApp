
package com.ahmedkhalifa.careerlinkapp.di

import com.ahmedkhalifa.careerlinkapp.data.network.JobsApiService
import com.ahmedkhalifa.careerlinkapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): JobsApiService =
        Retrofit.Builder().run {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }.create(JobsApiService::class.java)


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { interceptor ->
            val request: Request = interceptor.request()
            val newReq = request.newBuilder()
                .header("lang", "en")
                .header("User-Agent", "CareerLinkApp/1.0")
                .method(request.method, request.body)
                .build()
            interceptor.proceed(newReq)
        }.addNetworkInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()


}