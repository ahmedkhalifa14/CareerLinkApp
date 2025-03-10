package com.ahmedkhalifa.careerlinkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ahmedkhalifa.careerlinkapp.graphs.RootNavigationGraph
import com.ahmedkhalifa.careerlinkapp.ui.theme.CareerLinkAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CareerLinkAppTheme {
             RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CareerLinkAppTheme {
    }
}