package ru.mertsalovda.netlog.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import okhttp3.Request
import okhttp3.Response
import ru.mertsalovda.netlog.databinding.FragmentNetLogDetailPageBinding

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private var response: Response? = null
    private var request: Request? = null
    private var body: String = ""
    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentNetLogDetailPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)
        if (request != null) {
            pageViewModel.updateData(request!!)
        } else {
            response?.let { pageViewModel.updateData(it, body) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentNetLogDetailPageBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.detailText
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(request: Request?, response: Response?, body: String = ""): PlaceholderFragment {
            return PlaceholderFragment().apply {
                this.request = request
                this.response = response
                this.body = body
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}