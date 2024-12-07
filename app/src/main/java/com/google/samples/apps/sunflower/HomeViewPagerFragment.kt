/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.samples.apps.sunflower.adapters.MY_GARDEN_PAGE_INDEX
import com.google.samples.apps.sunflower.adapters.PLANT_LIST_PAGE_INDEX
import com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter
import com.google.samples.apps.sunflower.databinding.FragmentViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeViewPagerFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("lifecycle", "onAttach $this")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("lifecycle", "onCreate $this")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("lifecycle", "onCreateView $this")
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = SunflowerPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("lifecycle", "onViewCreated $this")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.e("lifecycle", "onViewStateRestored $this")
    }

    override fun onStart() {
        super.onStart()
        Log.e("lifecycle", "onStart $this")
    }

    override fun onResume() {
        super.onResume()
        Log.e("lifecycle", "onResume $this")
    }

    override fun onPause() {
        super.onPause()
        Log.e("lifecycle", "onPause  $this")
    }

    override fun onStop() {
        super.onStop()
        Log.e("lifecycle", "onStop $this")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("lifecycle", "onDestroyView $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("lifecycle", "onDestroy $this")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("lifecycle", "onDetach $this")
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector
            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title)
            PLANT_LIST_PAGE_INDEX -> getString(R.string.plant_list_title)
            else -> null
        }
    }

}
