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
                val queryList = query.split(" ")
                val ignoreList = queryList.filter { it.startsWith("-") }
                    .map { if (it.startsWith("-")) it.replace("-", "") else it }
                val searchQueryList = if (ignoreList.isEmpty()) queryList else queryList.filter { !it.contains(getRegex(ignoreList)) }
                val items = repository.getItems().value ?: mutableListOf()
                value = if (query.isEmpty())
                    items
                else
                    items.filter { itemIsValidSearchQuery(it, searchQueryList, ignoreList) }
            }

            addSource(searchQuery) { onChanged.invoke() }
            addSource(repository.getItems()) { onChanged.invoke() }
        }

    private fun itemIsValidSearchQuery(
        netLogItem: NetLogItem,
        searchQueryList: List<String>,
        ignoreList: List<String>
    ): Boolean {
        val url = netLogItem.request.url().toString()
        val containsInSearch = url.contains(getRegex(searchQueryList)) || searchQueryList.isEmpty()
        val notContainsInIgnore = !url.contains(getRegex(ignoreList)) || ignoreList.isEmpty()
        return containsInSearch && notContainsInIgnore
    }

    private fun getRegex(allQueryList: List<String>) =
        allQueryList.joinToString(separator = "|").toRegex()


    class Factory(
        private val repository: INetLogRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NetLogDialogViewModel(repository) as T
        }

    }
}