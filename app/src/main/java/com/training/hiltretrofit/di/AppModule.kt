package com.training.hiltretrofit.di

import android.util.Log
import com.training.hiltretrofit.api.ApiService
import com.training.hiltretrofit.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private var retrofit:Retrofit?=null

    @Provides
    fun provideApiServiceInstance(retrofit: Retrofit):ApiService{
      return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideRetrofitInstance(
        okkHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ):Retrofit{
      if (retrofit == null){
          retrofit = Retrofit.Builder()
              .client(okkHttpClient)
              .baseUrl(Constants.BASE_URL)
              .addConverterFactory(gsonConverterFactory)
              .build()
      }
        return retrofit!!
    }

    @Provides
    fun provideGsonFactoryInstance():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClientInstance(interceptor: HttpLoggingInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(interceptor = interceptor).build()
    }

    @Provides
    fun provideInterceptorInstance(): HttpLoggingInterceptor {
        var interceptor = HttpLoggingInterceptor(object :
            HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.e("api",message)
            }
        }
        ).setLevel(HttpLoggingInterceptor.Level.BODY)

        return interceptor
    }

}