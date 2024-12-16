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

package com.google.samples.apps.sunflower.data

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

// [GardenPlanting 테이블과 관련된 데이터 추가, 삭제, 조회]

@Singleton
// 단 하나의 인스턴스만 생성되며, 여러 컴포넌트에서 재사용 가능
// 왜 여기서 @Singleton을 써야할까 ?  Hilt가 GardenPlantingRepository의 인스턴스를 앱의 전체 수명 동안 한 번만 생성한다
// 여러 곳에서 재사용한다. 즉, 데이터 일관성을 유지하고, 리소스를 효율적으로 사용 할 수 있다

// 왜 Repository만? ViewModel이 데이터를 요청할 때 항상 사용되고, 여러 ViewModel에서 공유된다
// DB의 연결은 비용이 크기 때문에, 매번 새로 생성하기보다 한 번 생성하여 재사용하는 것이 리소스를 효율적으로 사용할 수 있다

// Repository란 ? ViewModel과 DB에서 데이터를 중개한다, 데이터를 변환하거나 필요한 로직을 처리하여 ViewModel에 제공한다
// Repository를 사용하면 ViewModel은 데이터 소스의 세부 구현에 의존하지 않게 된다

// Hilt가 GardenPlantingDao 객체를 생성하고, GardenPlantingRepository에 전달한다
class GardenPlantingRepository @Inject constructor(
    // internal를 쓰지 않고 private를 쓴 이유는 ?
    // private으로 설정하면 DAO를 오직 Repository 내부에서만 사용할 수 있게 제한되지만
    // internal을 사용하면 같은 모듈 내 다른 클래스에서도 gardenPlantingDao에 접근 가능하다
    // Repository를 거치지 않고 ViewModel에서 DAO를 직접 호출하는 코드가 생길 수 있다

    // GardenPlantingRepository의 생성자에는 internal을 쓰지 않은 이유는?
    // 여러 ViewModel이나 다른 계층에서 공통적으로 사용될수 있기 때문에
    private val gardenPlantingDao: GardenPlantingDao
) {
    // 인터페이스 구현은? 어떻게 interface 메소드를 아래와 같이 사용하는 걸까?
    // Room은 컴파일 시점에 이 인터페이스의 구현 클래스를 자동으로 생성한다
    // 즉, DAO의 메서드를 호출하기만 하면 되며, 그 내부에서 실행되는 SQL 작업은 Room이 처리한다
    // @Insert 메서드가 호출될 때 DB에 실제 SQL INSERT 작업 수행

    // 왜 Dao에 suspend로 정의되어 있는 걸까?
    // Room은 DB 작업할 때, Main 스레드에서 실행하면 앱이 멈추거나 성능 문제가 발생할 수 있다
    // suspend 메서드를 통해 자동으로 I/O 스레드를 사용하여 작업을 처리한다
    // I/O 스레드란 ? 입출력 작업을 처리하기 위해 사용하는 별도의 스레드
    // 직접 스레드를 만들어야 하는건가? Room은 Dispatcher.IO를 내부적으로 사용한다
    // Dispatcher.IO란? 코루틴에서 제공하는 비동기 I/O 작업을 위한 코루틴 디스패처
    // 파일 읽기/쓰기, 네트워크 요청, 데이터베이스 작업 등 입출력(I/O) 관련 작업을 처리할 때 사용한다

    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        // plantId를 사용하여 새로운 GardenPlanting 객체 생성
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
        // DB에서 gardenPlantingDao를 통해 GardenPlanting 테이블에 새로운 데이터 삽입

        // 어떻게 plantId 만으로 테이블에 새로운 데이터를 삽입한다는 걸까?
        // 종합해보면 plantid 만으로 GardenPlanting 테이블에 메타데이터가 채워지고, PlantAndGardenPlantings 엔티티를 통해서 plant 정보를 채운다?
        // 외래키인 plantId를 기준으로 두 테이블을 join
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
        // DB에서 gardenPlantingDao를 통해 GardenPlanting 테이블에 매개변수로 들어온 데이터를 삭제
    }

    fun isPlanted(plantId: String) = gardenPlantingDao.isPlanted(plantId)
    // 매개변수로 들어온 plantId가 GardenPlanting 테이블에 존재하는지 boolean 값 return
    // 정원에 해당 식물이 심어져있는지 확인

//    fun isPlanted(plantId: String): Flow<Boolean> {
//        return gardenPlantingDao.isPlanted(plantId)
//    }

    //fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()
    // 정원에 심어진 식물 정보들 return

    fun getPlantedGardens(): Flow<List<PlantAndGardenPlantings>> {
        Log.e("data", "getPlantedGardens")
        return gardenPlantingDao.getPlantedGardens()
    }
}
