package com.sarmady.task.di

import com.sarmady.task.data.remote.RemoteApi
import com.sarmady.task.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) //scope all the app
object AppModule {

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): RemoteApi =
        retrofit.create(RemoteApi::class.java)


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()


    @Provides
    fun provideClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor { chain: Interceptor.Chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader("Accept", "application/json")
                chain.proceed(requestBuilder.build())
            }.build()
    }
}