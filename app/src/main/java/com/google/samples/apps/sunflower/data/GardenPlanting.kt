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
 */

// 데이터베이스의 테이블 정의
// 저장하고 싶은 속성의 변수 이름과 타입을 정해준다
@Entity(
    tableName = "garden_plantings", // 이 테이블의 이름
    foreignKeys = [ // 다른 테이블과의 관계 설정
        ForeignKey( // Plant 확인필요
            entity = Plant::class,
            parentColumns = ["id"],
            childColumns = ["plant_id"]
        )
    ],
    indices = [Index("plant_id")]
)
data class GardenPlanting(
    @ColumnInfo(name = "plant_id") val plantId: String,

    /**
     * Indicates when the [Plant] was planted. Used for showing notification when it's time
     * to harvest the plant.
     */
    @ColumnInfo(name = "plant_date") val plantDate: Calendar = Calendar.getInstance(),

    /**
     * Indicates when the [Plant] was last watered. Used for showing notification when it's
     * time to water the plant.
     */
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gardenPlantingId: Long = 0
}
