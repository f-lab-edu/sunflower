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
// MY GARDEN 메뉴 화면
// Fragment 클래스를 상속받는다
class GardenFragment : Fragment() {

    // lateinit => 변수 값을 나중에 초기화한다고 명시하는 것, 변수의 첫 상태를 정의하기 어려울 때 사용, null 값을 가질 수 없다
    // : => 변수의 타입을 지정
    // FragmentGardenBinding => fragment_garden.xml
    // 초기값 없이 변수의 타입만 지정, 다른 메서드에서도 접근할 수 있도록
    private lateinit var binding: FragmentGardenBinding
    private val viewModel: GardenPlantingListViewModel by viewModels()

    // 호스트 액티비티에 연결될 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("lifecycle", "onAttach $this")
    }

    // 프래그먼트가 액티비티의 호출을 받아 생성
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("lifecycle", "onCreate $this")
    }

    // Fragment의 UI를 생성, Fragment의 레이아웃을 설정하고, 뷰를 반환
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

    // UI가 생성된 후 뷰 관련 작업을 수행
    // View 의 초기값을 설정해주거나 RecyclerView나 ViewPager2에 사용할 Adapter 초기화 등을 이곳에서 수행하는 것이 적절
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("lifecycle", "onViewCreated $this")
    }

    // deprecated 되면서 Activity의 생명주기와 Fragment의 생명주기가 서로 영향을 덜 주는 방향으로 개편
    // View 관련 초기화는 onViewCreated, 나머지 초기화 루틴은 onCreate에서 수행
    // INITIALIZED 상태
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//    }

    // 저장해둔 모든 state 값이 Fragment의 View 계층구조에 복원됐을 때
    // INITIALIZED → CREATED
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.e("lifecycle", "onViewStateRestored $this")
    }

    // Fragment가 사용자에게 보이기 시작
    // CREATED → STARTED
    override fun onStart() {
        super.onStart()
        Log.e("lifecycle", "onStart $this")
    }

    // Fragment가 사용자와 상호작용 할 준비가 되었을 때
    override fun onResume() {
        super.onResume()
        Log.e("lifecycle", "onResume $this")
    }

    // 다른 Fragment나 Activity가 포그라운드로 전환되기 전
    // PAUSED X
    override fun onPause() {
        super.onPause()
        Log.e("lifecycle", "onPause  $this")
    }

    // Fragment가 더 이상 사용자에게 보이지 않을 때
    // STARTED → CREATED
    override fun onStop() {
        super.onStop()
        Log.e("lifecycle", "onStop $this")
    }

    // Fragment의 UI가 소멸
    // CREATED → DESTROYED
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("lifecycle", "onDestroyView $this")
    }

    // Fragment가 완전히 소멸
    override fun onDestroy() {
        super.onDestroy()
        Log.e("lifecycle", "onDestroy $this")
    }

    // Fragment가 Activity와의 연결이 끊어질 때
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
