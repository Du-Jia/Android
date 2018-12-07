package com.example.user.words_notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Notes";

    //Create a table
    private static final String SQL_CREATE_TABLE = "create table "
            + Notes.Note.TABLE_NAME + "(" +
            Notes.Note.COLUMN_NAME_ID + " varchar(20) primary key ," +
            Notes.Note.COLUMN_NAME_FOLDER + " varchar(40) ," +
            Notes.Note.COUUMN_NAME_TITLE + " varchar(40) unique," +
            Notes.Note.COLUMN_NAME_CREATEDATE + " date not null," +
            Notes.Note.COLUMN_NAME_CONTEXT + " text not null)";
    //Version of database
    private static final int DATABASE_VERSION = 1;

    //drop ths database
    private static final String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Notes.Note.TABLE_NAME;

    public NotesDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }
}
