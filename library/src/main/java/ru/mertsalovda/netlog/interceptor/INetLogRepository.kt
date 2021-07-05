package ru.mertsalovda.netlog.interceptor

interface INetLogRepository {
    fun addItem(item: NetLogItem)
    fun getItems(): ObservableList<MutableList<NetLogItem>>
    fun clear()
}
