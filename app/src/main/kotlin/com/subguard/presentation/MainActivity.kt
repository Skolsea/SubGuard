package com.subguard.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.subguard.domain.repository.PreferenceRepository
import com.subguard.presentation.navigation.SubGuardNavigation
import com.subguard.presentation.theme.SubGuardTheme
import com.subguard.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply saved locale before setting content
        lifecycleScope.launch {
            preferenceRepository.getLanguage().collect { languageCode ->
                LocaleHelper.setLocale(this@MainActivity, languageCode)
            }
        }

        setContent {
            SubGuardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SubGuardNavigation()
                }
            }
        }
    }
}
