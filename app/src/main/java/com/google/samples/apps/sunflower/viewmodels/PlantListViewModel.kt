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

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.data.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for [PlantListFragment].
 */
@HiltViewModel
class PlantListViewModel @Inject internal constructor
    (plantRepository: PlantRepository, private val savedStateHandle: SavedStateHandle) : ViewModel() {
        // SavedStateHandle => ViewModel의 데이터를 구성 변경이나 프로세스 종료 후에도 안전하게 저장하고 복원할 수 있다 (키-값 형태로 저장)
        // private => ViewModel 내부에서만 SavedStateHandle을 안전하게 사용
        // 해당 viewModel에서만 SavedStateHandle를 사용하는 이유는? growZone값을 저장하고 불러오기 위해서

    private val keyWord: MutableStateFlow<String> =
        MutableStateFlow(savedStateHandle.get(KEYWORD_SAVED_STATE_KEY) ?: NO_KEYWORD)

    private val growZone: MutableStateFlow<Int> =
        MutableStateFlow(savedStateHandle.get(GROW_ZONE_SAVED_STATE_KEY) ?: NO_GROW_ZONE)
    // MutableStateFlow => 상태 변경 시 이를 실시간으로 제공하고, 읽기와 쓰기가 모두 가능하다
    // 초기값이 항상 필요 왜? 항상 현재 상태를 제공 해야 하기 때문에
    // 그럼 그냥 flow랑은 뭐가 다를까? flow는 DB에서 데이터를 한 번 가져오는 작업에 주로 사용한다
    // growZone 값 -1 또는 9

    // flatMapLatest => growZone 값이 변경될 때마다 실행
    // 상위 스트림의 값이 변경되면, 이전 작업을 취소하고 새로운 작업만 실행
    // 상위 스트림 => growZone, 하위 스트림 => if else 문에서 실행 되는 것들
    val plants: LiveData<List<Plant>> = growZone.flatMapLatest { zone ->
        Log.e("filter", "check now zone : $zone")
        if (zone == NO_GROW_ZONE) {
            plantRepository.getPlants()
            // plants 이름을 기준으로 오름차순 정렬한 엔티티 객체 return
        } else {
            plantRepository.getPlantsWithGrowZoneNumber(zone)
            // plants의 growZoneNumber 값이 zone과 동일한 (+ 이름 오름차순) 엔티티 객체 return
        }
    }.asLiveData()

    val searchPlants: LiveData<List<Plant>> = keyWord.flatMapLatest { word ->
        Log.e("filter", "check now zone : $word")
        if (word == NO_KEYWORD) {
            plantRepository.getPlants()
        } else {
            plantRepository.getPlantsWithKeyWord(word)
        }
    }.asLiveData()

    init {

        /**
         * When `growZone` changes, store the new value in `savedStateHandle`.
         * `growZone`이 변경되면 새 값을 `savedStateHandle`에 저장합니다.
         *
         * There are a few ways to write this; all of these are equivalent.
         * 이를 작성하는 방법은 몇 가지가 있으며, 모두 동일합니다.
         * (This info is from https://github.com/android/sunflower/pull/671#pullrequestreview-548900174)
         *
         * 1) A verbose version: 자세한 버전
         *
         *    viewModelScope.launch {
         *        growZone.onEach { newGrowZone ->
         *            savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY, newGrowZone)
         *        }
         *    }.collect()
         *
         *    onEach => growZone 값이 변경될때 실행될 값 정의
         *    collect() => 비동기적으로 정의된 작업 실행
         *
         * 2) A simpler version of 1). Since we're calling `collect`, we can consume
         *    the elements in the `collect`'s lambda block instead of using the `onEach` operator.
         *    This is the version that's used in the live code below.
         *
         *    1)의 더 간단한 버전입니다. `collect`를 호출하기 때문에 `onEach` 연산자를 사용하는 대신
         *    `collect`의 람다 블록에 있는 요소를 소비할 수 있습니다. 이는 아래 라이브 코드에서 사용되는 버전입니다.
         *
         * 3) We can avoid creating a new coroutine using the `launchIn` terminal operator. In this
         *    case, `onEach` is needed because `launchIn` doesn't take a lambda to consume the new
         *    element in the Flow; it takes a `CoroutineScope` that's used to create a coroutine
         *    internally.
         *
         *    `launchIn` 터미널 연산자를 사용하여 새 코루틴을 만드는 것을 피할 수 있습니다.
         *    이 경우 `onEach`가 필요한데, `launchIn`은 Flow에서 새 요소를 소비하기 위해 람다를 사용하지 않기 때문입니다.
         *    내부적으로 코루틴을 만드는 데 사용되는 `CoroutineScope`를 사용합니다.
         *
         *    growZone.onEach { newGrowZone ->
         *        savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY, newGrowZone)
         *    }.launchIn(viewModelScope)
         *    // growZone 값을 실시간으로 모니터링하겠다, 별도의 코루틴을 생성하지 않는다
         */
        // viewModelScope => 상태 관찰 및 데이터 흐름 관리를 ViewModel 생명주기와 연동하여 처리
        viewModelScope.launch { // launch => 새로운 코루틴을 생성하고, 해당 코루틴 내에서 비동기 작업을 수행한다
            // ViewModel이 파괴되면 현재 블록의 코루틴 취소
            growZone.collect { newGrowZone -> // growZone값을 실시간 모니터링하고, 값이 변경되면 set 한다
                savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY, newGrowZone)
            }
        }
        // ViewModel의 생명주기에 맞춰 growZone 상태를 비동기로 관찰하고, 상태 변경을 savedStateHandle에 저장

        viewModelScope.launch {
            keyWord.collect { newKeyWord ->
                savedStateHandle.set(KEYWORD_SAVED_STATE_KEY, newKeyWord)
                Log.e("search", "set newKeyWord in savedStateHandle: $newKeyWord")
            }
        }
    }

    fun setGrowZoneNumber(num: Int) {
        Log.e("filter", "before setGrowZoneNumber : ${growZone.value}")
        growZone.value = num
        Log.e("filter", "after setGrowZoneNumber : ${growZone.value}")
    }

    fun clearGrowZoneNumber() {
        Log.e("filter", "before clearGrowZoneNumber : ${growZone.value}")
        growZone.value = NO_GROW_ZONE
        Log.e("filter", "after clearGrowZoneNumber : ${growZone.value}")
    }

    fun isFiltered() = growZone.value != NO_GROW_ZONE

    fun setKeyWord(word: String) {
        Log.e("search", "before setKeyWord : ${keyWord.value}")
        keyWord.value = word
        Log.e("search", "after setKeyWord : ${keyWord.value}")
    }

    companion object {
        private const val NO_GROW_ZONE = -1
        private const val GROW_ZONE_SAVED_STATE_KEY = "GROW_ZONE_SAVED_STATE_KEY"
        private const val NO_KEYWORD = ""
        private const val KEYWORD_SAVED_STATE_KEY = "KEYWORD_SAVED_STATE_KEY"
    }
}
