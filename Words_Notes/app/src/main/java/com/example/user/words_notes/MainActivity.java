package com.example.user.words_notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.LayoutClass.Note;
import com.example.user.LayoutClass.NoteAdapter;
import com.example.user.dao.NotesCRUD;
import com.example.user.dao.NotesDBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    NotesDBHelper notesdb;
    private SQLiteDatabase dbutil;
    private List<Note> noteList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link database
        notesdb = new NotesDBHelper(this);
        NotesCRUD noteutill = new NotesCRUD(notesdb);

        dbutil = notesdb.getReadableDatabase();

        //find all button or ui
        ListView list = (ListView)findViewById(R.id.listView);
        Button newNotes = (Button)findViewById(R.id.new_note);
        final Button folder = (Button)findViewById(R.id.folder);
        TextView folder_name = (TextView)findViewById(R.id.folder_name);


//        noteList = getNotesList();
        noteList = noteutill.getAll();
        NoteAdapter adapter = new NoteAdapter(MainActivity.this, R.layout.note_item, noteList);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = noteList.get(i);
                TextView folder_name = (TextView)findViewById(R.id.folder_name);
                EditActivity.actionStart(MainActivity.this, folder_name.getText().toString(),
                        note.getTitle(), note.getContent());
            }
        });
        newNotes.setOnClickListener(this);
        folder.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Note> getNotesList(){//get notes list from sqlite database -- Notesdb
        List<Note> noteList = new ArrayList<>();
        String sql = "select * from Notes";
        Cursor cursor = dbutil.rawQuery(sql, null);
        Log.d("SQL", sql);
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("context"));
            String date = cursor.getString(cursor.getColumnIndex("createDate"));
            noteList.add(new Note(title, content, date));
        }
        cursor.close();
        return noteList;
    }

    /*
    TODO:
    1. click newNotes to jump to EditActivity
    2. click item once to get the context and edit it (update)
    3. click item long time to remove the item or edit it (delete or update
    4. input string to search notes, using like and % %
    5. click folder to jump to ShowFolderActivity
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_note:{
                TextView folder_name = (TextView)findViewById(R.id.folder_name);
                EditActivity.actionStart(MainActivity.this, " ");
//                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                intent.putExtra("tiltle", "未命名");
//                intent.putExtra("folder_name", folder_name.getText().toString());
//                intent.putExtra("content", "");
//                startActivity(intent);
            }break;
            case R.id.folder:{

            }break;
            default:
                break;
        }
    }

    public void RefreshNoteList(ArrayList<Map<String , String>> datalist, ListView listView){
        int size = datalist.size();
        if(size > 0){
            datalist.removeAll(datalist);

        }
    }

}
