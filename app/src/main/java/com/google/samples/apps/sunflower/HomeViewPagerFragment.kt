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
// 가장먼저 실행 되는 ViewPager2와 TabLayout을 포함하는 Fragment
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
        // XML에 정의된 Resource를 View 객체로 반환
        inflater: LayoutInflater,
        // 해당 Fragment가 들어갈 컨테이너 (부모 ViewGroup에 넣어서 화면을 구성)
        container: ViewGroup?,
        // Fragment가 이전 상태에서 복원될 때, 저장된 데이터 제공
        savedInstanceState: Bundle?
    ): View { // return 타입
        Log.e("lifecycle", "onCreateView $this")
        // View Binding : XML 파일 이름에 따라 자동으로 클래스가 생성, findViewById 대체
        // View Binding의 inflate()를 사용하여 뷰를 초기화
        // View Binding 미사용시
        // val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        // 탭을 표시하는 UI 구성 요소
        val tabLayout = binding.tabs
        // viewPager2 스와이프할 수 있는 형식으로 뷰 또는 프래그먼트를 표시
        val viewPager = binding.viewPager

        // ViewPager의 어댑터 정의, 페이지 전환 시 어떤 Fragment를 보여줄지를 정의
        viewPager.adapter = SunflowerPagerAdapter(this)

        // Set the icon and text for each tab
        // TabLayout과 ViewPager2를 연결해주는 class
        // 사용자가 탭을 클릭할 때 해당 페이지로 쉽게 전환
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position)) // 현재 탭에 아이콘 설정
            tab.text = getTabTitle(position) // 현재 탭에 제목 설정
        }.attach() //ViewPager2의 페이지 수에 따라 탭을 생성
        // TabLayout과 ViewPager2 간의 연결을 활성화
        // 탭을 클릭하거나 페이지를 스와이프할 때 서로 상호작용

        // as => activity를 AppCompatActivity 타입으로 변환
        // binding.toolbar를 ActionBar로 사용
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        // View Binding으로 생성된 뷰의 루트 레이아웃을 반환
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

    private fun getTabIcon(position: Int): Int { // 왜 int일까 => 정수형 리소스 ID가 return
        return when (position) { // java의 switch문과 유사
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector
            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? { // return 값이 String이거나 null일 수 있다
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title)
            PLANT_LIST_PAGE_INDEX -> getString(R.string.plant_list_title)
            else -> null
        }
    }

}
