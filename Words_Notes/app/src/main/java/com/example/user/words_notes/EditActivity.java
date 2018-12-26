package com.example.user.words_notes;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.dao.Notes;
import com.example.user.dao.NotesCRUD;
import com.example.user.dao.NotesDBHelper;

import java.io.FileReader;
import java.util.Date;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    private NotesDBHelper notedsdb;
    private NotesCRUD notesUtil;
    private Button back ;
    private Button complete;
    private EditText titleText ;
    private EditText contentText;
    private String titleString, contentString, folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_new_notes);

        //open database, write
        notedsdb = new NotesDBHelper(this);
        notesUtil = new NotesCRUD(notedsdb);

        //View
        back = (Button)findViewById(R.id.backButton);
        complete = (Button)findViewById(R.id.completeButton);
        titleText = (EditText)findViewById(R.id.titleText);
        contentText = (EditText)findViewById(R.id.contentText);


        Intent intent = getIntent();
        titleString = intent.getStringExtra("title");
        contentString = intent.getStringExtra("content");
        folderName = intent.getStringExtra("folder_name");


        titleText.setText(titleString);
        titleText.setText(titleString);
        contentText.setText(contentString);

        back.setOnClickListener(this);
        complete.setOnClickListener(this);

    }

    //If Button new_note was clicked, start EditActivity here
    public static void actionStart(Context context, String folder_name){
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("folder_name", folder_name);
        intent.putExtra("title", "未命名");
        intent.putExtra("content", "");
        context.startActivity(intent);
    }

    //If notes list item was clicked or click long time, start EditActivity here
    public static void actionStart(Context context, String folder, String title, String content){
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("folder_name", folder);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }

    //version 1, insert data directly
//    public void insertAll(SQLiteDatabase db, String title, String content, String folder_name){
//        String id = getId(title);
//        Date d = new Date();
//        String date = "";
//        date += d.getYear() + "-" + d.getMonth() + "-" + d.getDay();
//
//        String sql = "INSERT INTO " + Notes.Note.TABLE_NAME + " VALUES('"
//                + id + "','"
//                + folder_name + "','"
//                + title + "',' "
//                + date + "','"
//                + content + "')";
//        Log.d("SQL", sql);
//        db.execSQL(sql);
//    }
//
//    private String getId(String title){
//        String id = "";
//        for(int i = 0; i < title.length(); i++){
//            id += (int)title.charAt(i);
//        }
//        return id;
//    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.backButton :{
                Intent intent = new Intent();
                finish();
            }break;
            case R.id.completeButton:{
                if(contentString.equals("") || contentString == null){
                    //If content is empty, insert new data to database when complete button is clicked
                    Log.d("Button", "click on compelete");
                    //                insertAll(dbutil, titleText.getText().toString(),contentText.getText().toString(), folderName);
                    notesUtil.insert(folderName, titleText.getText().toString(),contentText.getText().toString() );
                    Log.d("SQL", "Insert Successfully!");
                }else{
                    //If content is not empty, update the data, change the date in data with current date
                    Log.d("BUTTON", "Click on compelete");
                    notesUtil.update(folderName, titleText.getText().toString(),contentText.getText().toString() );
                    Log.d("SQL", "Update Successfully!");
                    Intent intent = new Intent();
                    finish();
                }
            }break;
            default:break;
        }
    }
}
