package app.android_challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.android_challenge.app.presentation.ArticleViewModel
import app.android_challenge.domain.repository.ArticleRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import app.android_challenge.data.models.Result
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class ArticleViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var repository: ArticleRepository

    private lateinit var viewModel: ArticleViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ArticleViewModel(repository)
    }

    @Test
    fun `fetchArticles should update live data with success`() = runBlocking() {
        val articles = listOf<Result>(
            Result(
                "Sample Abstract 1",
                "Sample Keywords 1",
                12345,
                "Sample Byline 1",
                "",
                emptyList(),
                67890,
                emptyList(),
                23456,
                emptyList(),
                "Sample NY Times Section 1",
                emptyList(),
                listOf("Sample Person 1", "Sample Person 2"),
                "2023-10-28",
                "Sample Section 1",
                "Sample Source 1",
                "Sample Subsection 1",
                "Sample Title 1",
                "Sample Type 1",
                "2023-10-28",
                "https://example.com/article1" ,
                ""
            )
        )

        coEvery { repository.getCombinedArticles() } returns articles

        val observer = mockk<Observer<List<app.android_challenge.data.models.Result>>>()
        viewModel.articlesLiveData.observeForever(observer)

        viewModel.fetchArticles()

        coVerify { repository.getCombinedArticles() }
        verifySequence {
            observer.onChanged(any())
            observer.onChanged(articles)
        }
    }

    @Test
    fun `fetchArticles should update live data with error`() = runBlocking() {
        val errorMessage = "An error occurred"

        coEvery { repository.getCombinedArticles() } throws Exception(errorMessage)

        val observer = mockk<Observer<String>>()
        viewModel.errorMessageLiveData.observeForever(observer)

        viewModel.fetchArticles()

        coVerify { repository.getCombinedArticles() }
        verifySequence {
            observer.onChanged(any())
            observer.onChanged(errorMessage)
        }
    }
}