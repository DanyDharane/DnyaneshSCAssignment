package com.dany.dnyaneshscassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dany.dnyaneshscassignment.ui.theme.DnyaneshSCAssignmentTheme
import com.dany.dnyaneshscassignment.ui.viewModel.ContactsViewModel
import com.dany.dnyaneshscassignment.ui.widget.SearchableListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DnyaneshSCAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize().padding(8.dp)) { innerPadding ->
                    Column() {
                        Greeting(
                            name = "Dnyanesh",
                            modifier = Modifier
                                .align(androidx.compose.ui.Alignment.CenterHorizontally)
                                .padding(24.dp)

                        )
                        SearchableListScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Contacts by $name!",
        modifier = modifier,
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        color = Color.Magenta
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DnyaneshSCAssignmentTheme {
        Greeting("Android")
    }
}