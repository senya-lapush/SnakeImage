package com.example.snakeimage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(imageWorker: ImageWorker) {
    val context = LocalContext.current
    var images by remember { mutableStateOf<List<ImagePart>>(listOf()) }
    var size by remember { mutableIntStateOf(1) }

    LaunchedEffect(true) {
        val list = imageWorker.loadImagesFromJson(context)

        images = imageWorker.getOrderedListForSnake(list)
        size = imageWorker.getN(list.size)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(size),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp)
    ) {
        items(images) { item ->
            Image(
                bitmap = item.image.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}