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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

// [DB 안에 들어가는 식물을 정원에 심은 날과 마지막으로 물을 준 날에 대한 정보만 저장한 테이블]
// [식물의 상세 정보는 연결된 Plant 테이블에서 가져온다]

/**
 * [GardenPlanting] represents when a user adds a [Plant] to their garden, with useful metadata.
 * Properties such as [lastWateringDate] are used for notifications (such as when to water the
 * plant).
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 *
 * [GardenPlanting]은 사용자가 [Plant]를 자신의 정원에 추가했을 때의 정보를 나타내며, 유용한 메타데이터를 포함합니다.
 * 예를 들어, [lastWateringDate]와 같은 속성은 식물에 물을 줘야 하는 시기 등의 알림에 사용됩니다.
 *
 * 열 정보를 선언하면, 변수 이름을 변경하더라도 데이터베이스 마이그레이션을 구현하지 않아도 됩니다.
 * 이는 열 이름이 변경되지 않기 때문입니다.
 *
 * 메타데이터란 ? 데이터에 대한 추가 정보
 * 이 클래스에서는 plantDate(식물이 심어진 시점), lastWateringDate(마지막으로 물을 준 시점) 이다
 *
 * 열 정보를 선언한다는게 무슨 뜻 ? @ColumnInfo DB의 열(속성) 이름을 명시, 고정
 * 데이터베이스 마이그레이션이 무슨 뜻 ? 데이터 구조가 변경될 때 기존 데이터를 보존하며 새로운 구조로 전환하는 작업
 * @ColumnInfo 사용하지 않았을 때 plantId를 id로 변수명을 바꾸면, DB에서는 새로운 열 "id"로 인식한다
 * 기존 데이터와 호환되지 않아 데이터베이스 마이그레이션을 구현해야 한다.
 * @ColumnInfo를 사용하면 plantId 속성의 이름이 바뀌더라도, 데이터베이스 열 이름은 여전히 "id"로 유지된다
 */

// 데이터베이스의 테이블 정의
// 저장하고 싶은 속성의 변수 이름과 타입을 정해준다
@Entity(
    tableName = "garden_plantings", // 이 테이블의 이름
    foreignKeys = [ // 다른 테이블과의 관계 설정
        ForeignKey(
            entity = Plant::class, // 참조할 Parent 테이블
            parentColumns = ["id"], // Parent 테이블의 Primary key
            childColumns = ["plant_id"] // Child 테이블의 Foreign key
        ) // plant_id가 Plant 테이블의 id를 참조한다
    ],
    // indices => 특정 열에 인덱스를 설정하여 데이터 검색 성능을 향상
    indices = [Index("plant_id")] // plant_id 열에 인덱스를 생성
    // 필수로 사용해야 하는 걸까 ? 데이터 삽입/삭제가 많고, 조회가 적다면 굳이 사용할 필요가 없다
)
data class GardenPlanting(
    // plantId 속성을 "plant_id"라는 이름으로 저장
    @ColumnInfo(name = "plant_id") val plantId: String,

    /**
     * Indicates when the [Plant] was planted. Used for showing notification when it's time
     * to harvest the plant.
     *
     * [식물]이 심어진 시점을 나타냅니다. 식물을 수확할 때가 되었을 때 알림을 표시하는 데 사용됩니다.
     */
    // plantDate 속성을 "plant_date"라는 이름으로 저장
    @ColumnInfo(name = "plant_date")
    val plantDate: Calendar = Calendar.getInstance(),
    // Calendar.getInstance() => 현재 시스템의 날짜와 시간 정보를 담고 있는 Calendar 객체 생성

    /**
     * Indicates when the [Plant] was last watered. Used for showing notification when it's
     * time to water the plant.
     *
     * [식물]에 마지막으로 물을 준 시간을 나타냅니다. 식물에 물을 줄 때가 되었을 때 알림을 표시하는 데 사용됩니다.
     */
    // lastWateringDate 속성을 "last_watering_date"라는 이름으로 저장
    @ColumnInfo(name = "last_watering_date") val lastWateringDate: Calendar = Calendar.getInstance()
) {
    // gardenPlantingId라는 속성이 기본 키다, 같은 값을 가진 항목이 없도록 보장한다
    // (autoGenerate = true) => DB가 자동으로 이 값을 생성한다
    @PrimaryKey(autoGenerate = true)
    // gardenPlantingId 속성을 "id"라는 이름으로 저장
    @ColumnInfo(name = "id") var gardenPlantingId: Long = 0
}
