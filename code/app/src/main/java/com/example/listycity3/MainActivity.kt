package com.example.listycity3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.listycity3.ui.theme.ListyCity3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCity3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it) },
                        //The following onUpdateCity function parameters is from OpenAI, ChatGPT 5.6 Terra, "in my MainActivity class, i am facing an argument mismatch error on my onUpdateCity function?", 2026-09-15
                        onUpdateCity = { oldCity, newCity -> cityRepository.updateCity(oldCity, newCity) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}