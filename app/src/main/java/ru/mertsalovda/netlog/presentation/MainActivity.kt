package ru.mertsalovda.netlog.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.mertsalovda.netlog.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tvText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        tvText = findViewById(R.id.tvText)
        initObservers()
    }

    private fun initObservers() {
        viewModel.countries.observe(this, {
            setTextInPreview(it.toString())
        })
    }

    private fun setTextInPreview(text: String) {
        tvText.text = text
    }

    fun openNetLog(view: View) {
        when (view.id) {
            R.id.btnRest -> viewModel.sendRest()
        }
        Toast.makeText(this, "${(view as? Button)?.text}", Toast.LENGTH_SHORT).show()
    }
}