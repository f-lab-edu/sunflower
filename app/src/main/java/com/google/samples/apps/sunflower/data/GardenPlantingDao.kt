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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

// [정원에 심어진 식물에 대한 정보(데이터)를 조회, 추가, 삭제]

/**
 * The Data Access Object for the [GardenPlanting] class.
 * [GardenPlanting] 클래스에 대한 데이터 액세스 객체입니다.
 * GardenPlanting 테이블과 관련된 데이터 작업을 처리
 */
@Dao
// Data Access Object
// 데이터에 접근할 수 있는 메서드를 정의해놓은 인터페이스

// Room 라이브러리에서 데이터베이스와 상호작용하는 메서드를 정의, Room이 해당 인터페이스를 통해 데이터베이스 작업을 처리한다
// Room => 안드로이드에서 SQLite를 더 쉽게 사용할 수 있도록 도와주는 라이브러리
interface GardenPlantingDao {
    // @Query => 데이터베이스 쿼리(SQL 명령어) 정의
    // SELECT * => 모든 데이터 선택
    // FROM garden_plantings => 데이터를 가져올 테이블을 지정
    // garden_plantings 테이블의 모든 데이터 선택
    @Query("SELECT * FROM garden_plantings")
    // [정원에 심은 모든 식물 데이터를 가져올 때]
    fun getGardenPlantings(): Flow<List<GardenPlanting>>
    // <List<GardenPlanting>> => GardenPlanting 객체를 List 형태로 반환 (현재시점만)
    // 왜 List 형태일까 ? 정원에 심은 식물이 여러 개 있을 수 있어서
    // Room은 쿼리를 통해 테이블의 모든 데이터를 반환할 때, 모든 행을 하나의 리스트로 반환한다

    // Flow<List<GardenPlanting>> => GardenPlanting 데이터가 업데이트 될 때마다 Flow로 반환 (비동기 처리)
    // Room이 데이터베이스 변경 사항을 자동으로 감지하고, Flow를 통해 업데이트된 데이터를 계속 제공한다
    // Room에서 반환된 Flow를 ViewModel에서 LiveData로 변환한다


    // SELECT EXISTS => 특정 조건을 만족하는 데이터가 데이터베이스에 존재하는지를 확인 서브쿼리의 결과가 하나라도 존재하면 true, 그렇지 않으면 false
    // SELECT 1 => 서브쿼리에서 단지 데이터가 존재하는지만 확인하기 위해 사용
    // FROM garden_plantings => garden_plantings 테이블에서 데이터 검색
    // WHERE plant_id = :plantId: => plant_id 열의 값이 메서드의 매개변수 plantId와 일치하는 행을 검색
    // LIMIT 1 => 결과의 수 제한
    @Query("SELECT EXISTS(SELECT 1 FROM garden_plantings WHERE plant_id = :plantId LIMIT 1)")
    // [특정 식물이 정원에 심어져있는지 여부 확인]
    fun isPlanted(plantId: String): Flow<Boolean>
    // garden_plantings 테이블 중 매개변수인 plantId에 해당하는 행이 존재하는지 확인

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     *
     * 이 쿼리는 Room에 [Plant] 및 [GardenPlanting] 테이블을 모두 쿼리하고 개체 매핑을 처리하도록 지시합니다.
     *
     * 개체 매핑 처리가 무슨 뜻 ? 테이블 데이터를 앱에서 사용할 수 있는 클래스의 객체로 변환하는 것
     * 그럼 plant 랑 GardenPlanting는 왜 매핑 처리를 하지 않을까? Room은 단일 테이블에 대해서는 자동으로 객체 매핑을 처리한다
     * 두 테이블의 데이터를 함께 가져오고자 할 때는 관계를 정의하고 매핑 처리가 필요하다. 즉, 묶음 역할을 하는 데이터 클래스가 필요하다
     */
    // @Transaction => 여러 테이블 작업을 안전하게 수행하기 위해 사용
    // 만약 하나의 작업이 실패하면, 이전 상태로 롤백하여 데이터의 일관성을 유지
    @Transaction
    // SELECT * FROM plants => plants 테이블의 모든 데이터 선택
    // WHERE id IN (...): plants 테이블의 id가 서브쿼리에서 반환된 값들 중 하나와 일치한다면 선택
    // SELECT DISTINCT(plant_id) FROM garden_plantings => garden_plantings 테이블에서 plant_id 열의 고유한 값 선택
    // DISTINCT => 중복된 값 제거
    // plants의 모든 id와 garden_plantings의 모든 plant_id를 비교해서 동일한 값이 있는 경우 해당 plants 테이블의 모든 정보를 가져온다
    @Query("SELECT * FROM plants WHERE id IN (SELECT DISTINCT(plant_id) FROM garden_plantings)")
    // [garden_plantings에 심어진 식물에 대한 정보를 plants 테이블에서 조회]
    fun getPlantedGardens(): Flow<List<PlantAndGardenPlantings>>
    // Room 라이브러리는 쿼리 결과를 PlantAndGardenPlantings 객체에 담겨서 반환한다
    // PlantAndGardenPlantings는 두 테이블의 데이터를 하나의 객체로 묶는다
    // 그럼 PlantAndGardenPlantings는 개발자가 미리 인지하고 만드는 걸까? 자동으로 생성되는걸끼?
    // 두 테이블의 관계를 처리하기 위해 직접 만들어야 하는 클래스다..


    // @Insert => 데이터베이스에 새로운 레코드를 삽입
    @Insert
    // suspend => 비동기적으로 실행되어 코루틴에서 호출될 수 있다, 다른 스레드에서 실행된다
    // gardenPlanting => 추가할 레코드
    // : Long => 삽입된 데이터의 기본 키 return
    suspend fun insertGardenPlanting(gardenPlanting: GardenPlanting): Long


    // @Delete => 데이터베이스에서 레코드를 삭제
    @Delete
    // gardenPlanting => 삭제할 레코드
    suspend fun deleteGardenPlanting(gardenPlanting: GardenPlanting)
}
