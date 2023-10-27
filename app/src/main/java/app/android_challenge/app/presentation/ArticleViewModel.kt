package app.android_challenge.app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.android_challenge.data.models.Result
import app.android_challenge.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val repository: ArticleRepository) : ViewModel() {
    private val _articlesLiveData = MutableLiveData<List<Result>>()
    val articlesLiveData: LiveData<List<Result>> get() = _articlesLiveData
    val loadingLiveData = MutableLiveData<Boolean>()
    private val _errorMessageLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = _errorMessageLiveData

    fun fetchArticles() {
        loadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val articles = repository.getCombinedArticles()
                _articlesLiveData.postValue(articles)
            } catch (e: Exception) {
                _errorMessageLiveData.postValue("An error occurred: ${e.message}")
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }
}