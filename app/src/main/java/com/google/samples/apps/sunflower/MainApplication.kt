/*
 * Copyright 2020 Google LLC
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

package com.google.samples.apps.sunflower

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
// Application class 란? 액티비티, 서비스와 같은 다른 기본 구성 요소를 포함하는 안드로이드 앱 내의 기본 클래스
// 어느 컴포넌트(액티비티, 서비스, 인텐트 등)에서나 공유할 수 있는 전역 클래스를 의미
// 어떤 값을 액티비티, 서비스 등 안드로이드 컴포넌트들 사이에서 공유해 사용할 수 있게 해준다
// Application을 상속받은 클래스는 공동으로 관리해야 하는 데이터를 작성하기에 적합
class MainApplication : Application(), Configuration.Provider {

    // https://developer.android.com/develop/background-work/background-tasks/testing/persistent/debug?hl=ko#kotlin
    // 작업자가 너무 자주 실행되거나 전혀 실행되지 않는 경우 어떤 일이 일어나고 있는지 발견하는 데 도움이 되는 디버깅 단계
    // 작업자 인스턴스가 제대로 실행되지 않는 이유를 확인하려면 상세 WorkManager 로그를 확인하는 것이 매우 유용
    override fun getWorkManagerConfiguration(): Configuration =
                Configuration.Builder()
                        .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.DEBUG else android.util.Log.ERROR)
                        .build()
}
