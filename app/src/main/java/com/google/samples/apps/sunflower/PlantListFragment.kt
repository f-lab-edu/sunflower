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
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.samples.apps.sunflower.adapters.PlantAdapter
import com.google.samples.apps.sunflower.databinding.FragmentPlantListBinding
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantListFragment : Fragment() {

    private val viewModel: PlantListViewModel by viewModels()

    // [PlantList 열릴 때 1] 가장 먼저 실행
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("lifecycle", "onAttach $this")
    }

    // [PlantList 열릴 때 2] onAttach -> onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("lifecycle", "onCreate $this")
    }

    // [PlantList 열릴 때 3] onCreate -> onCreateView
    // [PlantDetail에서 PlantList로 갈 때 5] HomeViewPagerFragment의 onViewCreated -> onCreateView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.e("lifecycle", "onCreateView $this")
        val binding = FragmentPlantListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = PlantAdapter()
        binding.plantList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    // [PlantList 열릴 때 4] onCreateView -> onViewCreated
    // [PlantDetail에서 PlantList로 갈 때 6] onCreateView -> onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("lifecycle", "onViewCreated $this")
    }

    // [PlantList 열릴 때 5] onViewCreated -> onActivityCreated
    // [PlantDetail에서 PlantList로 갈 때 8] HomeViewPagerFragment의 onActivityCreated -> onActivityCreated
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("lifecycle", "onActivityCreated $this")
    }

    // [PlantList 열릴 때 6] onActivityCreated -> onStart
    // [PlantDetail에서 PlantList로 갈 때 10] HomeViewPagerFragment의 onStart -> onStart
    override fun onStart() {
        super.onStart()
        Log.e("lifecycle", "onStart $this")
    }

    // [PlantList 열릴 때 8] GardenFragment의 onPause -> onResume /
    // [PlantDetail에서 PlantList로 갈 때 12] HomeViewPagerFragment의 onResume -> onResume
    override fun onResume() {
        super.onResume()
        Log.e("lifecycle", "onResume $this")
    }

    // [PlantList에서 메인으로 돌아갈 때 1] 가장 먼저 실행
    // [PlantList에서 PlantDetail 갈 때 1] 가장 먼저 실행
    override fun onPause() {
        super.onPause()
        Log.e("lifecycle", "onPause  $this")
    }

    // [PlantList에서 PlantDetail 갈 때 4] GardenFragment의 onStop -> onStop
    override fun onStop() {
        super.onStop()
        Log.e("lifecycle", "onStop $this")
    }

    // [PlantList에서 PlantDetail 갈 때 14] GardenFragment의 onDestroyView -> onDestroyView
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {
                updateData()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: PlantAdapter) {
        viewModel.plants.observe(viewLifecycleOwner) { plants ->
            adapter.submitList(plants)
        }
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearGrowZoneNumber()
            } else {
                setGrowZoneNumber(9)
            }
        }
    }
}
