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

import androidx.room.Embedded
import androidx.room.Relation

// [Plant와 GardenPlanting 테이블 간의 관계를 정의하고 데이터를 묶음]
// [정원에 심은 식물에 대한 종합 정보]
// DB 테이블은 아닌 걸까 ? Room에서 제공하는 @Relation 기능을 활용한 데이터 묶음 클래스다
// 실제 데이터는 여전히 Plant와 GardenPlanting 테이블에 각각 저장된다

/**
 * This class captures the relationship between a [Plant] and a user's [GardenPlanting], which is
 * used by Room to fetch the related entities.
 *
 * 이 클래스는 [Plant]과 사용자의 [GardenPlanting] 간의 관계를 캡처하며, Room에서 관련 엔터티를 가져오는 데 사용됩니다.
 *
 * 엔티티를 가져온다는 무슨뜻일까 ? 두 테이블 간의 데이터를 관계에 따라 자동으로 묶어서 앱에서 사용할 수 있도록 객체로 변환한다
 * 엔티티란 ? 데이터베이스 테이블과 매핑된 클래스
 */
data class PlantAndGardenPlantings(
    // @Embedded => Room이 Plant 객체의 모든 속성을 직접 포함
    @Embedded
    val plant: Plant, // Plant 테이블의 데이터를 plant에 저장

    // @Relation => 테이블 간의 관계 정의
    // 어떻게 parent를 판단 ? @Embedded로 포함된 객체를 기준으로 부모 테이블을 판단한다
    // parentColumn => Plant 테이블의 기본 키 "id"
    // entityColumn => GardenPlanting 테이블의 외래 키 "plant_id"
    @Relation(parentColumn = "id", entityColumn = "plant_id")
    val gardenPlantings: List<GardenPlanting> = emptyList()
    // List<GardenPlanting> => GardenPlanting 객체를 담는 리스트 (GardenPlanting 테이블의 한 행, 레코드)
    // emptyList() => pval은 읽기 전용이기 때문에 초기화가 반드시 필요
    // [Plant 테이블의 한 행과 GardenPlanting 테이블의 한 행을 묶어서, 하나의 객체로 표현]
)

// Plant 테이블의 한 행과 GardenPlanting 테이블의 한 행을 묶어서, 하나의 행으로 표현하는게 아닌가?
// 두 테이블은 여전히 독립적이다. 데이터베이스에는 하나의 레코드로 합쳐지는 일이 없다

// 이미 GardenPlanting class에서 설정을 했는데 또 하는 이유는 ?
// ForeignKey는 데이터베이스 레벨에서 부모-자식 관계를 설정, 데이터베이스 무결성을 위해
// @Relation는 앱 코드에서 테이블 간 관계를 정의, 자동 매핑과 객체 반환을 위해
