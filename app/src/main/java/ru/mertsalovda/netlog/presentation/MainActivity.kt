package ru.mertsalovda.netlog.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.mertsalovda.netlog.R
import ru.mertsalovda.netlog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    fun openNetLog(view: View) {
        when (view.id) {
            R.id.btnRest -> viewModel.sendRest()
        }
        Toast.makeText(this, "${(view as? Button)?.text}", Toast.LENGTH_SHORT).show()
    }
}