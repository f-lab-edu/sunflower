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

package com.google.samples.apps.sunflower

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.google.samples.apps.sunflower.databinding.ActivityGardenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
// Hilt라는 도구의 일부, Android 앱에서 필요한 객체(의존성)를 쉽게 가져올 수 있게 해주는 애너테이션
// 필요한 객체를 @Inject 애너테이션을 사용하여 선언
class GardenActivity : AppCompatActivity() {

    // [앱 첫 실행 3] HomeViewPagerFragment의 onCreate 이후 실행
    // [설정에서 언어 변경 시 12]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
        Log.d("lifecycle", "onCreate $this")
    }

    // [앱 첫 실행 8] HomeViewPagerFragment의 onStart -> onStart
    // [메인화면에서 home key로 앱을 bg로 보냈다가 다시 fg로 3] GardenFragment onStart -> onStart
    // [전원버튼 눌러서 화면 껏다가 다시 킬 때 3] GardenFragment onStart -> onStart
    // [메인화면에서 back키 이후 재실행 3] GardenFragment onStart -> onStart
    // [설정에서 언어 변경 시 21]
    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart $this")
    }

    // [앱 첫 실행 9] onStart -> onResume
    // [메인화면에서 home key로 앱을 bg로 보냈다가 다시 fg로 4] onStart -> onResume
    // [전원버튼 눌러서 화면 껏다가 다시 킬 때 4] onStart -> onResume
    // [메인화면에서 back키 이후 재실행 4] onStart -> onResume
    // [설정에서 언어 변경 시 22]
    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume $this")
    }

    // [메인화면에서 홈 키로 앱을 bg로 3] HomeViewPagerFragment의 onPause -> onPause
    // [전원버튼 눌러서 화면 끌 때 3] HomeViewPagerFragment의 onPause -> onPause
    // [메인화면에서 back 키 3] HomeViewPagerFragment의 onPause -> onPause
    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 3] HomeViewPagerFragment의 onPause -> onPause
    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "onPause $this")
    }

    // [메인화면에서 홈 키로 앱을 bg로 6] HomeViewPagerFragment의 onStop -> onStop /
    // [전원버튼 눌러서 화면 끌 때 6] HomeViewPagerFragment의 onStop -> onStop /
    // [메인화면에서 back 키 6] HomeViewPagerFragment의 onPause -> onPause /
    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 6] HomeViewPagerFragment의 onStop -> onStop
    override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "onStop $this")
    }

    // [메인화면 실행중 최근 앱 목록에서 앱 날릴 때 13] HomeViewPagerFragment의 onDetach -> onDestroy /
    // [설정에서 언어 변경 시 7]
    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle", "onDestroy $this")
    }

}
