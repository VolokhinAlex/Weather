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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.java.android1.weather.view.theme.SearchFieldColor
import com.example.java.android1.weather.view.theme.SearchFieldHintColor

/**
 * The method for remembering all search states
 */

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

/**
 * The method for showing hint in the search text field
 */


@Composable
private fun SearchTextFieldHint(modifier: Modifier = Modifier, searchHint: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Text(
            color = SearchFieldHintColor,
            text = searchHint,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * The method for creating a search text field with states
 * @param query - The value written in the text field
 * @param onQueryChange - Occurs every time the value in the text field changes
 * @param onSearchFocusChange - Occurs when the text field is clicked and when the back button is clicked
 * @param onClearQuery - Occurs to clear a value in a text field
 * @param location - Called when requesting weather by location
 * @param searching - Search states. Occurs when the user types some text
 * @param focused - Occurs if there is a focus on the text field
 * @param searchHint - Hint for a text field
 */

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
        color = SearchFieldColor,
        shape = RoundedCornerShape(percent = 50),
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = modifier
        ) {
            if (query.text.isEmpty()) {
                SearchTextFieldHint(
                    modifier
                        .padding(start = 24.dp, end = 8.dp)
                        .align(Alignment.Center), searchHint
                )
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

/**
 * The method for displaying the search text field
 * @param query - The value written in the text field
 * @param onQueryChange - Occurs every time the value in the text field changes
 * @param onSearchFocusChange - Occurs when the text field is clicked and when the back button is clicked
 * @param onClearQuery - Occurs to clear a value in a text field
 * @param onBack - Occurs when you click on the back arrow
 * @param searching - Search states. Occurs when the user types some text
 * @param focused - Occurs if there is a focus on the text field
 * @param searchHint - Hint for a text field
 * @param location - Called when requesting weather by location
 */

@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    onBack: () -> Unit,
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
                modifier = Modifier.padding(start = 2.dp),
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