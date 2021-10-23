package com.example.kotlintodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickDeleteInterface, NoteClickInterface {
    private lateinit var notesRv:RecyclerView
    private lateinit var addFAB:FloatingActionButton
    lateinit var viewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRv=findViewById(R.id.recyclerView)
        addFAB=findViewById(R.id.floatingButton)
        notesRv.layoutManager= LinearLayoutManager(this)

        val noteAdapter = NotesAdapter(this,this,this)
        notesRv.adapter=noteAdapter
        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(NotesViewModel::class.java)
        viewModel.allNotes.observe(this,{list->
            list?.let {
                noteAdapter.updateList(list)
            }
        })

        addFAB.setOnClickListener {
            val intent=Intent(this@MainActivity,EditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle} Deleted" , Toast.LENGTH_SHORT).show()
    }

    override fun onNoteClick(note: Note) {
        val intent=Intent(this@MainActivity,EditNoteActivity::class.java)
        intent.putExtra("notetype","edit")
        intent.putExtra("title",note.noteTitle)
        intent.putExtra("description",note.notDescription)
        intent.putExtra("id",note.id)
        startActivity(intent)
        this.finish()
    }
}