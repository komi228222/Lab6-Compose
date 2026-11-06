package com.example.artspaceapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp(artworkViewModel: ArtworkViewModel = viewModel()) {
    val uiState by artworkViewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val isWideScreen = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isWideScreen) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ArtworkImage(index = uiState.currentArtworkIndex, modifier = Modifier.weight(1f))
                ArtworkText(index = uiState.currentArtworkIndex, modifier = Modifier.weight(1f))
            }
        } else {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ArtworkImage(index = uiState.currentArtworkIndex, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                ArtworkText(index = uiState.currentArtworkIndex)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { artworkViewModel.previousArtwork() },
                enabled = uiState.currentArtworkIndex > 1
            ) { Text(stringResource(R.string.previous)) }

            Button(
                onClick = { artworkViewModel.nextArtwork() },
                enabled = uiState.currentArtworkIndex < 3
            ) { Text(stringResource(R.string.next)) }
        }
    }
}

@Composable
fun ArtworkImage(index: Int, modifier: Modifier = Modifier) {
    val imageResource = when (index) {
        1 -> R.drawable.art1
        2 -> R.drawable.art2
        else -> R.drawable.art3
    }
    Image(
        painter = painterResource(imageResource),
        contentDescription = null,
        modifier = modifier.clip(RoundedCornerShape(8.dp)).fillMaxWidth(),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun ArtworkText(index: Int, modifier: Modifier = Modifier) {
    val title = when (index) {
        1 -> stringResource(R.string.art1_title)
        2 -> stringResource(R.string.art2_title)
        else -> stringResource(R.string.art3_title)
    }
    val artist = when (index) {
        1 -> stringResource(R.string.art1_artist)
        2 -> stringResource(R.string.art2_artist)
        else -> stringResource(R.string.art3_artist)
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Text(text = artist, style = MaterialTheme.typography.bodyMedium)
    }
}