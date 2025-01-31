package com.hari.studentinfoapp
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("student_prefs")

class DataStoreManager(private val context: Context) {
    private val ID_KEY = stringPreferencesKey("id")
    private val USERNAME_KEY = stringPreferencesKey("username")
    private val COURSE_NAME_KEY = stringPreferencesKey("course_name")

    val studentData: Flow<StudentData> = context.dataStore.data.map { preferences ->
        StudentData(
            id = preferences[ID_KEY] ?: "",
            username = preferences[USERNAME_KEY] ?: "",
            courseName = preferences[COURSE_NAME_KEY] ?: ""
        )
    }

    suspend fun saveData(id: String, username: String, courseName: String) {
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = id
            preferences[USERNAME_KEY] = username
            preferences[COURSE_NAME_KEY] = courseName
        }
    }

    suspend fun clearData() {
        context.dataStore.edit { it.clear() }
    }
}

data class StudentData(val id: String, val username: String, val courseName: String)
