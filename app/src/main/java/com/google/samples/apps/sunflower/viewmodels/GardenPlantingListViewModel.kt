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

// [GardenPlantingRepository를 통해 정원에 심은 식물들 정보를 가져와서 LiveData로 변환한다]
// 변환해서 UI에 어떻게 전달, 반영한다는 걸까? LiveData의 데이터가 변경되면 Observer가 호출된다

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

// ViewModel 이란? Activity나 Fragment가 Destroy 또는 구성변경에도 데이터를 유지하도록 도와준다
// 직접 데이터를 가져오거나 저장하지 않고, Repository를 통해 데이터 작업을 요청한다.

// @Inject => GardenPlantingRepository를 매개변수로 받아서 사용할 수 있게 한다
// internal => 외부에서 이 ViewModel을 생성하지 못하게 한다
// constructor => 클래스의 생성자, 지우면 에러발생
// 어노테이션이 생성자에 적용될 때는 constructor를 명시해야 한다
// Hilt가 GardenPlantingRepository 객체를 생성하고 ViewModel에 전달
class GardenPlantingListViewModel @Inject internal constructor(gardenPlantingRepository: GardenPlantingRepository) : ViewModel() {
    // visibility modifier(private, internal 같은)가 class 앞에 붙은거랑, constructor 앞에 붙은 거랑은 다릅니다. 어떻게 다를까요?
    // 생성자에 internal을 붙이면 해당 클래스의 인스턴스를 같은 모듈 내에서만 생성 가능하다
    // 외부 모듈에서 ViewModel을 직접 생성할 필요가 없고, Hilt(ViewModelProvider)에 의한 생성만 허용한다
    // 클래스 앞에 제어자는 해당 클래스의 접근 권한을 부여하는 거고 생성자 앞에 제어자는 인스턴스 생성 권한을 부여하는 것이다

    // private로 선언하지 않고 internal로 한 이유는?
    // ViewModel은 주로 같은 모듈 내의 Activity나 Fragment에서 사용된다
    // 같은 모듈 내 테스트 코드에서는 접근 가능해야 한다
    // GardenPlantingListViewModel이 ViewModel을 상속받음

    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
        // PlantAndGardenPlantings 객체들을 List 형태로 반환

        // LiveData란 뭘까? Observer 클래스
        // Activity, Fragment와 같은 UI 컴포넌트의 생명주기를 인식해서 UI가 onStop, onDestroy일 때는 관찰자에게 데이터를 전달하지 않는다
        // 데이터가 변경되면 자동으로 Observer에게 알림
        // UI가 재생성되어도 마지막 상태의 데이터를 보존한다

        // 왜 이번엔 Flow가 아니라 LiveData를 사용하는 걸까?
        // Flow는 UI 생명주기 관리 기능이 없으므로, 데이터를 관찰하는 UI와 직접 연결하기에는 부적합하다

        gardenPlantingRepository.getPlantedGardens().asLiveData()
        // asLiveData() 란? Flow로 반환되는 데이터를 LiveData로 변환한다
        // 따라서 UI 생명주기를 자동으로 인식하고 데이터를 안전하게 전달한다

        // Flow란 비동기라고 했는데 코루틴이랑 연관이 있나? Flow는 코루틴 내부에서 실행된다
        // 그렇다면 개발자가 관여해야할 부분이 있을까? Room과 결합된 Flow는 DB 작업과 변경 사항 감지를 자동화한다
}

//class GardenPlantingListViewModel : ViewModel() {
//    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>>
//
//    @Inject
//    internal constructor(gardenPlantingRepository: GardenPlantingRepository) {
//        plantAndGardenPlantings = gardenPlantingRepository.getPlantedGardens().asLiveData()
//    }
//}
