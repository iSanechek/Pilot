package com.isanechek.pilotproject.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by isanechek on 11/15/16.
 */

public class ArticlesDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Beardycast.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ArticlesPersistenceContract.ArticleEntry.TABLE_NAME + " (" +
                    ArticlesPersistenceContract.ArticleEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    ArticlesPersistenceContract.ArticleEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticleEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticleEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticleEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticleEntry.COLUMN_NAME_IMG_URL + TEXT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticleEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticleEntry.COLUMN_NAME_COMPLETED + BOOLEAN_TYPE +
                    " )";

    public ArticlesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
