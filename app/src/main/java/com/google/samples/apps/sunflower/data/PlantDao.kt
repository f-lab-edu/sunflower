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
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface PlantDao {
    // ORDER BY => plants 테이블의 name 값을 기준으로 오름차순으로 정렬
    @Query("SELECT * FROM plants ORDER BY name")
    fun getPlants(): Flow<List<Plant>>

    // WHERE => 테이블의 growZoneNumber와 매개변수로 받은 값이 동일한지
    @Query("SELECT * FROM plants WHERE growZoneNumber = :growZoneNumber ORDER BY name")
    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int): Flow<List<Plant>>

    // WHERE name LIKE :keyWord => name에서 keyWord로 시작하는 값 선택
    // || '%' => keyWord 뒤에 모든 문자열이 올 수 있다
    @Query("SELECT * FROM plants WHERE name LIKE :keyWord || '%'")
    fun getPlantsWithKeyWord(keyWord: String) : Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlant(plantId: String): Flow<Plant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Plant>)
}
