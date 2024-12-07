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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
        Log.d("lifecycle", "onCreate $this")
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume $this")
    }

    override fun onPause() {
        super.onPause()
        Log.d("lifecycle", "onPause $this")
    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "onStop $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle", "onDestroy $this")
    }

}
