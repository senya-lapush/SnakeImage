package com.example.snakeimage

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.sqrt

class ImageWorker {
    suspend fun loadImagesFromJson(context: Context): List<ImagePart> =
        withContext(Dispatchers.IO) {
            val jsonString =
                context.assets.open("images.json").bufferedReader().use { it.readText() }
            val rootObject = JSONObject(jsonString)

            val jsonArray = rootObject.getJSONArray("items")

            val images = mutableListOf<ImagePart>()

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)

                val base64Image = obj.getString("image")
                val position = obj.getInt("position")

                val decoded = Base64.decode(base64Image, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)

                images.add(ImagePart(image = bitmap, position = position))
            }

            images.sortedBy { it.position }
        }

    fun getOrderedListForSnake(list: List<ImagePart>): List<ImagePart> {
        val n = getN(list.size)

        val new = list.chunked(n).mapIndexed { index, parts ->
            when {
                index % 2 == 0 -> parts
                else -> parts.reversed()
            }
        }

        return new.flatten()
    }

    fun getN(size: Int) = sqrt(size.toDouble()).toInt()
}