package com.isanechek.pilotproject.data.source.remote;

import android.support.annotation.NonNull;

import com.isanechek.pilotproject.Constants;
import com.isanechek.pilotproject.data.Article;
import com.isanechek.pilotproject.data.source.DataSource;
import com.isanechek.pilotproject.data.source.remote.parser.Parser;

import java.util.List;

import rx.Observable;

/**
 * Created by isanechek on 11/15/16.
 */

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private RemoteDataSource() {}

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Article>> getArticleList() {
        List<Article> list = Parser.parseList(Constants.HOME_LINK);
        if (list.size() != 0) {
            return Observable.from(list).toList();
        } else {
            return Observable.empty();
        }
    }

    @Override
    public Observable<Article> getArticle(@NonNull String artId) {
        // Not required
        return null;
    }

    @Override
    public Observable<String> getSourceArticle(@NonNull String url) {
        final String source = Parser.getDetailArticleContentBody(url);
        if (source != null) {
            return Observable.just(source);
        } else {
            return Observable.empty();
        }
    }

    @Override
    public void saveArticle(@NonNull Article article) {
        // Not required
    }

    @Override
    public void completeArticle(@NonNull Article article) {
        // Not required
    }

    @Override
    public void completeArticle(@NonNull String artId) {
        // Not required
    }

    @Override
    public void refreshArticle() {
        // Not required
    }

    @Override
    public void deleteAllArticle() {
        // Not required
    }

    @Override
    public void clearAllOfflineArticle() {
        // Not required
    }

    @Override
    public void deleteOfflineArticle(@NonNull String artId) {
        // Not required
    }
}
