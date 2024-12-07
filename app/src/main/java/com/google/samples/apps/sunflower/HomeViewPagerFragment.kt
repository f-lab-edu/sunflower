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

    // [앱 첫 실행 1] 가장 먼저 실행
    // [설정에서 언어 변경 시 8]
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("lifecycle", "onAttach $this")
    }

    // [앱 첫 실행 2] onAttach -> onCreate
    // [설정에서 언어 변경 시 11]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("lifecycle", "onCreate $this")
    }

    // [앱 첫 실행 4] GardenActivity의 onCreate -> onCreateView
    // [PlantDetail에서 PlantList로 갈 때 3] PlantDetailFragment의 onStop -> onCreateView
    // [설정에서 언어 변경 시 13]
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

    // [앱 첫 실행 5] onCreateView -> onViewCreated
    // [PlantDetail에서 PlantList로 갈 때 4] onCreateView -> onViewCreated
    // [설정에서 언어 변경 시 14]
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("lifecycle", "onViewCreated $this")
    }

    // [앱 첫 실행 6] onViewCreated -> onActivityCreated
    // [PlantDetail에서 PlantList로 갈 때 7] PlantListFragment의 onViewCreated -> onActivityCreated
    // [설정에서 언어 변경 시 17]
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("lifecycle", "onActivityCreated $this")
    }

    // [앱 첫 실행 7] onActivityCreated -> onStart
    // [메인화면에서 home key로 앱을 bg로 보냈다가 다시 fg로 1] 가장 먼저 실행
    // [전원버튼 눌러서 화면 껏다가 다시 킬 때 1] 가장 먼저 실행
    // [메인화면에서 back키 이후 재실행 1] 가장 먼저 실행
    // [PlantDetail에서 PlantList로 갈 때 9] PlantListFragment의 onActivityCreated -> onStart
    // [설정에서 언어 변경 시 19]
    override fun onStart() {
        super.onStart()
        Log.e("lifecycle", "onStart $this")
    }

    // [앱 첫 실행 10] GardenActivity의 onResume -> onResume
    // [메인화면에서 home key로 앱을 bg로 보냈다가 다시 fg로 5] GardenActivity의 onResume -> onResume
    // [전원버튼 눌러서 화면 껏다가 다시 킬 때 5] GardenActivity의 onResume -> onResume
    // [메인화면에서 back키 이후 재실행 5] GardenActivity의 onResume -> onResume
    // [PlantDetail에서 PlantList로 갈 때 11] PlantListFragment의 onStart -> onResume
    // [설정에서 언어 변경 시 23]
    override fun onResume() {
        super.onResume()
        Log.e("lifecycle", "onResume $this")
    }

    // [메인화면에서 홈 키로 앱을 bg로 2] GardenFragment의 onPause -> onPause
    // [전원버튼 눌러서 화면 끌 때 2] GardenFragment의 onPause -> onPause
    // [메인화면에서 back 키 2] GardenFragment의 onPause -> onPause
    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 2] GardenFragment의 onPause -> onPause
    // [PlantList에서 PlantDetail 갈 때 2] PlantListFragment의 onPause -> onPause
    override fun onPause() {
        super.onPause()
        Log.e("lifecycle", "onPause  $this")
    }

    // [메인화면에서 홈 키로 앱을 bg로 5] GardenFragment의 onStop -> onStop
    // [전원버튼 눌러서 화면 끌 때 5] GardenFragment의 onStop -> onStop
    // [메인화면에서 back 키 5] GardenFragment의 onStop -> onStop
    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 5] GardenFragment의 onStop -> onStop
    // [PlantList에서 PlantDetail 갈 때 5] PlantListFragment의 onStop -> onStop
    override fun onStop() {
        super.onStop()
        Log.e("lifecycle", "onStop $this")
    }

    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 8] GardenFragment의 onDestroyView -> onDestroyView
    // [PlantList에서 PlantDetail 갈 때 15] PlantListFragment의 onDestroyView -> onDestroyView /
    // [설정에서 언어 변경 시 2]
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("lifecycle", "onDestroyView $this")
    }

    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 11] GardenFragment의 onDetach -> onDestroy
    // [설정에서 언어 변경 시 5]
    override fun onDestroy() {
        super.onDestroy()
        Log.e("lifecycle", "onDestroy $this")
    }

    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 12] onDestroy -> onDetach
    // [설정에서 언어 변경 시 6]
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
