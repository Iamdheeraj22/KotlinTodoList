package com.example.kotlintodolist

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class EditNoteActivity : AppCompatActivity() {
    lateinit var button:Button
    lateinit var editTitle:EditText
    lateinit var editDescription:EditText
    lateinit var viewModel: NotesViewModel
    var noteId=-1
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        button=findViewById(R.id.button)
        editTitle=findViewById(R.id.et_title)
        editDescription=findViewById(R.id.et_description)
        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NotesViewModel::class.java)
        val noteType=intent.getStringExtra("notetype").toString()
        if(noteType == "edit"){
            val noteTitle=intent.getStringExtra("title").toString()
            val noteDescription=intent.getStringExtra("description").toString()
            noteId=intent.getIntExtra("id",-1)
            button.text = "Update Note"
            editTitle.setText(noteTitle)
            editDescription.setText(noteDescription)
        }else {button.setText("Save Note")}

        button.setOnClickListener {
            val title=editTitle.text.toString()
            val description=editDescription.text.toString()

            if(noteType.equals("edit")){
                if(title.isNotEmpty() && description.isNotEmpty())
                {
                    val sdf=SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDate:String=sdf.format(Date())
                    val updateNote=Note(title,description,currentDate)

                    updateNote.id=noteId
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated",Toast.LENGTH_SHORT).show()
                }else{
                    if(title.isNotEmpty() && description.isNotEmpty()){
                        val sdf=SimpleDateFormat("dd MM, yyyy - HH:mm")
                        val currentDate:String=sdf.format(Date())

                        viewModel.insertNote(Note(title,description,currentDate))
                        Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
                    }
                }
                startActivity(Intent(applicationContext,MainActivity::class.java))
                this.finish()
            }
        }
    }
}