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

// [정원에 심은 식물 목록, add plant 누를 경우 화면 전환, LiveDate를 통해 데이터 업데이트 시 UI 자동 업데이트]

@AndroidEntryPoint
// MY GARDEN 메뉴 화면
// Fragment 클래스를 상속받는다
class GardenFragment : Fragment() {

    // lateinit => 변수 값을 나중에 초기화한다고 명시하는 것, 변수의 첫 상태를 정의하기 어려울 때 사용, null 값을 가질 수 없다
    // : => 변수의 타입을 지정
    // FragmentGardenBinding => fragment_garden.xml
    // 초기값 없이 변수의 타입만 지정, 다른 메서드에서도 접근할 수 있도록
    private lateinit var binding: FragmentGardenBinding

    // GardenPlantingListViewModel 타입의 변수 선언
    private val viewModel: GardenPlantingListViewModel by viewModels()
    // by viewModels() ? Fragment의 생명주기에 따라 ViewModel을 생성한다
    // ViewModelProvider를 통해 GardenPlantingListViewModel의 인스턴스가 생성된다
    // Fragment나 Activity가 재생성될 때마다 동일한 ViewModel 인스턴스를 반환하므로, 데이터를 유지할 수 있다

    // by가 뭘까 ? 다른 객체에게 작업을 맡길 때(위임) 쓰는 키워드
    // 어떻게 ? viewModel이라는 변수가 필요할 때마다 viewModels()가 자동으로 ViewModel을 생성하고 관리해준다

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
        // View Binding을 사용하여 fragment_garden.xml을 뷰 객체로 변환하고 이를 binding 변수에 저장

        val adapter = GardenPlantingAdapter()
        // GardenPlantingAdapter의 인스턴스를 생성

        binding.gardenList.adapter = adapter
        // binding.gardenList(RecyclerView)에 어댑터 연결
        // HomeViewPagerFragment 처럼 binding.gardenList.adapter = GardenPlantingAdapter()로 쓰지 않는 이유는?
        // 어댑터를 참조하거나 추가 작업을 수행하기 어렵기 때문

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        } // add Plant 버튼누르면 plant list page 로 이동

        subscribeUi(adapter, binding)
        // LiveData를 관찰 시작
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
        var updateCount = 0

        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result -> // LiveData가 업데이트될 때마다 호출되는 람다 함수
            Log.e("data", "LiveData update, updateCount is $updateCount")
            updateCount++
            // Fragment의 생명 주기를 감지하여 UI를 업데이트
            // observe => 데이터가 변경될 때마다 observe 블록 내부의 코드가 실행된다
            // viewLifecycleOwner => Fragment가 활성화된 상태일 때만 변경 사항을 감지
            binding.hasPlantings = result.isNotEmpty()
            // isNotEmpty => result가 비어 있지 않으면 true가 return되고
            // hasPlantings 값이 true로 설정됨
            //Log.e("data", "LiveData update, result is $result")
            result.forEachIndexed { index, item ->
                Log.e("data", "Item $index: plantId=${item.plant.plantId}, plantName=${item.plant.name}")
            }
            adapter.submitList(result) {
                // RecyclerView의 어댑터에 새로운 데이터를 전달
                // At this point, the content should be drawn / 이 시점에서 콘텐츠를 그려야 합니다
                activity?.reportFullyDrawn()
                // activity? => 현재 Fragment가 연결된 Activity
                // reportFullyDrawn() => Activity 또는 Fragment의 UI가 완전히 표시되었음을 시스템에 알림
                // 왜 사용하는 걸까? 성능 최적화와 데이터 분석을 위해 권장
            }
        }
    }

    // TODO: convert to data binding if applicable
    // 해당되는 경우 데이터 바인딩으로 변환
    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem = PLANT_LIST_PAGE_INDEX
        // requireActivity() => 현재 Fragment가 연결된 Activity return
        // view_pager id를 가진 ViewPager2를 찾고 현재 페이지를 1로 설정
    }

}
