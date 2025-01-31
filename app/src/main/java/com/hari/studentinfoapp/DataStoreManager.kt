package com.hari.studentinfoapp
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore instance
private val Context.dataStore by preferencesDataStore(name = "student_prefs")

class DataStoreManager(private val context: Context) {

    // Define keys for storing data
    private val idKey = stringPreferencesKey("id")
    private val usernameKey = stringPreferencesKey("username")
    private val courseNameKey = stringPreferencesKey("course_name")

    // Expose student data as a Flow
    val studentData: Flow<StudentData> = context.dataStore.data.map { preferences ->
        StudentData(
            id = preferences[idKey] ?: "",
            username = preferences[usernameKey] ?: "",
            courseName = preferences[courseNameKey] ?: ""
        )
    }

    // Save data to DataStore
    suspend fun saveData(id: String, username: String, courseName: String) {
        context.dataStore.edit { preferences ->
            preferences[idKey] = id
            preferences[usernameKey] = username
            preferences[courseNameKey] = courseName
        }
    }

    // Clear all data from DataStore
    suspend fun clearData() {
        context.dataStore.edit { it.clear() }
    }
}

// Data class to hold student information
data class StudentData(
    val id: String,
    val username: String,
    val courseName: String
)
