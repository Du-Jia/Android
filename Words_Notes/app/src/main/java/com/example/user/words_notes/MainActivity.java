package com.example.user.words_notes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.user.LayoutClass.Note;
import com.example.user.LayoutClass.NoteAdapter;
import com.example.user.dao.NotesDBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    NotesDBHelper notesdb;
    private SQLiteDatabase dbutil;
    private List<Note> noteList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link database
        notesdb = new NotesDBHelper(this);
        dbutil = notesdb.getReadableDatabase();

        ListView list = (ListView)findViewById(R.id.listView);

        noteList = getNotesList();
        NoteAdapter adapter = new NoteAdapter(MainActivity.this, R.layout.note_item, noteList);

        list.setAdapter(adapter);
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

    public List<Note> getNotesList(){
        List<Note> noteList = new ArrayList<>();
        String sql = "select * from Notes";
        Cursor cursor = dbutil.rawQuery(sql, null);
        Log.d("SQL", sql);
//        ArrayList<Map<String, String>> adpter = new ArrayList<Map<String, String>>();
        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("context"));
            noteList.add(new Note(title, content));
//            Map<String, String> tmp = new HashMap<String, String>();
//            tmp.put("title", title);
//            tmp.put("content", content);
//            adpter.add(tmp);
        }
        cursor.close();
        return noteList;
    }

    public void RefreshNoteList(ArrayList<Map<String , String>> datalist, ListView listView){
        int size = datalist.size();
        if(size > 0){
            datalist.removeAll(datalist);

        }
    }
}
