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

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.samples.apps.sunflower.adapters.GalleryAdapter
import com.google.samples.apps.sunflower.databinding.FragmentGalleryBinding
import com.google.samples.apps.sunflower.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private val adapter = GalleryAdapter()
    private val args: GalleryFragmentArgs by navArgs()
    private var searchJob: Job? = null
    private val viewModel: GalleryViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("lifecycle", "onAttach $this")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("lifecycle", "onCreate $this")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("lifecycle", "onCreateView $this")
        val binding = FragmentGalleryBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.photoList.adapter = adapter
        search(args.plantName)

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("lifecycle", "onViewCreated $this")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("lifecycle", "onActivityCreated $this")
    }

    override fun onStart() {
        super.onStart()
        Log.e("lifecycle", "onStart $this")
    }

    override fun onResume() {
        super.onResume()
        Log.e("lifecycle", "onResume $this")
    }

    override fun onPause() {
        super.onPause()
        Log.e("lifecycle", "onPause  $this")
    }

    override fun onStop() {
        super.onStop()
        Log.e("lifecycle", "onStop $this")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("lifecycle", "onDestroyView $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("lifecycle", "onDestroy $this")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("lifecycle", "onDetach $this")
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchPictures(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
