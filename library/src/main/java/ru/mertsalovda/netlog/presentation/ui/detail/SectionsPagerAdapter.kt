package ru.mertsalovda.netlog.presentation.ui.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.mertsalovda.netlog.interceptor.NetLogItem
import ru.mertsalovda.netlog.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_request,
    R.string.tab_text_response
)

private const val REQUEST_POSITION = 0
private const val RESPONSE_POSITION = 1

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    private val context: Context,
    private val item: NetLogItem,
    fm: FragmentManager,
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            REQUEST_POSITION -> PlaceholderFragment.newInstance(item.request, null)
            RESPONSE_POSITION -> PlaceholderFragment.newInstance(null, item.response, item.responseBody)
            else -> throw IndexOutOfBoundsException("Position failure")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}