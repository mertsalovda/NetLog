package ru.mertsalovda.netlog.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import ru.mertsalovda.netlog.INetLogRepository
import ru.mertsalovda.netlog.databinding.FragmentNetlogDialogListDialogBinding
import ru.mertsalovda.netlog.presentation.adapter.ItemAdapter

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    NetLogDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class NetLogDialogFragment : BottomSheetDialogFragment() {

    private var itemAdapter: ItemAdapter = ItemAdapter { item ->
        childFragmentManager.beginTransaction()
            .add(NetLogDetailFragment.newInstance(item), "NetLogDetailFragment")
            .addToBackStack("NetLogDetailFragment")
            .commit()
    }

    private var repository: INetLogRepository? = null

    private var _binding: FragmentNetlogDialogListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentNetlogDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = itemAdapter
        if (repository == null) {
            dismiss()
        }
        itemAdapter.setData(viewLifecycleOwner.lifecycleScope, repository?.getItems()?.getValue() ?: mutableListOf())

        repository?.getItems()?.subscribe(this) { items ->
            itemAdapter.setData(viewLifecycleOwner.lifecycleScope, items)
        }

        binding.clearBtn.setOnClickListener {
            repository?.clear()
        }
    }


    companion object {

        fun newInstance(repository: INetLogRepository): NetLogDialogFragment =
            NetLogDialogFragment().apply {
                this.repository = repository
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        repository?.getItems()?.unsubscribe(this)
        _binding = null
    }
}