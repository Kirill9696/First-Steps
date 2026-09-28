package com.example.learn2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.learn2.ui.theme.Learn2Theme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UserScreen()
        }
    }
}





@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    UserScreenContent(
        uiState = uiState,
        onLoadUser = { viewModel.loadUser() },
        onError = { viewModel.showError() },
        onTestDispatchers = { viewModel.testDispatchers() }
    )
}

@Composable
fun UserScreenContent(
    uiState: UiState,
    onLoadUser: () -> Unit,
    onError: () -> Unit,
    onTestDispatchers: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = uiState) {
            is UiState.Loading -> {
                Text("Loading...")
            }
            is UiState.Error -> {
                Text("Error")
                Text(state.message)
            }
            is UiState.Success -> {
                Text("Name: ${state.name}")
                Text("Profession: ${state.profession}")
            }
        }

        Button(onClick = onLoadUser) {
            Text("Load User")
        }

        Button(onClick = onError) {
            Text("Error")
        }

        Button(onClick = onTestDispatchers) {
            Text("Test Dispatchers")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    var uiState by remember {
        mutableStateOf<UiState>(
            UiState.Success(
                name = "Kirill",
                profession = "Android Developer"
            )
        )
    }

    Learn2Theme {
        UserScreenContent(
            uiState = uiState,
            onLoadUser = {
                uiState = UiState.Loading
            },
            onError = {
                uiState = UiState.Error("User not found")
            },
            onTestDispatchers = {}
        )
    }
}
