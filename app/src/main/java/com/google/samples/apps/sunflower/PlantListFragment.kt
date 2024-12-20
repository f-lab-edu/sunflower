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
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.samples.apps.sunflower.adapters.PlantAdapter
import com.google.samples.apps.sunflower.databinding.FragmentPlantListBinding
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantListFragment : Fragment() {

    private lateinit var binding: FragmentPlantListBinding

    private val viewModel: PlantListViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("lifecycle", "onAttach $this")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("lifecycle", "onCreate $this")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.e("lifecycle", "onCreateView $this")
        binding = FragmentPlantListBinding.inflate(inflater, container, false)
        context ?: return binding.root // context가 null 이면 binding.root을 return
        // 왜 여기서만? 일부 시스템 상황에서 UI를 생성이나 복원하는 중 context가 null일 수 있다

        val adapter = PlantAdapter()
        binding.plantList.adapter = adapter
        initSearchView()
        subscribeUi(adapter)
        setHasOptionsMenu(true) // 옵션 메뉴 사용 설정
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu) // 필터 버튼 설정
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

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                Log.e("filter", "isFiltered")
                clearGrowZoneNumber()
            } else {
                Log.e("filter", "is not Filtered")
                setGrowZoneNumber(9) // growZoneNumber가 9인 식물만 필터링 함
            }
        }
    }

    private fun subscribeUi(adapter: PlantAdapter) {
        viewModel.plants.observe(viewLifecycleOwner) { plants ->
            adapter.submitList(plants)
        }

        viewModel.searchPlants.observe(viewLifecycleOwner) { searchPlants ->
            adapter.submitList(searchPlants)
        }
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    Log.e("search", "text submit : $query")
                }
                return false
                // Returns: 리스너가 쿼리를 처리한 경우 true, SearchView가 기본 작업을 수행하도록 하려면 false
                // false로 하는 경우 submit을 하면 text 창이 사라진다
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.run {
                    Log.e("search", "text change : $this")
                    viewModel.setKeyWord(this)
                }
                return false
                // false로 하면 text 입력시 입력창에 x가 생긴다
            }
        })

        binding.buttonSearch.setOnClickListener {
            val query = binding.searchView.query.toString()
            Log.e("search", "ok button : $query")
        }
    }

}
