package ru.mertsalovda.netlogdemo

import android.app.Application
import ru.mertsalovda.netlog.interceptor.INetLogRepository
import ru.mertsalovda.netlog.repository.NetLogRepositoryImpl

class App : Application() {

    companion object {
        lateinit var netLogRepository: INetLogRepository
    }

    override fun onCreate() {
        super.onCreate()
        netLogRepository = NetLogRepositoryImpl()
    }
}