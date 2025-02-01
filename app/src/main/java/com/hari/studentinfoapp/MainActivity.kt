package com.hari.studentinfoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Entry point
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize DataStoreManager to manage local storage
        val dataStoreManager = DataStoreManager(applicationContext)
        setContent {
            // Set the theme and launch the main UI
            MaterialTheme {
                StudentInfoApp(dataStoreManager)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentInfoApp(dataStoreManager: DataStoreManager) {
    // State variables to hold user input
    var id by remember { mutableStateOf(TextFieldValue("660")) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var courseName by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            // Top App Bar with centered title
            CenterAlignedTopAppBar(title = { Text("Student Info") })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Input field for Student ID
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    label = { Text("Student ID") },
                    modifier = Modifier.fillMaxWidth()
                )
                // Input field for Username
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                // Input field for Course Name
                OutlinedTextField(
                    value = courseName,
                    onValueChange = { courseName = it },
                    label = { Text("Course Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Row containing Load and Store buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Load button retrieves stored data
                    Button(
                        onClick = {
                            scope.launch {
                                val data = dataStoreManager.studentData.first()
                                id = TextFieldValue(data.id)
                                username = TextFieldValue(data.username)
                                courseName = TextFieldValue(data.courseName)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Load")
                    }
                    // Store button saves input data
                    Button(
                        onClick = {
                            scope.launch {
                                dataStoreManager.saveData(id.text, username.text, courseName.text)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Store")
                    }
                }

                // Reset button clears stored data and resets input fields
                Button(
                    onClick = {
                        scope.launch {
                            dataStoreManager.clearData()
                            id = TextFieldValue("660")
                            username = TextFieldValue("")
                            courseName = TextFieldValue("")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reset")
                }

                // Display student details
                Text(
                    "Harikrishnan Parameswaran\n301474660",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    )
}
