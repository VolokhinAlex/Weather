package com.example.java.android1.weather.view.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun rememberSearchState(
    query: TextFieldValue = TextFieldValue(""),
    focused: Boolean = false,
    searching: Boolean = false,
): SearchState {
    return remember {
        SearchState(
            query = query,
            focused = focused,
            searching = searching,
        )
    }
}

@Composable
private fun SearchHint(modifier: Modifier = Modifier, searchHint: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)

    ) {
        Text(
            color = Color(0xff757575),
            text = searchHint,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SearchTextField(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    focused: Boolean,
    location: () -> Unit,
    modifier: Modifier = Modifier,
    searchHint: String
) {

    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = modifier
            .then(
                Modifier
                    .height(56.dp)
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = if (!focused) 16.dp else 0.dp,
                        end = 16.dp
                    )
            ),
        color = Color(0xffF5F5F5),
        shape = RoundedCornerShape(percent = 50),
    ) {

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = modifier
        ) {

            if (query.text.isEmpty()) {
                SearchHint(
                    modifier
                        .padding(start = 24.dp, end = 8.dp)
                        .align(Alignment.Center), searchHint)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        }
                        .focusRequester(focusRequester)
                        .padding(top = 9.dp, bottom = 8.dp, start = 24.dp, end = 8.dp),
                    singleLine = true
                )
                when {
                    searching -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .size(36.dp)
                        )
                    }
                    query.text.isNotEmpty() -> {
                        IconButton(onClick = onClearQuery) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                        }
                    }
                    query.text.isEmpty() -> {
                        IconButton(onClick = location) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                        }
                    }
                }
            }
        }

    }
}

@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onBack: ()-> Unit,
    searching: Boolean,
    focused: Boolean,
    modifier: Modifier = Modifier,
    searchHint: String,
    location: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AnimatedVisibility(visible = focused) {
            IconButton(
                modifier = Modifier.padding(start =2.dp),
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    onBack()
                }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }
        SearchTextField(
            query,
            onQueryChange,
            onSearchFocusChange,
            onClearQuery,
            searching,
            focused,
            location,
            modifier.weight(1f),
            searchHint
        )
    }
}