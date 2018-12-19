package com.example.user.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.user.LayoutClass.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesCRUD {
    private NotesDBHelper notesdb;
    private SQLiteDatabase db;

    public NotesCRUD(NotesDBHelper notesdb){
        this.notesdb = notesdb;
    }

    public boolean update(String folder, String title, String content){
        boolean status = false;
        if(folder.equals("") || folder == null || title.equals("") || title == null){
            Log.d("InvalidData", "Insert failed caused by empty folder/title/context");
            return status;
        }
        else{
            db = notesdb.getWritableDatabase();
            String date = getCurrentDate();
            String sql = "UPDATE " + Notes.Note.TABLE_NAME
                    + " SET " + Notes.Note.COLUMN_NAME_CONTEXT + "='" + content + "', "
                    + Notes.Note.COLUMN_NAME_CREATEDATE + "='" + date
                    + "' WHERE " +
                    Notes.Note.COLUMN_NAME_FOLDER + "='" + folder
                    + "' AND "
                    + Notes.Note.COUUMN_NAME_TITLE + "='" + title + "'";
            db.execSQL(sql);
            Log.d("SQL", sql);
            status = true;
        }
        return status;
    }

    public boolean delete(String folder, String title){
        boolean status = false;
        String sql = "DELETE from " + Notes.Note.TABLE_NAME + " WHERE "
                + Notes.Note.COLUMN_NAME_FOLDER + "='" + folder + "' AND "
                + Notes.Note.COUUMN_NAME_TITLE + "='" + title + "'";
        db.execSQL(sql);
        return status;
    }

    /*
    TODO:
    1. implement query method by sql
    2. order by date
    3. use 'like'
     */
    public List<Note> query(String folder, String title, String date){
        List<Note> noteList = new ArrayList<>();
        String [] attrs = {
                Notes.Note.COLUMN_NAME_ID,
                Notes.Note.COLUMN_NAME_FOLDER,
                Notes.Note.COUUMN_NAME_TITLE,
                Notes.Note.COLUMN_NAME_CREATEDATE,
                Notes.Note.COLUMN_NAME_CONTEXT
            };
        return noteList;
    }

    public List<Note> queryByContent( String con){
        List<Note> noteList = new ArrayList<>();
        String sql = "SELECT * FROM Notes WHERE " +
                Notes.Note.COLUMN_NAME_CONTEXT + " LIKE '%"
                + con + "%'";
        db = notesdb.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
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

    public List<Note> queryByTitle(String title){
        List<Note> noteList = null;

        return noteList;
    }

    public List<Note> getAll(){
        List<Note> noteList = new ArrayList<>();
        db = notesdb.getReadableDatabase();
        String sql = "select * from Notes" ;
        Cursor cursor = db.rawQuery(sql, null);
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

    public boolean insert(String folder, String title, String context){
        String date = getCurrentDate();
        boolean status = false;
        if(folder.equals("") || folder == null || title.equals("") || title == null
                || date.equals("") || date == null || context.equals("") || context == null ){
            Log.d("InvalidData", "Insert failed caused by empty folder/title/date/context");
            return status;
        }
        else{
            db = notesdb.getWritableDatabase();
            String link = "','";
            String sql = "INSERT INTO " + Notes.Note.TABLE_NAME + " VALUES ('"
                    + getId(folder,title) + link
                    + folder + link
                    + title + link
                    + date + link
                    + context + "')";
            db.execSQL(sql);
            Log.d("SQL", sql);
            status = true;
        }
        return status;
    }

    //v1 id
    private String getId(String title){
        String id = "";
        for(int i = 0; i < title.length(); i++){
            id += (int)title.charAt(i);
        }
        return id;
    }

    private String getId(String folder, String title){
        String id = "";
        for(int i = 0; i < title.length(); i++){
            id += (int)title.charAt(i);
        }
        for(int i = 0; i < folder.length(); i++){
            id += (int)folder.charAt(i);
        }
        return id;
    }

    private String getCurrentDate(){
        String formatDate = "0000-00-00";
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YY-MM-DD");
        formatDate = sdf.format(d);
        Log.d("Date", formatDate);
        formatDate = "2018-12-14";
        return formatDate;
    }

}
