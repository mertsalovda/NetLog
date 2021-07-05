package ru.mertsalovda.netlog.interceptor

class NetLogRepositoryImpl : INetLogRepository {
    private val items = ObservableList(mutableListOf<NetLogItem>())

    override fun addItem(item: NetLogItem) {
        val list = items.getValue()
        list.add(0, item)
        items.setValue(list)
    }

    override fun getItems(): ObservableList<MutableList<NetLogItem>> = items

    override fun clear() {
        items.setValue(mutableListOf())
    }

}