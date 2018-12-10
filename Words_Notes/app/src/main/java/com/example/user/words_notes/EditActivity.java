package com.example.user.words_notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_notes);
    }

    //If Button new_note was clicked, start EditActivity here
    public static void actionStart(Context context, String folder_name){
        Intent intent = new Intent(context, EditText.class);
        intent.putExtra("folder_name", folder_name);
        context.startActivity(intent);
    }

    //If notes list item was clicked or click long time, start EditActivity here
    public static void actionStart(Context context, String title, String content){
        Intent intent = new Intent(context, EditText.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }


}
