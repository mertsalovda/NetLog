package ru.mertsalovda.netlog

interface INetLogRepository {
    fun addItem(item: NetLogItem)
    fun getItems(): ObservableList<MutableList<NetLogItem>>
    fun clear()
}
