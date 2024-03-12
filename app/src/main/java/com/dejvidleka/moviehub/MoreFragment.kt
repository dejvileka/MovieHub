package com.dejvidleka.moviehub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.ContentView
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.semantics.Role.Companion.DropdownList
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.AppPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

//@AndroidEntryPoint
//class MoreFragment : Fragment() {
//
//    private val mainViewModel: MainViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent { ContentView() }
//        }
//    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.regions.collect { result ->
                when (result) {
                    is Result.Success -> {
                        val regionsName = result.data.map { it.english_name + " " + it.iso_3166_1 }
                        Log.d("Regions", result.data.toString())
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            regionsName
                        )
                        val locationAutocomplete = binding.locationAutocomplete
                        locationAutocomplete.setAdapter(adapter)
                        locationAutocomplete.threshold = 1
                        binding.savePreferencesButton.setOnClickListener {
                            val selectedText = binding.locationAutocomplete.text.toString()
                            val regions = regionsName.filter { it.startsWith(selectedText) }
                            if (regions.isNotEmpty()) {
                                val selectedRegionCode =
                                    regions.first().split(" ").last() // Extract ISO code
                                AppPreferences.saveRegionCode(requireContext(), selectedRegionCode)
                            } else {
                                // Handle the case where the region isn't found (e.g., show a message)
                            }
                        }
                    }


                    is Result.Loading -> {}
                    is Result.Error -> {}
                }
            }
        }

    }*/

    /*    @Composable
        fun CountryDropDown() {
            Surface(color = MaterialTheme.colors.onSurface) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Country picker Here", style = MaterialTheme.typography.h4)
                    Text(text = "John", style = MaterialTheme.typography.h4)
                    Button(
                        onClick = { Toast.makeText(context, "Nice", Toast.LENGTH_SHORT).show() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(text = "Change country")
                    }

                }

            }
        }*/
    @Composable
    fun ContentView() {

        var list: List<String> = listOf()

        var selectedIndex by rememberSaveable {
            mutableStateOf(0)
        }
        var buttonModifier = Modifier.width(100.dp)
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            DropDownList(
                itemList = list,
                selectedIndex = selectedIndex,
                modifier = buttonModifier,
                onItemClick = { selectedIndex = it })

            Text(
                text = "You have chosen ${list[selectedIndex]}",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray),
            )
        }
    }


    @Composable
    fun DropDownList(
        itemList: List<String>,
        selectedIndex: Int,
        modifier: Modifier,
        onItemClick: (Int) -> Unit
    ) {
        var showDropdown by rememberSaveable { mutableStateOf(false) }

        Box(
            modifier = modifier
                .background(Color.LightGray)
                .height(80.dp)
                .width(50.dp)
                .clickable { showDropdown = true }, contentAlignment = Alignment.Center
        ) {
            Text(text = itemList[selectedIndex], modifier = Modifier.padding(3.dp))

        }
        val scrollState = rememberScrollState()

        Box {
            if (showDropdown) {
                Popup(
                    alignment = Alignment.TopCenter,
                    properties = PopupProperties(excludeFromSystemGesture = true),
                    onDismissRequest = { showDropdown = false }
                ) {
                    Column(
                        modifier = modifier
                            .heightIn(max = 90.dp)
                            .verticalScroll(state = scrollState)
                            .border(width = 1.dp, color = Color.Gray),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemList.onEachIndexed { index, item ->
                            if (index != 0) {
                                Divider(thickness = 1.dp, color = Color.LightGray)
                            }
                            Box(
                                modifier = Modifier
                                    .background(color = Color.LightGray)
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemClick(index)
                                        showDropdown = !showDropdown
                                    }, contentAlignment = Alignment.Center
                            ) { Text(text = item) }
                        }

                    }

                }
            }

        }
    }


