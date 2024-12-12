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

package com.google.samples.apps.sunflower.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
// Hilt 라이브러리가 이 ViewModel을 관리
// ViewModel에서 필요한 데이터나 기능을 쉽게 가져오도록 해준다
// ViewModel에 필요한 의존성을 간편하게 주입받을 수 있으며, 생명 주기를 관리하여 메모리 누수를 방지

// @Inject => GardenPlantingRepository를 매개변수로 받아서 사용할 수 있게 한다
// internal => 외부에서 이 ViewModel을 생성하지 못하게 한다
// constructor => 클래스의 생성자, 지우면 에러발생
// 어노테이션이 생성자에 적용될 때는 constructor를 명시해야 한다
class GardenPlantingListViewModel @Inject internal constructor(
    gardenPlantingRepository: GardenPlantingRepository // repository 확인 필요
) : ViewModel() { // GardenPlantingListViewModel이 ViewModel을 상속받음
    // LiveData => 데이터가 변경되면 자동으로 감지하여, Fragment를 업데이트
    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens().asLiveData()
}

//class GardenPlantingListViewModel : ViewModel() {
//    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>>
//
//    @Inject
//    internal constructor(gardenPlantingRepository: GardenPlantingRepository) {
//        plantAndGardenPlantings = gardenPlantingRepository.getPlantedGardens().asLiveData()
//    }
//}
