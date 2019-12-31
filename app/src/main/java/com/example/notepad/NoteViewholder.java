package com.example.notepad;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewholder extends RecyclerView.ViewHolder {

    TextView name;
    TextView time;
    TextView note;

    public NoteViewholder(@NonNull View view) {
        super(view);

        name = view.findViewById(R.id.name);
        time = view.findViewById(R.id.time );
        note = view.findViewById(R.id.note);


    }
}
