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

// [DB 안에 들어가는 식물 데이터 테이블]

// @Entity => Room 데이터베이스의 테이블과 매핑
@Entity(tableName = "plants") // "plants"라는 데이터베이스 테이블 정의
data class Plant( // data class란 ? 데이터 저장을 위한 클래스를 간결하게 정의
    // @PrimaryKey => plantId라는 속성이 기본 키다, 같은 값을 가진 항목이 없도록 보장한다
    // @ColumnInfo(name = "id") => plantId라는 속성을 DB에 "id"로 저장, 기본적으로 속성 이름과 동일하게 저장하지만 다른 이름으로 저장하고 싶을 때 사용
    @PrimaryKey @ColumnInfo(name = "id") val plantId: String,

    // 각각의 속성들
    val name: String,
    val description: String,
    val growZoneNumber: Int,
    val wateringInterval: Int = 7, // how often the plant should be watered, in days
    val imageUrl: String = ""
) {
    // 메소드를 왜 data class에 만들까 ? data와 data를 다루는 로직을 한곳에 묶어두기 위해서
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
