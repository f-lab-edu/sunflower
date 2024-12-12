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
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

@Entity(tableName = "plants") // plants라는 데이터베이스 테이블 정의
data class Plant(
    // plantId라는 속성이 기본 키다, 같은 값을 가진 항목이 없도록 보장
    // plantId라는 속성을 DB에 "id"로 저장
    // 각각의 속성 저장
    @PrimaryKey @ColumnInfo(name = "id") val plantId: String,
    val name: String,
    val description: String,
    val growZoneNumber: Int,
    val wateringInterval: Int = 7, // how often the plant should be watered, in days
    val imageUrl: String = ""
) {

    /**
     * Determines if the plant should be watered.  Returns true if [since]'s date > date of last
     * watering + watering Interval; false otherwise.
     *
     * 식물에 물을 줘야 하는지 판단합니다.
     * [since]의 날짜가 마지막 물 준 날짜 + 물 주기 간격을 초과하면 `true`를 반환하고,
     * 그렇지 않으면 `false`를 반환합니다.
     */
    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
        since > lastWateringDate.apply {
            // apply => 이 블록 안에서 lastWateringDate 객체를 수정
            add(DAY_OF_YEAR, wateringInterval)
            // add => lastWateringDate에 wateringInterval(7일)을 더해라
        }

    // 객체를 출력할 때 기본적으로 제공되는 toString() 메서드 대신 name을 반환
    override fun toString() = name
}
