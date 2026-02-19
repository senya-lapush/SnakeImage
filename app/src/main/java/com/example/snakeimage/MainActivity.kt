package com.example.snakeimage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.snakeimage.ui.theme.SnakeImageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val imageWorker = ImageWorker()

        setContent {
            SnakeImageTheme {
                MainScreen(imageWorker)
            }
        }
    }
}