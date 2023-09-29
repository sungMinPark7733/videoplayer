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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = listOf(
            ListItem(150.dp, 150.dp, Color.Green, "Push up", "SeCKUmcrWt0"),
            ListItem(150.dp, 150.dp, Color.Green, "Squat", "xqvCmoLULNY"),
            // Add more items with different video IDs
        )

        setContent {
            MyApplicationTheme {
                var openBottomSheet by rememberSaveable { mutableStateOf(false) }
                var selectedItem by remember { mutableStateOf<ListItem?>(null) } // Move the declaration here

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
                                    selectedItem = item // Set the selected item when clicked
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
                    var skipPartiallyExpanded by remember { mutableStateOf(false) }
                    val bottomSheetState = rememberModalBottomSheetState(
                        skipPartiallyExpanded = skipPartiallyExpanded
                    )

                    ModalBottomSheet(
                        onDismissRequest = { openBottomSheet = false },
                        sheetState = bottomSheetState,
                        windowInsets = windowInsets
                    ) {
                        selectedItem?.let { item ->
                            YoutubePlayer(
                                youtubeVideoID = item.videoId, // Pass the video ID of the selected item
                                lifecycleOwnwer = LocalLifecycleOwner.current
                            )
                        }
                    }
                }
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

