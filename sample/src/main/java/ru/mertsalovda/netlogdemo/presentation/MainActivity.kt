package ru.mertsalovda.netlogdemo.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.mertsalovda.netlog.presentation.ui.dialog.NetLogDialogFragment
import ru.mertsalovda.netlogdemo.App
import ru.mertsalovda.netlogdemo.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun click(view: View) {
        when (view.id) {
            R.id.btnRest -> viewModel.sendRest()
            R.id.btnNetLog -> NetLogDialogFragment.newInstance(App.netLogRepository).show(supportFragmentManager, "NetLog")
        }
    }
}