package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = listOf(
            ListItem(150.dp, 150.dp, Color.Green, "Push up", "SeCKUmcrWt0"),
            ListItem(150.dp, 150.dp, Color.Green, "Squat", "xqvCmoLULNY"),
            // Add more items with different video IDs
        )

        setContent {
            MyApplicationTheme {
                MainScreen(items = items)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(items: List<ListItem>) {
    var openBottomSheet by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<ListItem?>(null) }

    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items) { item ->
            Box(
                modifier = Modifier
                    .height(item.height)
                    .width(item.width)
                    .clip(RoundedCornerShape(10.dp))
                    .background(item.color)
                    .clickable {
                        openBottomSheet = !openBottomSheet
                        selectedItem = item
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.name,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    if (openBottomSheet) {
        val windowInsets = if (openBottomSheet)
            WindowInsets(0) else BottomSheetDefaults.windowInsets
        val skipPartiallyExpanded by remember { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded
        )

        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            selectedItem?.let { item ->
                YoutubePlayer(
                    youtubeVideoID = item.videoId,
                    lifecycleOwnwer = LocalLifecycleOwner.current
                )
            }
        }
    }
}

data class ListItem(
    val height: Dp,
    val width: Dp,
    val color: Color,
    val name: String,
    val videoId: String
)
