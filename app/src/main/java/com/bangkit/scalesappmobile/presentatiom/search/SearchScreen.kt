package com.bangkit.scalesappmobile.presentatiom.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.bangkit.scalesappmobile.presentatiom.common.EmptyStateComponent
import com.bangkit.scalesappmobile.presentatiom.common.ErrorStateComponent
import com.bangkit.scalesappmobile.presentatiom.common.LoadingStateComponent
import com.bangkit.scalesappmobile.presentatiom.home.component.ScalesItem
import com.bangkit.scalesappmobile.presentatiom.home.component.StandardToolbar
import com.bangkit.scalesappmobile.presentatiom.search.state.SearchState
import com.bangkit.scalesappmobile.util.UiEvents
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun SearchScreen(
    navigator: SearchNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val searchState = viewModel.searchState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    SearchScreenContent(
        currentSearchString = viewModel.searchString.value,
        searchState = searchState,
        onScalesClick = { id ->
            navigator.openScalesDetails(id = id)
        },
        onSearchStringChange = { newValue ->
            viewModel.setSearchString(newValue)
        },
        onSearch = { searchParam ->
            viewModel.search(searchParam)
        },
        onClickBack = {
            navigator.popBackStack()
        }
    )
}

@Composable
private fun SearchScreenContent(
    searchState: SearchState,
    onScalesClick: (String) -> Unit,
    onSearchStringChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    currentSearchString: String,
    onClickBack: () -> Unit,
) {

    val lazyPagingItems = searchState.searchData?.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        StandardToolbar(
            navigate = {
                onClickBack()
            },
            title = {
                Text(text = "Cari Timbangan", fontSize = 18.sp)
            },
            showBackArrow = true,
            navActions = {
            }
        )

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            currentSearchString = currentSearchString,
            onSearchStringChange = onSearchStringChange,
            onSearch = onSearch
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                lazyPagingItems?.itemCount?.let {
                    items(it) { index ->
                        lazyPagingItems[index]?.let { scales ->
                            ScalesItem(
                                scales = scales,
                                onClick = {
                                    onScalesClick(scales.id)
                                }
                            )
                        }
                    }
                }
            }

            // Loading data
            if (searchState.isLoading) {
                LoadingStateComponent()
            }

            // An Error has occurred
            if (!searchState.isLoading && searchState.error != null) {
                ErrorStateComponent(errorMessage = searchState.error)
            }

            // Loaded Data but the list is empty
            if (!searchState.isLoading && searchState.error == null && (lazyPagingItems == null || lazyPagingItems.itemCount == 0)) {
                EmptyStateComponent()
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
    onSearchStringChange: (String) -> Unit,
    currentSearchString: String,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = currentSearchString,
        onValueChange = { onSearchStringChange(it) },
        placeholder = {
            Text(
                text = "Cari Timbangan"
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, CircleShape)
            .background(Color.Transparent, CircleShape),
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions {
            keyboardController?.hide()
            onSearch(currentSearchString)
        },
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        maxLines = 1,
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = {
                    keyboardController?.hide()
                    onSearch(currentSearchString)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    )
}