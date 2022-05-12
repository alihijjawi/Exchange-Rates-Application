package com.hmekhatib.currencyexchange


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                ExchangeFragment()
            }
            1 -> {
                TransactionsFragment()
            }
            2 -> {
                InsightsFragment()
            }
            3 -> {
                PostsFragment()
            }
            else -> ExchangeFragment()
        }
    }
    override fun getItemCount(): Int {
        return 4
    }
}