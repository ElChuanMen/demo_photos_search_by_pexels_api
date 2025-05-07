package com.example.demophotosearchapp.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.demophotosearchapp.R
import com.example.demophotosearchapp.ui.component.SimpleDropdown
import com.example.demophotosearchapp.ui.screens.DoubleBackToExit
import com.example.demophotosearchapp.ui.theme.beVietNamMedium
import com.example.demophotosearchapp.ui.theme.beVietNamRegular
import com.example.demophotosearchapp.ui.theme.playWriteMediumRegular
import com.example.demophotosearchapp.utils.extension.Dimens
import com.example.demophotosearchapp.utils.extension.dimenDp
import com.example.demophotosearchapp.utils.extension.dimenSp
import timber.log.Timber
import kotlin.random.Random
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.demophotosearchapp.data.model.Photo
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import com.el.mybasekotlin.data.state.DataState
import com.example.demophotosearchapp.ui.component.isKeyboardVisible
import com.example.demophotosearchapp.ui.component.rememberKeyboardVisibility
import com.example.demophotosearchapp.utils.extension.color

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateTo: (String) -> Unit,
    onBackStack: () -> Unit, onExit: () -> Unit
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val photosHeader = remember {
        listOf(
            R.drawable.header_search,
            R.drawable.header_second,
            R.drawable.header_third,
            R.drawable.header_fourth,
            R.drawable.header_fifth,
            R.drawable.header_sixth
        )
    }
    val context = LocalContext.current
    val randomIndex = remember { Random.nextInt(photosHeader.size) }
    val randomImageResource = remember { photosHeader[randomIndex] }
    val keyboardController = LocalSoftwareKeyboardController.current
    val photoSearchState by homeViewModel.searchPhotoState.collectAsStateWithLifecycle()
    val searchData = homeViewModel.dataPhotoSearch
    var selected by remember { mutableStateOf("All") }
    var textSeach by remember { mutableStateOf("") }
    val hintText = stringResource(R.string.text_hit_search)
    var isLoading by remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(true) }
    var loadMore by remember { mutableStateOf(false) }

    val state = rememberPullRefreshState(refreshing && isLoading, {
        refreshing = true
        loadMore = false
        Timber.d("HomeScreen : Reload photos")
        homeViewModel.reloadSearch(
            false,
            textSeach,
            orientation = selected.takeIf { it != "All" } ?: "")
    })

    fun resetFlag() {
        isLoading = false
        refreshing = false
        loadMore = false
    }
    when (photoSearchState) {
        is DataState.Loading -> {
            isLoading = true
            if (!loadMore)
                refreshing = true
        }

        is DataState.Empty -> {
            resetFlag()
        }

        is DataState.Error -> {
            resetFlag()
        }

        is DataState.Success -> {

        }
    }

    DoubleBackToExit(onExit = onExit)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box {
            //Background header
            Image(
                painter = painterResource(id = randomImageResource),
                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.size_200))
                    .align(Alignment.TopStart),
                contentScale = ContentScale.Crop
            )
            //title screen header
            Text(
                text = stringResource(R.string.title_home),
                modifier = Modifier
                    .padding(top = dimenDp(R.dimen.size_10))
                    .align(Alignment.TopStart)
                    .padding(start = dimensionResource(R.dimen.size_30))
                    .statusBarsPadding(),
                style = playWriteMediumRegular(
                    dimenSp(R.dimen.text_size_22), shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(5f, 5f),
                        blurRadius = 3f
                    )
                ), color = Color.White

            )
            /**
             * Content Search function
             */
            Box(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.size_20),
                        end = dimensionResource(R.dimen.size_20),
                        bottom = dimensionResource(R.dimen.size_30)
                    )
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.size_56))
                    .shadow(8.dp, RoundedCornerShape(dimensionResource(R.dimen.size_60)))

                    .align(Alignment.BottomCenter)
                    .clip(
                        RoundedCornerShape(dimensionResource(R.dimen.size_60))
                    )
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = Dimens.Padding6)
                            .size(dimenDp(R.dimen.size_32))
                            .align(Alignment.CenterVertically)
                    )


                    val focusModifier = Modifier
                        .onFocusChanged { focusState ->
//
                        }
                    val focusRequester = remember { FocusRequester() }
                    val focusManager = LocalFocusManager.current


                    BasicTextField(
                        value = textSeach,
                        onValueChange = { textSeach = it },
                        singleLine = true,
                        textStyle = beVietNamMedium(
                            dimenSp(R.dimen.text_size_12),
                            textAlign = TextAlign.Start
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .focusRequester(focusRequester)
                            .then(focusModifier), keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                //TODO call Api Search
                                if (textSeach.isNotEmpty()) {
                                    homeViewModel.searchPhoto(
                                        false,
                                        textSeach,
                                        orientation = selected.takeIf { it != "All" } ?: "")
                                    keyboardController?.hide()
                                } else Toast.makeText(
                                    context,
                                    "Please input the key word.",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 6.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (textSeach.isEmpty()) {
                                    Text(
                                        text = hintText,
                                        color = Color.Gray, // Màu xám nhạt cho hint
                                        style = beVietNamRegular(
                                            dimenSp(R.dimen.text_size_12),
                                            TextAlign.Start
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )


                    SimpleDropdown(onSelected = { it ->
                        selected = it
                        if (textSeach.isNotEmpty()) {
                            homeViewModel.reloadSearch(
                                false,
                                textSeach,
                                orientation = it.takeIf { it != "All" } ?: "")
                        }
                    })

                }
            }
        }
        /**
         * Content list image results
         */
        Box(
            modifier = Modifier
                .pullRefresh(state)
                .navigationBarsPadding()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                StaggeredGridDemo(searchData, loadMoreAction = {
                    Timber.d("HomeScreen : Load more photos")
                    loadMore = true
                    homeViewModel.loadMore(
                        true,
                        textSeach,
                        orientation = selected.takeIf { it != "All" } ?: "")
                })
//                if (loadMore)
//                    CircularProgressIndicator()
            }

            PullRefreshIndicator(
                refreshing && isLoading,
                state,
                Modifier.align(Alignment.TopCenter),
                contentColor = "#56CCF2".color
            )

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaggeredGridDemo(searchData: MutableList<Photo>, loadMoreAction: () -> Unit) {
    val listState = rememberLazyStaggeredGridState()
    LaunchedEffect(listState.canScrollForward) {
        if (listState.canScrollForward.not() && listState.firstVisibleItemIndex > 1) {

            loadMoreAction.invoke()
        }
    }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        contentPadding = PaddingValues(2.dp), state = listState,
        verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(count = searchData.size, key = { it }) { index ->
            val item = searchData[index]

            AsyncImage(
                model = item.src.medium,
                placeholder = ColorPainter(Color.LightGray),
                contentDescription = "Avatar",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(item.width.toFloat() / item.height.toFloat())
            )

        }
    }
}

