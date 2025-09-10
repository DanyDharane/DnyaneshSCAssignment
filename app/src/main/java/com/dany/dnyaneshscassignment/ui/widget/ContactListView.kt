package com.dany.dnyaneshscassignment.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dany.dnyaneshscassignment.data.model.Contact

@Composable
fun ContactListView(personList:List<Contact>) {

    LazyColumn {
        itemsIndexed(personList) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Text(
                        text = "Name. ${item.name}",
                        modifier = Modifier
                            .padding(16.dp)

                    )
                    Text(
                        text = "Email. ${item.email}",
                        modifier = Modifier
                            .padding(16.dp)

                    )
                    Text(
                        text = "Website. ${item.website}",
                        modifier = Modifier
                            .padding(16.dp)

                    )
                }
            }
        }
    }
}