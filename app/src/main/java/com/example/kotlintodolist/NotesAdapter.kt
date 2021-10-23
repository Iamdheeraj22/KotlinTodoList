package com.example.kotlintodolist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class NotesAdapter(val context:Context,
                    val noteClickDeleteInterface: NoteClickDeleteInterface,
                    val noteClickInterface:NoteClickInterface
                   ): RecyclerView.Adapter<NotesAdapter.ViewHolder>()
{
    private val allNotes=ArrayList<Note>()

    inner  class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val title:TextView=itemView.findViewById(R.id.noteTitle)
        val timestamp:TextView=itemView.findViewById(R.id.noteTimeStamp)
        val deleteIcon:ImageView=itemView.findViewById(R.id.deleteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout=LayoutInflater.from(context).inflate(R.layout.note_rv_item,parent,false)
        return ViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = allNotes[position].noteTitle
        holder.timestamp.text = "Last Updated :"+ allNotes[position].timeStamp

        holder.deleteIcon.setOnClickListener{
            noteClickDeleteInterface.onDeleteIconClick(allNotes[position])
        }

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes[position])
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList:List<Note>)
    {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface{
    fun onNoteClick(note: Note)
}