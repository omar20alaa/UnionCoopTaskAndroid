package app.android_challenge.app.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.android_challenge.R
import app.android_challenge.app.adapter.ArticleAdapter
import app.android_challenge.data.models.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesActivity : AppCompatActivity() {

    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var adapter: ArticleAdapter

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        initViews()
        setupRecyclerView()
        setupObservers()
        viewModel.fetchArticles()
    }

    private fun initViews() {
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.rv_articles)
        tvDesc = findViewById(R.id.tv_desc)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter()
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.articlesLiveData.observe(this) { articles ->
            adapter.submitNewList(articles)
            tvDesc.text = getDetails(articles)
        }

        viewModel.loadingLiveData.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessageLiveData.observe(this) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDetails(articles: List<Result>): String {
        return getString(R.string.show_details).trimIndent() + "\nTotal items --> ${articles.size} item"
    }
}