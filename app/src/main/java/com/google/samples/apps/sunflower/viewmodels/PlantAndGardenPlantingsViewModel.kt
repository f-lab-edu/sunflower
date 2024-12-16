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

import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import java.text.SimpleDateFormat
import java.util.Locale

// [PlantAndGardenPlantings 데이터를 UI에 필요한 형식으로 변경]
// UI에 어떻게 데이터를 주겠다는 걸까?
// ViewHolder가 PlantAndGardenPlantingsViewModel 객체를 생성하고 데이터 바인딩을 통해 UI와 연계된다
// RecyclerView의 개별 아이템 데이터를 처리하는 일반 클래스 (ViewModel 상속 받지 않음)

class PlantAndGardenPlantingsViewModel(plantings: PlantAndGardenPlantings) {
    private val plant = checkNotNull(plantings.plant)
    // checkNotNull => null일 경우 IllegalStateException를 발생, null이 아닐 경우 해당 값을 반환

    // PlantAndGardenPlantings의 plant 속성을 가져온다. 정원에 심어진 식물들 중 특정 식물의 정보?
    // RecyclerView와 Adapter는 각각의 ViewHolder에 하나의 식물에 대한 정보만 보여주도록 설계
    // PlantAndGardenPlantings 객체는 한 번에 한 식물의 정보만을 기준으로 데이터가 전달된다
    private val gardenPlanting = plantings.gardenPlantings[0]
    // 리스트는 하나일텐데 왜 첫번째 리스트만?

    val waterDateString: String = dateFormat.format(gardenPlanting.lastWateringDate.time)
    val wateringInterval get() = plant.wateringInterval
    val imageUrl get() = plant.imageUrl
    val plantName get() = plant.name
    val plantDateString: String = dateFormat.format(gardenPlanting.plantDate.time)
    val plantId get() = plant.plantId

    companion object {
        // 모든 인스턴스에서 공유되며, 객체를 생성하지 않아도 사용 가능 (java의 static과 유사)
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }
    // companion object로 쓴 이유는?
    // 리소스 낭비 방지, 날짜 형식이 바뀌어야 하는 경우, companion object의 dateFormat만 수정하면 된다
}
