package com.dany.dnyaneshscassignment.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dany.dnyaneshscassignment.ui.viewModel.ContactsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableListScreen(viewModel: ContactsViewModel) {
    LaunchedEffect(Unit, block = {
        viewModel.getContacts()
    })
    val allContacts =  remember {viewModel.contactList}
    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val filteredItems = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            allContacts
        } else {
            allContacts.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // Add some padding
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onSearch = { active = false }, // Close search bar on search
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Search contacts...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
    ) {

    }
    ContactListView(filteredItems)
}