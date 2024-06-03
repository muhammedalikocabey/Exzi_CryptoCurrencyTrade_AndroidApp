package com.muhammedalikocabey.exzi.core.di

import android.app.Application
import android.content.Context
import com.muhammedalikocabey.exzi.data.network.ApiClient
import com.muhammedalikocabey.exzi.data.network.BASE_SOCKET_URL
import com.muhammedalikocabey.exzi.data.remote.RemoteDataSource
import com.muhammedalikocabey.exzi.data.repository.GetRepositoryImp
import com.muhammedalikocabey.exzi.domain.repository.GetRepository
import com.muhammedalikocabey.exzi.domain.usecases.GetCandlesUseCase
import com.muhammedalikocabey.exzi.domain.usecases.GetOrderBookUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesNetworkClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient().newBuilder()
            .cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong()))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url
                val url = originalHttpUrl.newBuilder().build()
                request.url(url)
                return@addInterceptor chain.proceed(request.build())
            }
            .addInterceptor { chain ->
                var request = chain.request()
                request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60 * 5).build()
                chain.proceed(request)
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_SOCKET_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun providesGetCandleRepo(application: Application, remoteDataSource: RemoteDataSource): GetRepository {
        return GetRepositoryImp(application, remoteDataSource)
    }

    @Provides
    @Singleton
    fun providesGetCandlesUsecase(getRepository: GetRepository): GetCandlesUseCase {
        return GetCandlesUseCase(getRepository)
    }

    @Provides
    @Singleton
    fun providesGetOrderBookUseCase(getRepository: GetRepository): GetOrderBookUseCase {
        return GetOrderBookUseCase(getRepository)
    }
}
