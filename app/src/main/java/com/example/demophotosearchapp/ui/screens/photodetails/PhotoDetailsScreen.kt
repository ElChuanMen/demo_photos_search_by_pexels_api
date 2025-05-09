/*
 * *
 *  * Created by ElChuanmen on 5/8/25, 10:57 AM
 *  * Telegram : elchuanmen
 *  * Phone :0949514503-0773209008
 *  * Mail :doanvanquang146@gmail.com
 *
 */

package com.example.demophotosearchapp.ui.screens.photodetails

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.example.demophotosearchapp.R
import com.example.demophotosearchapp.data.model.Photo
import com.example.demophotosearchapp.ui.screens.home.HomeViewModel
import com.example.demophotosearchapp.ui.screens.home.MainContent

/**
 * Created by ElChuanmen on 6/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PhotoDetailsScreens(homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onNavigateTo: (String) -> Unit,
    onBackStack: () -> Unit, sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope, photo: Photo,  maxScale: Float = 4f,
    minScale: Float = 1f
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    with(sharedTransitionScope) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        Box(
            modifier = modifier
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        scale = (scale * zoom).coerceIn(minScale, maxScale)
                        if (scale > 1f) {
                            offsetX += pan.x
                            offsetY += pan.y
                        } else {
                            offsetX = 0f
                            offsetY = 0f
                        }
                    }
                }
        ) {
                AsyncImage(
                    model = photo.src.large,
                    placeholder = ColorPainter(Color.LightGray),
                    contentDescription = "Photo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.graphicsLayer(
                        scaleX = scale.coerceIn(1f, 5f),
                        scaleY = scale.coerceIn(1f, 5f),
                        translationX = offset.x,
                        translationY = offset.y
                    )
                        .fillMaxSize()
                        .aspectRatio(photo.width.toFloat() / photo.height.toFloat())
                       .sharedElement(
                            rememberSharedContentState( key = photo.id),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
            }


            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back Button",
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(
                        top = dimensionResource(R.dimen.size_10),
                        start = dimensionResource(R.dimen.size_12)
                    )
                    .size(32.dp)
                    .clickable {
                        onBackStack.invoke()
                    }
                    .align(Alignment.TopStart)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_download),
                contentDescription = "Back Button",
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(
                        top = dimensionResource(R.dimen.size_10),
                        end = dimensionResource(R.dimen.size_12)
                    )
                    .size(32.dp)
                    .clickable {
                       homeViewModel.downloadMedia(photo.src.original)
                    }
                    .align(Alignment.TopEnd)
            )
        }
    }
}
