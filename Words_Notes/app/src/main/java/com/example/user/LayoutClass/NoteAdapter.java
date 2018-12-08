package com.example.user.LayoutClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.words_notes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    private int resourceId ;
    public NoteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.resourceId = resource;
    }

    public NoteAdapter(Context context, int textViewResourceId, List<Note> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView title = (TextView)view.findViewById(R.id.note_title);
        TextView content = (TextView)view.findViewById(R.id.note_content);
        TextView date = (TextView)view.findViewById(R.id.note_date);

        title.setText(note.getTitle());
        content.setText(note.getContent());
        date.setText(note.getDate());

        return view;
    }
}
