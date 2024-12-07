/*
 * Copyright 2018 Google LLC
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter
import com.google.samples.apps.sunflower.adapters.PLANT_LIST_PAGE_INDEX
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {

    private lateinit var binding: FragmentGardenBinding
    private val viewModel: GardenPlantingListViewModel by viewModels()

    // [앱 첫 실행 11] HomeViewPagerFragment의 onResume -> onAttach
    // [설정에서 언어 변경 시 9]
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("lifecycle", "onAttach $this")
    }

    // [앱 첫 실행 12] onAttach -> onCreate
    // [설정에서 언어 변경 시 10]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("lifecycle", "onCreate $this")
    }

    // [앱 첫 실행 13] onCreate -> onCreateView
    // [설정에서 언어 변경 시 15]
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("lifecycle", "onCreateView $this")
        binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }

        subscribeUi(adapter, binding)
        return binding.root
    }

    // [앱 첫 실행 14] onCreateView -> onViewCreated
    // [설정에서 언어 변경 시 16]
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("lifecycle", "onViewCreated $this")
    }

    // [앱 첫 실행 15] onViewCreated -> onActivityCreated
    // [설정에서 언어 변경 시 18]
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("lifecycle", "onActivityCreated $this")
    }

    // [앱 첫 실행 16] onActivityCreated -> onStart
    // [메인화면에서 home key로 앱을 bg로 보냈다가 다시 fg로 2] HomeViewPagerFragment의 onStart -> onStart
    // [전원버튼 눌러서 화면 껏다가 다시 킬 때 2] HomeViewPagerFragment의 onStart -> onStart
    // [메인화면에서 back키 이후 재실행 2] HomeViewPagerFragment의 onStart -> onStart
    // [설정에서 언어 변경 시 20]
    override fun onStart() {
        super.onStart()
        Log.e("lifecycle", "onStart $this")
    }

    // [앱 첫 실행 17] onStart -> onResume /
    // [메인화면에서 home key로 앱을 bg로 보냈다가 다시 fg로 6] HomeViewPagerFragment의 onResume -> onResume /
    // [전원버튼 눌러서 화면 껏다가 다시 킬 때 6] HomeViewPagerFragment의 onResume -> onResume /
    // [메인화면에서 back키 이후 재실행 6] HomeViewPagerFragment의 onResume -> onResume /
    // [PlantList에서 메인으로 돌아갈 때 2] PlantListFragment의 onPause -> onResume /
    // [설정에서 언어 변경 시 24] /
    override fun onResume() {
        super.onResume()
        Log.e("lifecycle", "onResume $this")
    }

    // [메인화면에서 홈 키로 앱을 bg로 1] 가장 먼저 실행
    // [전원버튼 눌러서 화면 끌 때 1] 가장 먼저 실행
    // [메인화면에서 back 키 1] 가장 먼저 실행
    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 1] 가장 먼저 실행
    // [PlantList 열릴 때 7] PlantListFragment의 onStart -> onPause
    override fun onPause() {
        super.onPause()
        Log.e("lifecycle", "onPause  $this")
    }

    // [메인화면에서 홈 키로 앱을 bg로 4] GardenActivity의 onPause -> onStop
    // [전원버튼 눌러서 화면 끌 때 4] GardenActivity의 onPause -> onStop
    // [메인화면에서 back 키 4] GardenActivity의 onPause -> onStop
    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 4] GardenActivity의 onPause -> onStop
    // [PlantList에서 PlantDetail 갈 때 3] HomeViewPagerFragment의 onPause -> onStop
    override fun onStop() {
        super.onStop()
        Log.e("lifecycle", "onStop $this")
    }

    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 7] GardenActivity의 onStop -> onDestroyView
    // [PlantList에서 PlantDetail 갈 때 13] PlantDetailFragment의 onResume -> onDestroyView
    // [설정에서 언어 변경 시 1]
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("lifecycle", "onDestroyView $this")
    }

    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 9] HomeViewPagerFragment의 onDestroyView -> onDestroy
    // [설정에서 언어 변경 시 3]
    override fun onDestroy() {
        super.onDestroy()
        Log.e("lifecycle", "onDestroy $this")
    }

    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 10] onDestroy -> onDetach
    // [설정에서 언어 변경 시 4]
    override fun onDetach() {
        super.onDetach()
        Log.e("lifecycle", "onDetach $this")
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            binding.hasPlantings = result.isNotEmpty()
            adapter.submitList(result) {
                // At this point, the content should be drawn
                activity?.reportFullyDrawn()
            }
        }
    }

    // TODO: convert to data binding if applicable
    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PLANT_LIST_PAGE_INDEX
    }

}
