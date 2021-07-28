package ru.mertsalovda.netlog.presentation.ui.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.mertsalovda.netlog.interceptor.INetLogRepository
import ru.mertsalovda.netlog.databinding.FragmentNetlogDialogListDialogBinding
import ru.mertsalovda.netlog.presentation.ui.dialog.adapter.ItemAdapter
import ru.mertsalovda.netlog.presentation.ui.detail.NetLogDetailFragment

class NetLogDialogFragment : BottomSheetDialogFragment() {

    private var itemAdapter: ItemAdapter = ItemAdapter { item ->
        childFragmentManager.beginTransaction()
            .add(NetLogDetailFragment.newInstance(item), "NetLogDetailFragment")
            .addToBackStack("NetLogDetailFragment")
            .commit()
    }

    private lateinit var repository: INetLogRepository

    private var _binding: FragmentNetlogDialogListDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NetLogDialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNetlogDialogListDialogBinding.inflate(inflater, container, false)
        if (!::repository.isInitialized) {
            dismiss()
        }
        viewModel = ViewModelProvider(this, NetLogDialogViewModel.Factory(repository)).get(NetLogDialogViewModel::class.java)
        return binding.root

    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = itemAdapter
        itemAdapter.setData(repository.getItems().value ?: mutableListOf())

        viewModel.getSearchedLogs().observe(viewLifecycleOwner, Observer { items ->
            itemAdapter.setData(items.toMutableList())
        })

        binding.clearBtn.setOnClickListener {
            repository.clear()
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    viewModel.setSearchQuery(it.toString())
                }
            }
        })
    }


    companion object {

        fun newInstance(repository: INetLogRepository): NetLogDialogFragment =
            NetLogDialogFragment().apply {
                this.repository = repository
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}