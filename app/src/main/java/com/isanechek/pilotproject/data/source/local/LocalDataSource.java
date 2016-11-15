package com.isanechek.pilotproject.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.isanechek.pilotproject.data.Article;
import com.isanechek.pilotproject.data.source.DataSource;
import com.isanechek.pilotproject.utils.schedulers.BaseSchedulerProvider;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.isanechek.pilotproject.data.source.local.ArticlesPersistenceContract.ArticleEntry;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by isanechek on 11/15/16.
 */

public class LocalDataSource implements DataSource {

    @Nullable
    private static LocalDataSource INSTANCE;

    @NonNull
    private final BriteDatabase database;

    @NonNull
    private Func1<Cursor, Article> articleMapperFunction;

    private LocalDataSource(@NonNull Context context,
                            @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        ArticlesDbHelper dbHelper = new ArticlesDbHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        database = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
        articleMapperFunction = new Func1<Cursor, Article>() {
            @Override
            public Article call(Cursor c) {
                String id = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_NAME_TITLE));
                String description = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_NAME_DESCRIPTION));
                String url = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_NAME_URL));
                String imgUrl = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_NAME_IMG_URL));
                String date = c.getString(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_NAME_DATE));
                boolean completed = c.getInt(c.getColumnIndexOrThrow(ArticleEntry.COLUMN_NAME_COMPLETED)) == 1;
                return new Article(id, title, description, url, imgUrl, date, completed);
            }
        };
    }

    public static LocalDataSource getInstance(@NonNull Context context,
                                              @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Article>> getArticleList() {
        String[] projection = {
                ArticleEntry.COLUMN_NAME_ENTRY_ID,
                ArticleEntry.COLUMN_NAME_TITLE,
                ArticleEntry.COLUMN_NAME_DESCRIPTION,
                ArticleEntry.COLUMN_NAME_URL,
                ArticleEntry.COLUMN_NAME_IMG_URL,
                ArticleEntry.COLUMN_NAME_DATE,
                ArticleEntry.COLUMN_NAME_COMPLETED
        };
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), ArticleEntry.TABLE_NAME);
        return database.createQuery(ArticleEntry.TABLE_NAME, sql).mapToList(articleMapperFunction);
    }

    @Override
    public Observable<Article> getArticle(@NonNull String artId) {
        String[] projection = {
                ArticleEntry.COLUMN_NAME_ENTRY_ID,
                ArticleEntry.COLUMN_NAME_TITLE,
                ArticleEntry.COLUMN_NAME_DESCRIPTION,
                ArticleEntry.COLUMN_NAME_URL,
                ArticleEntry.COLUMN_NAME_IMG_URL,
                ArticleEntry.COLUMN_NAME_DATE,
                ArticleEntry.COLUMN_NAME_COMPLETED
        };
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?", TextUtils.join(",", projection),
                ArticleEntry.TABLE_NAME, ArticleEntry.COLUMN_NAME_ENTRY_ID);
        return database.createQuery(ArticleEntry.TABLE_NAME, sql, artId).mapToOneOrDefault(articleMapperFunction, null);
    }

    @Override
    public Observable<String> getSourceArticle(@NonNull String artId) {
        // Not required
        return null;
    }

    @Override
    public void saveArticle(@NonNull Article a) {
        checkNotNull(a);
        ContentValues values = new ContentValues();
        values.put(ArticleEntry.COLUMN_NAME_ENTRY_ID, a.getArtId());
        values.put(ArticleEntry.COLUMN_NAME_TITLE, a.getArtTitle());
        values.put(ArticleEntry.COLUMN_NAME_DESCRIPTION, a.getArtDescription());
        values.put(ArticleEntry.COLUMN_NAME_URL, a.getArtUrl());
        values.put(ArticleEntry.COLUMN_NAME_IMG_URL, a.getArtImgUrl());
        values.put(ArticleEntry.COLUMN_NAME_DATE, a.getArtDate());
        values.put(ArticleEntry.COLUMN_NAME_COMPLETED, a.isArtCompleted());
        database.insert(ArticleEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void completeArticle(@NonNull Article article) {
        completeArticle(article.getArtId());
    }

    @Override
    public void completeArticle(@NonNull String artId) {
        ContentValues values = new ContentValues();
        values.put(ArticleEntry.COLUMN_NAME_COMPLETED, true);
        String selection = ArticleEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {artId};
        database.update(ArticleEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @Override
    public void refreshArticle() {
        // Not required
    }

    @Override
    public void deleteAllArticle() {
        database.delete(ArticleEntry.TABLE_NAME, null);
    }

    @Override
    public void clearAllOfflineArticle() {

    }

    @Override
    public void deleteOfflineArticle(@NonNull String artId) {

    }
}
