package ru.mertsalovda.netlog.presentation.ui.dialog

import androidx.lifecycle.*
import ru.mertsalovda.netlog.interceptor.INetLogRepository
import ru.mertsalovda.netlog.model.NetLogItem

class NetLogDialogViewModel(
    private val repository: INetLogRepository
) : ViewModel() {

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    fun setSearchQuery(query: String) {
        _searchQuery.postValue(query)
    }

    fun getSearchedLogs(): LiveData<List<NetLogItem>> =
        MediatorLiveData<List<NetLogItem>>().apply {

            val onChanged = {
                val query = searchQuery.value ?: ""
                val items = repository.getItems().value ?: mutableListOf()
                value = if (query.isEmpty())
                    items
                else
                    items.filter { it.request.url().toString().contains(query) }
            }

            addSource(searchQuery) { onChanged.invoke() }
            addSource(repository.getItems()) { onChanged.invoke() }
        }


    class Factory(
        private val repository: INetLogRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NetLogDialogViewModel(repository) as T
        }

    }
}