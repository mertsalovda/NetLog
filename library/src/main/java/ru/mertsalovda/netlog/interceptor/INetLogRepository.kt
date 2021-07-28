package ru.mertsalovda.netlog.interceptor

import androidx.lifecycle.LiveData
import ru.mertsalovda.netlog.model.NetLogItem

/**
 * This interface implements a class that stores network logs
 */
interface INetLogRepository {
    /**
     * Add an entry to the log
     *
     * @param item  new entry
     */
    fun addItem(item: NetLogItem)

    /**
     * Get all log entries from the repository
     *
     * @return  [LiveData] with collection of log records
     */
    fun getItems(): LiveData<MutableList<NetLogItem>>

    /**
     * Delete all entries from the repository
     *
     */
    fun clear()
}
