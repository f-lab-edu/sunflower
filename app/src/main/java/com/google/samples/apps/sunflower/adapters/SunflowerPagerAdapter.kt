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

package com.google.samples.apps.sunflower.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.samples.apps.sunflower.GardenFragment
import com.google.samples.apps.sunflower.PlantListFragment

const val MY_GARDEN_PAGE_INDEX = 0
const val PLANT_LIST_PAGE_INDEX = 1

// ViewPager2는 페이지를 fragment로 구성할 때 FragmentStateAdapter를 사용해야 한다
// SunflowerPagerAdapter class가 FragmentStateAdapter를 상속받음
// 부모 Fragment를 생성자로 받아, ViewPager2의 페이지를 부모 Fragment의 생명 주기에 맞게 관리
class SunflowerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    // mapOf => 변경이 불가능한 Immutable Map 생성
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { GardenFragment() },
        // 키 0에 값으로 GardenFragment를 생성하는 람다 함수를 저장
        PLANT_LIST_PAGE_INDEX to { PlantListFragment() }
        // 키 1에 값으로 PlantListFragment를 생성하는 람다 함수를 저장
    )

    // map의 크기, fragment의 개수 return
    override fun getItemCount() = tabFragmentsCreators.size

    // ViewPager2에서 각 페이지에 표시할 Fragment를 생성
    override fun createFragment(position: Int): Fragment {
        // ?.invoke() => null이 아닌 경우에만 람다를 호출하고, null이라면 호출하지 않고 null을 return
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
        // ?: => 값이 null이면 throw처리
    }
}
