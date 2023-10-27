package app.android_challenge.domain.repository

import app.android_challenge.app.network.Api
import app.android_challenge.data.models.Result
import app.android_challenge.util.AppConstants.Companion.API_KEY
import app.android_challenge.util.AppConstants.Companion.FIRST_PERIOD
import app.android_challenge.util.AppConstants.Companion.SECOND_PERIOD
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class ArticleRepository(
    private val nyTimesService: Api.NYTimesService,
) {
    suspend fun getCombinedArticles(): List<Result> = runBlocking {

        coroutineScope {
            val deferred1 =
                async {
                    nyTimesService.getMostPopularArticlesAsync(
                        FIRST_PERIOD, API_KEY
                    ).await().results
                }
            val deferred2 =
                async {
                    nyTimesService.getMostPopularArticlesAsync(
                        SECOND_PERIOD, API_KEY
                    ).await().results
                }
            val articlesList = awaitAll(deferred1, deferred2)
            articlesList.flatten()
        }
    }
}