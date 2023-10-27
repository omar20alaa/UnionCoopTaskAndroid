package app.android_challenge.app.network


import app.android_challenge.data.models.ArticleResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    interface NYTimesService {
        @GET("all-sections/{period}.json")
        fun getMostPopularArticlesAsync(
            @Path("period") period: String,
            @Query("api-key") apiKey: String
        ): Deferred<ArticleResponse>
    }




}