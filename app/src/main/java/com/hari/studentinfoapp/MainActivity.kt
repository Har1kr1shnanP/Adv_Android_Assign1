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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentInfoApp()
        }
    }
}

@Composable
fun StudentInfoApp() {
    var id by remember { mutableStateOf(TextFieldValue("123")) } // Default ID
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var courseName by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { /* Implement Load */ }) { Text("Load") }
                Button(onClick = { /* Implement Store */ }) { Text("Store") }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    id = TextFieldValue("123")
                    username = TextFieldValue("")
                    courseName = TextFieldValue("")
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.error)
            ) {
                Text("Reset")
            }
        }
        Text("Your Name\nYour College ID", modifier = Modifier.padding(top = 16.dp))
    }
}
