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

    // https://developer.android.com/guide/components/activities/activity-lifecycle?hl=ko
    // Activity가 생성되면 가장 먼저 호출, 단 한 번만 수행
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lifecycle", "onCreate $this")
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
        Log.d("lifecycle", "setContentView $this")
    }

    // Activity가 화면에 표시되기 직전에 호출, 화면에 진입할 때마다 실행되어야 하는 작업을 이곳에 구현
    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart $this")
    }

    // Activity가 화면에 보여지는 직후에 호출, Activity가 사용자에게 포커스 인
    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume $this")
    }

    // Activity가 화면에 보여지지 않은 직후에 호출, 다른 Activity가 호출되기 전에 실행
    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "onPause $this")
    }

    // Activity가 다른 Activity에 의해 100% 가려질 때 호출
    // 홈 키를 누르는 경우, 다른 액티비티로의 이동이 있는 경우, 이 상태에서 Activity가 호출되면, onRestart() 메소드가 호출
    override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "onStop $this")
    }

    // Activity가 완전히 종료, onStop(), onDestroy() 메소드는 메모리 부족이 발생하면 스킵될 수 있음
    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle", "onDestroy $this")
    }

    // onStop()이 호출된 이후에 다시 기존 Activity로 돌아오는 경우
    // onRestart()가 호출된 이후 이어서 onStart()가 호출
    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "onRestart $this")
    }

}
