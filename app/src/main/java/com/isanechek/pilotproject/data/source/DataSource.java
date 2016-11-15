package com.isanechek.pilotproject.data.source;

import android.support.annotation.NonNull;

import com.isanechek.pilotproject.data.Article;

import java.util.List;

import rx.Observable;

/**
 * Created by isanechek on 11/15/16.
 */

public interface DataSource {

    Observable<List<Article>> getArticleList();
    Observable<Article> getArticle(@NonNull String artId);
    Observable<String> getSourceArticle(@NonNull String artId);

    void saveArticle(@NonNull Article article);

    void completeArticle(@NonNull Article article);

    void completeArticle(@NonNull String artId);

    void refreshArticle();

    void deleteAllArticle();

    void clearAllOfflineArticle();

    void deleteOfflineArticle(@NonNull String artId);
}
