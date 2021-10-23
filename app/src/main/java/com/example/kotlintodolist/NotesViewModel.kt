package com.example.kotlintodolist

import android.app.Application
import android.view.KeyEvent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application):AndroidViewModel (application)
{
    val allNotes:LiveData<List<Note>>
    val repository:NotesRepository

    init {
        val dao =NoteDatabase.getDatabase(application).getNoteDao()
        repository= NotesRepository(dao)
        allNotes=repository.allNotes
    }

    fun deleteNote(note:Note) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }

    fun updateNote(note: Note)= viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun insertNote(note: Note)= viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}