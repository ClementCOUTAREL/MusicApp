package com.clementcoutarel.musicapp

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LibraryView(){
    val categories = listOf(
        Item("Playlists",R.drawable.baseline_format_list_bulleted_24),
        Item("Artists",R.drawable.baseline_mic_24),
        Item("Albums",R.drawable.baseline_album_24),
        Item("Songs",R.drawable.baseline_music_note_24),
        Item("Genres",R.drawable.baseline_queue_music_24),
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(categories){
            category ->
           LibItem(category)
        }
    }
}

data class Item(val title: String, @DrawableRes val icon: Int)

@Composable
fun LibItem(category: Item){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(painter = painterResource(id = category.icon),
                contentDescription = category.title,
                modifier = Modifier.padding(end = 8.dp))
            Text(text = category.title)
        }
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null)
    }
    Divider(color = Color.LightGray)
}