package com.example.demophotosearchapp.ui.screens.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import com.el.mybasekotlin.data.state.DataState
import com.el.mybasekotlin.data.state.ErrorCode
import com.example.demophotosearchapp.base.network.hasInternetConnection
import com.example.demophotosearchapp.data.local.AppPreferences
import com.example.demophotosearchapp.ui.component.rememberKeyboardVisibility
import com.example.demophotosearchapp.ui.screens.photodetails.PhotoDetailsScreens
import com.example.demophotosearchapp.ui.theme.beVietNamSemibold
import com.example.demophotosearchapp.utils.extension.color

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateTo: (String) -> Unit,
    onBackStack: () -> Unit,
    onExit: () -> Unit
) {
    /**
     * Can move the flags field to the ViewModel.
     */

    val listState = rememberLazyStaggeredGridState()
    var textSearch by rememberSaveable { mutableStateOf("") }
    var selected by rememberSaveable { mutableStateOf("All") }
    var showDetails by remember {
        mutableStateOf(false)
    }
    val homeViewModel = hiltViewModel<HomeViewModel>()
    DoubleBackToExit(onExit = onExit)

    var photo: Photo? = null
    SharedTransitionLayout {
        AnimatedContent(
            showDetails,
            label = "basic_transition"
        ) { targetState ->
            if (!targetState) {
                MainContent(
                    homeViewModel=homeViewModel,
                    modifier,
                    onNavigateTo,
                    onBackStack,
                    onExit,
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    clickImage = {
                        photo = it
                        showDetails = true
                    },
                    listState = listState,
                    keySearch = textSearch,
                    onTextSearchChange = { textSearch = it },
                    filterKey = selected,
                    onFilterKeyChange = { selected = it }
                )
            } else {
                BackHandler {
                    showDetails = false
                }
                photo?.let {
                    PhotoDetailsScreens(   homeViewModel=homeViewModel,
                        modifier,
                        onNavigateTo,
                        onBackStack = {
                            //TODO handle button back Image
                            showDetails = false
                        }, animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout, photo = it
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainContent(homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onNavigateTo: (String) -> Unit,
    onBackStack: () -> Unit,
    onExit: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    clickImage: (Photo) -> Unit,
    listState: LazyStaggeredGridState,
    keySearch: String,
    onTextSearchChange: (String) -> Unit,
    filterKey: String,
    onFilterKeyChange: (String) -> Unit
) {
    var textSearch = keySearch
    var selected = filterKey

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
//    var selected by rememberSaveable { mutableStateOf("All") }


    val hintText = stringResource(R.string.text_hit_search)
    var isLoading by remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(true) }
    var loadMore by remember { mutableStateOf(false) }
    var showEmptyScreen by remember { mutableStateOf(false) }

    val state = rememberPullRefreshState(refreshing && isLoading, {
        refreshing = true
        loadMore = false
        Timber.d("HomeScreen : Reload photos")
        if (textSearch.isNotEmpty())
            homeViewModel.reloadSearch(false,
                textSearch,
                orientation = selected.takeIf { it != "All" } ?: "")
    })

    fun resetFlag() {
        isLoading = false
        refreshing = false
        loadMore = false
    }
    when (photoSearchState) {
        is DataState.Loading -> {
            Timber.e("photoSearchState  DataState.Loading")
            isLoading = true
            if (!loadMore) refreshing = true
        }

        is DataState.Empty -> {
            Timber.e("photoSearchState  DataState.Empty")
            resetFlag()
        }

        is DataState.Error -> {
            Timber.e("photoSearchState  DataState.Error")
            val dataError = photoSearchState as DataState.Error
            if (dataError.code == ErrorCode.DATA_NOT_FOUND.code) {
                //Show Empty Screen
                showEmptyScreen = true
            }

        }

        is DataState.Success -> {
            showEmptyScreen = false
            Timber.e("photoSearchState  DataState.Success")
        }
    }
    fun callApiSearch(isLoadMore: Boolean) {
        if (!hasInternetConnection(context)) {
            Toast.makeText(context, context.getString(R.string.network_error), Toast.LENGTH_LONG)
                .show()
        }

        homeViewModel.searchPhoto(isLoadMore,
            textSearch,
            orientation = selected.takeIf { it != "All" } ?: "")
        AppPreferences.saveSearchQueryJson(textSearch)

    }
    Box(modifier = Modifier.fillMaxSize()) {

        /**
         * Header+ photo list
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box {
                //Background header
                Image(
                    painter = painterResource(id = randomImageResource), contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.size_200))
                        .align(Alignment.TopStart), contentScale = ContentScale.Crop
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
                            color = Color.Gray, offset = Offset(5f, 5f), blurRadius = 3f
                        )
                    ),
                    color = Color.White

                )

            }
            /**
             * Content list image results
             */

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .pullRefresh(state)
                    .navigationBarsPadding()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    StaggeredGridDemo(searchData, loadMoreAction = {
                        Timber.d("HomeScreen : Load more photos")
                        loadMore = true
                        if (!isLoading) homeViewModel.loadMore(true,
                            textSearch,
                            orientation = selected.takeIf { it != "All" } ?: "")
                    }, sharedTransitionScope, animatedVisibilityScope, clickImage, listState)
                }

                if (showEmptyScreen)
                    Column(
                        modifier = Modifier
                            .fillMaxSize(), verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.not_found),
                                contentDescription = "Not found Icon",
                                modifier = Modifier
                                    .width(dimensionResource(R.dimen.size_150))
                                    .aspectRatio(250.toFloat() / 200.toFloat()),
                                alignment = Alignment.Center

                            )

                        }
                        Text(
                            text = stringResource(R.string.no_results_found),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .statusBarsPadding(),
                            style = beVietNamSemibold(
                                dimenSp(R.dimen.text_size_18),
                            ),
                            color = Color.Black

                        )

                    }
                PullRefreshIndicator(
                    refreshing && isLoading,
                    state,
                    Modifier.align(Alignment.TopCenter),
                    contentColor = "#56CCF2".color
                )

            }
        }
        /**
         * Content Search function
         */

        var showHistory by remember { mutableStateOf(false) }
        val focusModifier = Modifier.onFocusChanged { focusState ->
        }
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {


            Box(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.size_20),
                        end = dimensionResource(R.dimen.size_20),
                        top = dimensionResource(R.dimen.size_114),
                    )
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.size_56))
                    .shadow(8.dp, RoundedCornerShape(dimensionResource(R.dimen.size_60)))


                    .clip(
                        RoundedCornerShape(dimensionResource(R.dimen.size_60))
                    )
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = Dimens.Padding6)
                            .size(dimenDp(R.dimen.size_32))
                            .align(Alignment.CenterVertically)
                    )


                    BasicTextField(value = textSearch,
                        onValueChange = {
                            onTextSearchChange.invoke(it)
                        },
                        singleLine = true,
                        textStyle = beVietNamMedium(
                            dimenSp(R.dimen.text_size_12), textAlign = TextAlign.Start

                        ),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .focusRequester(focusRequester)
                            .then(focusModifier),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(onSearch = {
                            //TODO call Api Search
                            if (textSearch.isNotEmpty()) {

                                callApiSearch(false)

                                keyboardController?.hide()
                            } else Toast.makeText(
                                context, "Please input the key word.", Toast.LENGTH_LONG
                            ).show()

                        }),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 6.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (textSearch.isEmpty()) {
                                    Text(
                                        text = hintText,
                                        color = Color.Gray,
                                        style = beVietNamRegular(
                                            dimenSp(R.dimen.text_size_12), TextAlign.Start
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        })


                    SimpleDropdown(default = selected, onSelected = { it ->
                        selected = it
                        onFilterKeyChange.invoke(it)
                        if (textSearch.isNotEmpty()) {
                            callApiSearch(false)
                        }
                    })

                }
            }
            //Check keyboard show + empty input  then show histories
            val keyboardVisibleState = rememberKeyboardVisibility()
            val isKeyboardVisible = keyboardVisibleState.value
            if (isKeyboardVisible && textSearch.isEmpty()) {
                showHistory = true
            } else showHistory = false
            val allItems =
                if (showHistory) AppPreferences.getSearchHistoryJson() else mutableListOf()

            AnimatedVisibility(
                visible = showHistory && allItems.isNotEmpty(),
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.size_58),
                            end = dimensionResource(R.dimen.size_50) + dimensionResource(R.dimen.box_filter_size)
                        )
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(colorResource(R.color.gray))
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    items(allItems) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clickable {
                                    textSearch = item
                                    onTextSearchChange.invoke(item)
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    callApiSearch(false)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier

                                    .fillMaxWidth()
                                    .padding(start = Dimens.Padding6),
                                text = item,
                                color = Color.Gray,
                                style = beVietNamRegular(
                                    dimenSp(R.dimen.text_size_12), TextAlign.Start
                                )
                            )

                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun StaggeredGridDemo(
    searchData: MutableList<Photo>,
    loadMoreAction: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    clickImage: (Photo) -> Unit, listState: LazyStaggeredGridState
) {
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
        contentPadding = PaddingValues(2.dp),
        state = listState,
        verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(count = searchData.size, key = { it }) { index ->
            val item = searchData[index]
            with(sharedTransitionScope) {
                AsyncImage(
                    model = item.src.medium,
                    placeholder = ColorPainter(Color.LightGray),
                    contentDescription = "Photo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(item.width.toFloat() / item.height.toFloat())
                        .clickable {
                            clickImage.invoke(item)
                        }
                        .sharedElement(
                            rememberSharedContentState(key = item.id),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
            }
        }

    }
}

