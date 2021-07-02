package ru.mertsalovda.netlog.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Request
import okhttp3.Response
import ru.mertsalovda.netlog.utils.formatToString

class PageViewModel : ViewModel() {

    private val _text = MutableLiveData<String>("")
    val text: LiveData<String> = _text

    fun updateData(request: Request) {

        _text.postValue(request.formatToString())
    }

    fun updateData(response: Response, body: String) {
        _text.postValue(response.formatToString(body))
    }
}