package id.taufikibrahim.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.taufikibrahim.data.BuildConfig
import id.taufikibrahim.data.remote.MovieApiService
import id.taufikibrahim.data.remote.MovieAuthenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(MovieAuthenticator())
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(20L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(20L, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}