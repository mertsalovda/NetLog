package ru.mertsalovda.netlogdemo

import android.app.Application
import ru.mertsalovda.netlog.INetLogRepository

class App : Application() {

    companion object {
        lateinit var netLogRepository: INetLogRepository
    }

    override fun onCreate() {
        super.onCreate()
        netLogRepository = NetLogRepositoryImpl()
    }
}