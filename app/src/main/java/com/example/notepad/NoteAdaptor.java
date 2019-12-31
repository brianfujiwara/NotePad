package com.example.notepad;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdaptor extends RecyclerView.Adapter<NoteViewholder> {

    private ArrayList<Note> aList;
    private MainActivity mainActivity;

    NoteAdaptor(ArrayList<Note> list, MainActivity mainActivity) {
        aList = list;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public NoteViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout, parent, false);

        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new NoteViewholder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewholder holder, int position) {
        Note selectedAnimal = aList.get(position);

        holder.name.setText(selectedAnimal.getName());
        //holder.time.setText((selectedAnimal.getTime()));
        holder.time.setText(new Date().toString());


        holder.note.setText(selectedAnimal.getNote());

    }

    @Override
    public int getItemCount() {


        return aList.size();
    }
}
