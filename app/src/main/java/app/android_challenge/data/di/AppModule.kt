package app.android_challenge.data.di

import app.android_challenge.app.network.Api
import app.android_challenge.domain.repository.ArticleRepository
import app.android_challenge.util.AppConstants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideArticleRepository(
        nyTimesService: Api.NYTimesService,
    ): ArticleRepository {
        return ArticleRepository(nyTimesService)
    }

    @Provides
    @Singleton
    fun provideNYTimesService(): Api.NYTimesService {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.NYTimesService::class.java)
    }
}