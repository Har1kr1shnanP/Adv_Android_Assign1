package com.hari.studentinfoapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStoreManager = DataStoreManager(applicationContext)

        setContent {
            StudentInfoApp(dataStoreManager)
        }
    }
}

@Composable
fun StudentInfoApp(dataStoreManager: DataStoreManager) {
    var id by remember { mutableStateOf(TextFieldValue("660")) } // Default ID (last 3 digits of student ID)
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var courseName by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Input Fields
        Column {
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = courseName,
                onValueChange = { courseName = it },
                label = { Text("Course Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            val data = dataStoreManager.studentData.first() // Load the first emitted value
                            id = TextFieldValue(data.id)
                            username = TextFieldValue(data.username)
                            courseName = TextFieldValue(data.courseName)
                        }
                    }
                ) {
                    Text("Load")
                }
                Button(
                    onClick = {
                        scope.launch {
                            dataStoreManager.saveData(
                                id.text,
                                username.text,
                                courseName.text
                            )
                        }
                    }
                ) {
                    Text("Store")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    scope.launch {
                        dataStoreManager.clearData() // Clear DataStore
                        id = TextFieldValue("660") // Reset to default
                        username = TextFieldValue("")
                        courseName = TextFieldValue("")
                    }
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.error)
            ) {
                Text("Reset")
            }
        }

        // About Section
        Text(
            "Harikrishnan Parameswaran\n301474660", // Replace with your name and full student ID
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
