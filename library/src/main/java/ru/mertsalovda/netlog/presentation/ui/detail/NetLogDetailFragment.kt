package ru.mertsalovda.netlog.presentation.ui.detail

import android.os.Bundle
import android.view.*
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.mertsalovda.netlog.interceptor.NetLogItem
import ru.mertsalovda.netlog.databinding.FragmentNetLogDetailBinding

class NetLogDetailFragment : BottomSheetDialogFragment() {

    private var item: NetLogItem? = null
    private var _binding: FragmentNetLogDetailBinding? = null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNetLogDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (item == null) dismiss()

        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), item!!, childFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(item: NetLogItem) =
            NetLogDetailFragment().apply {
                this.item = item
            }
    }
}