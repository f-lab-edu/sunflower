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

package com.google.samples.apps.sunflower.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import com.google.samples.apps.sunflower.databinding.ListItemGardenPlantingBinding
import com.google.samples.apps.sunflower.viewmodels.PlantAndGardenPlantingsViewModel

// [정원에 심은 식물 데이터를 RecyclerView로 표시, 리스트 데이터의 변경 사항을 효율적으로 반영]
// 그래서 어떻게 정원에 심은 식물 데이터가 표시되나? GardenFragment의 submitList 호출부분 확인필요

// RecyclerView에서 사용하는 ListAdapter를 상속
class GardenPlantingAdapter : ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder> (GardenPlantDiffCallback()) {
    // ListAdapter => 리스트의 변화를 효율적으로 처리
    // 어떻게 ? 내장하고 있는 DiffUtil을 사용하기 때문
    // DiffUtil이란 ? RecyclerView의 기존 데이터와 새로운 데이터를 비교하여, 변경된 항목만 갱신
    // DiffUtil.ItemCallback의 구현 필요

    // PlantAndGardenPlantings => RecyclerView에서 사용할 데이터의 타입
    // GardenPlantingAdapter.ViewHolder => RecyclerView의 각 항목을 관리 및 표시하는 ViewHolder 의 타입

    // ViewHolder란 ? 리스트 형태의 데이터를 화면에 표시할 때, 재사용 가능한 View를 관리 한다
    // RecyclerView가 각 아이템의 데이터를 View와 연결할 때 효율적으로 처리하도록 돕는다
    // View 객체를 메모리에 계속 생성이나 삭제하지 않도록 한다

    // 스크롤 시 새로운 항목이 필요 하거나 RecyclerView가 처음 생성될 때 호출
    // ViewHolder를 생성하고 XML 레이아웃 파일을 View 객체로 변환해 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.e("data", "onCreateViewHolder $this")
        return ViewHolder(
            DataBindingUtil.inflate( // Data Binding을 사용해서 XML 파일을 실제 뷰 객체로 변환
                LayoutInflater.from(parent.context), // parent.context => 현재 RecyclerView가 위치한 Context
                R.layout.list_item_garden_planting, // 인플레이트할 XML 레이아웃 파일의 리소스 ID
                parent, // inflate 된 뷰가 추가될 부모 ViewGroup 지정 (RecyclerView)
                false
            )
        )
    }

    // ViewHolder가 화면에 표시될 때, 데이터가 변경 되었을 때, 새로운 데이터를 ViewHolder에 바인딩 할 때 호출
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("data", "onBindViewHolder $this")
        holder.bind(getItem(position))
        // position에 해당하는 데이터를 가져와서 ViewHolder와 데이터 연결
    }

    class ViewHolder(private val binding: ListItemGardenPlantingBinding) : RecyclerView.ViewHolder(binding.root) {
        // RecyclerView.ViewHolder를 상속받는다
        // binding.root 란 ? 루트 뷰는 해당 XML 레이아웃의 전체 구조를 정의한다
        // 이 뷰가 있어야 그 안에 있는 다른 UI 요소(버튼, 텍스트뷰 등)에 접근할 수 있다
        init { // 클래스가 생성될 때 자동으로 실행되는 초기화 블록
            binding.setClickListener { view ->
                binding.viewModel?.plantId?.let { plantId ->
                    // plantId만 사용한다면 굳이 viewModel을 만들필요가 있나? 나머지 데이터는?
                    // 데이터 바인딩을 통해 XML에서 데이터를 직접 활용한다

                    // 바인딩된 ViewModel에서 plantId를 가져온다. let은 plantId가 null이 아닐 때만 실행
                    // 클릭한 식물이 뭔지 어떻게 알고 plantId를 가져온다는 걸까?
                    // RecyclerView는 리스트의 각 항목에 데이터를 바인딩하고, 바인딩된 데이터는 해당 뷰와 연결되기 때문
                    navigateToPlant(plantId, view)
                }
            }
        }

        // plantId를 통해 식물 세부 정보 화면으로 이동
        private fun navigateToPlant(plantId: String, view: View) {
            val direction = HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(plantId)
            // 무슨 메소드 ?
            // Jetpack Navigation Component는 앱 내의 화면 간 전환을 쉽게 관리하도록 도와주는 라이브러리
            // HomeViewPagerFragmentDirections => navigation_graph.xml에 정의된 Action을 통해 자동 생성
            // plantId를 받아서 어떻게 처리하나 ? navigation_graph.xml에 plantId라는 인자를 전달하도록 정의함
            // direction 이란? Action을 호출하고 매개변수를 전달하기 위해 생성된 객체
            // 화면 간 전환 정보를 담고 있고, 필요시 데이터를 인자로 전달할 수 있다
            view.findNavController().navigate(direction)
        }

        // ViewModel과 XML 레이아웃을 연결
        fun bind(plantings: PlantAndGardenPlantings) {
            with(binding) { // with=> binding 객체 내에서 작업을 간결하게 수행 (binding. 생략)
                viewModel = PlantAndGardenPlantingsViewModel(plantings)
                // PlantAndGardenPlantings 데이터를 기반으로 ViewModel을 생성하고, 바인딩된 ViewModel에 설정
                executePendingBindings()
                // 데이터 바인딩을 실행하여 UI를 데이터를 반영
            }
        }
    }
}

// 두 리스트의 항목을 비교하여 어떤 항목이 변경되었는지를 판단하는 메서드를 제공
// <PlantAndGardenPlantings> => PlantAndGardenPlantings 객체를 다룰 것이라고 명시
private class GardenPlantDiffCallback : DiffUtil.ItemCallback<PlantAndGardenPlantings>() {
    // 이 메소드들은 언제 어떻게 호출될까 ? submitList(newList)가 호출될 때,
    // ListAdapter가 newList를 받아 DiffUtil을 사용해서 oldList를 비교한다. 이 때 두 메서드를 호출한다

    override fun areItemsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        Log.e("data", "areItemsTheSame $this")
        return oldItem.plant.plantId == newItem.plant.plantId
        // plantId를 비교해서 두 항목이 같은 식물인지 확인
    }

    override fun areContentsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        Log.e("data", "areContentsTheSame $this")
        return oldItem.plant == newItem.plant
        // Plant 객체의 모든 속성을 비교
    }
}
