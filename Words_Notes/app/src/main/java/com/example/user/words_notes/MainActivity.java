package com.example.user.words_notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.LayoutClass.Note;
import com.example.user.LayoutClass.NoteAdapter;
import com.example.user.dao.NotesCRUD;
import com.example.user.dao.NotesDBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener{
    NotesDBHelper notesdb;
    private SQLiteDatabase dbutil;
    private List<Note> noteList = null;
    private String searchStr = "";
    private NotesCRUD noteutill = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link database
        notesdb = new NotesDBHelper(this);
        noteutill = new NotesCRUD(notesdb);
        dbutil = notesdb.getReadableDatabase();

        //find all button or ui
        ListView list = (ListView)findViewById(R.id.listView);
        Button newNotes = (Button)findViewById(R.id.new_note);
        final Button folder = (Button)findViewById(R.id.folder);
        TextView folder_name = (TextView)findViewById(R.id.folder_name);

        /*
        Init ListView
         */
        noteList = noteutill.getAll();
        NoteAdapter adapter = new NoteAdapter(MainActivity.this, R.layout.note_item, noteList);

        list.setAdapter(adapter);

        /*
        UPDATE: Click item to edit
         */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note = noteList.get(i);
                TextView folder_name = (TextView)findViewById(R.id.folder_name);
                EditActivity.actionStart(MainActivity.this, folder_name.getText().toString(),
                        note.getTitle(), note.getContent());
                note = noteList.get(i);
                ReflushItemList(noteList);
            }
        });
        list.setOnItemLongClickListener(this);

        //Search content listener
        SearchView searchView= (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {  // 点击软件盘搜索按钮会弹出 吐司
                Toast.makeText(MainActivity.this, "提交", Toast.LENGTH_SHORT).show();
                return false;
            }
            // 搜索框文本改变事件
            @Override
            public boolean onQueryTextChange(String s) {
                // 文本内容是空就让 recyclerView 填充全部数据 // 可以是其他容器 如listView
                if (s.isEmpty()) {  // 文本工具 检测是否为空，检测空，是输入文本改变 并且为空时触发，刚点击时候虽然为空，但是文本内容没有改变
                    // 设置 容器 的更新
                    searchStr = "";
                    noteList = noteutill.getAll();
                    ReflushItemList(noteList);
                }
                else{
                    searchStr = s;
                    noteList = noteutill.queryByContent(searchStr);
                    ReflushItemList(noteList);
                }
                return false;
            }
        });

        newNotes.setOnClickListener(this);
        folder.setOnClickListener(this);
    }

    public void ReflushItemList(List<Note> noteList){
        ListView list = (ListView)findViewById(R.id.listView);
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
    1. click newNotes to jump to EditActivity. Finished!
    2. click item once to get the context and edit it (update). Finished!
    3. click item long time to remove the item or edit it (delete or update)
    4. input string to search notes, using like and % %. Finished!
    5. click folder to jump to ShowFolderActivity.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_note:{
                TextView folder_name = (TextView)findViewById(R.id.folder_name);
                EditActivity.actionStart(MainActivity.this, folder_name.getText().toString());
            }break;
            case R.id.folder:{

            }break;
            default:
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final int ITEM = i;
        final long L = l;
        TextView text = (TextView)findViewById(R.id.folder_name);
        final String folder = text.getText().toString();
        final String title = noteList.get(ITEM).getTitle();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除");
        builder.setMessage("该操作不可逆,确认删除吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                noteutill.delete(folder, title);
                noteList = getNotesList();
                ReflushItemList(noteList);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create();
        builder.show();
        return false;
    }
}
