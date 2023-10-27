package app.android_challenge.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.android_challenge.R
import app.android_challenge.data.models.Result

class ArticleAdapter : ListAdapter<Result, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val sourceTextView: TextView = itemView.findViewById(R.id.textViewSource)
        private val publishedDateTextView: TextView =
            itemView.findViewById(R.id.textViewPublishedDate)
        private val sectionTextView: TextView = itemView.findViewById(R.id.textViewSection)

        fun bind(article: Result) {
            titleTextView.text = article.url
            sourceTextView.text = article.source
            publishedDateTextView.text = article.published_date
            sectionTextView.text = article.section
        }
    }

    fun submitNewList(newArticles: List<Result>) {
        submitList(newArticles)
    }

}

class ArticleDiffCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}