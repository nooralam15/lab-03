package com.example.listycity3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.ui.tooling.preview.Preview
@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }

    var selectedCity by remember { mutableStateOf<City?>(null) }
    var selectedCityName by remember { mutableStateOf("") }
    var selectedProvinceName by remember { mutableStateOf("") }

    var updatedCityName by remember { mutableStateOf("") }
    var updatedProvinceName by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields
                }
            ) {
                Text("+")
            }

        }

        if (showAddCityFields) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false
                        }
                    }
                ) {
                    Text("Add City")
                }
            }
            Text(
                text = "You have selected: $selectedCityName $selectedProvinceName"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = updatedCityName,
                    onValueChange = { updatedCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = updatedProvinceName,
                    onValueChange = { updatedProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (selectedCityName.isNotBlank() && selectedProvinceName.isNotBlank() && updatedCityName.isNotBlank() && updatedProvinceName.isNotBlank()) {
                            val oldCity = City(
                                name = selectedCityName,
                                province = selectedProvinceName
                            )
                            onUpdateCity(
                                oldCity,
                                City(
                                    name = updatedCityName,
                                    province = updatedProvinceName
                                )
                            )
                            selectedCityName = ""
                            selectedProvinceName = ""
                            updatedCityName = ""
                            updatedProvinceName = ""
                            showAddCityFields = false
                        }
                    }
                ) {
                    Text("Update City")
                }
            }
        }


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                //The following onCityClick parameter inside CityRow is from OpenAI, ChatGPT 5.6 Terra, "in my current code, if i have my clickable row in another composable CityRow function , how do i track what is clicked and send it to my main CityListScreen composable function?", 2026-09-15
                CityRow(city = city,
                    onCityClick = {
                    selectedCity = it
                    selectedCityName = it.name
                    selectedProvinceName = it.province
                })
                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
//The following onCityClick field inside CityRow's declaration is from OpenAI, ChatGPT 5.6 Terra, "in my current code, if i have my clickable row in another composable CityRow function , how do i track what is clicked and send it to my main CityListScreen composable function?", 2026-09-15
fun CityRow(city: City, onCityClick: (City) -> Unit ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickable{
                onCityClick(city)
            }
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            //The following onUpdateCity function parameters is from OpenAI, ChatGPT 5.6 Terra, "I keep getting an error on my Preview Compose on my onUpdatCity function?", 2026-09-15
            onUpdateCity = {_, _ ->}
        )
    }
}