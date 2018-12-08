package com.example.user.dao;

import android.provider.BaseColumns;

public class Notes {
    public Notes(){}

    public static abstract class Note implements BaseColumns{
        public static final String TABLE_NAME = "Notes";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COUUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_FOLDER = "folder";
        public static final String COLUMN_NAME_CREATEDATE = "createDate";
        public static final String COLUMN_NAME_CONTEXT = "context";
    }
}
