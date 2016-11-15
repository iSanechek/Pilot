package com.isanechek.pilotproject.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.isanechek.pilotproject.data.Article;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by isanechek on 11/15/16.
 */

public class Repository implements DataSource {

    @Nullable
    private static Repository INSTANCE = null;

    @NonNull
    private final DataSource remoteDataSource;

    @NonNull
    private final DataSource localDataSource;

    @Nullable
    private Map<String, Article> cachedArticles;

    private boolean mCacheIsDirty = false;

    private Repository(@NonNull DataSource remoteDataSource,
                       @NonNull DataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static Repository getInstance(@NonNull DataSource remoteDataSource,
                                         @NonNull DataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Article>> getArticleList() {
        if (cachedArticles != null && !mCacheIsDirty) {
            return Observable.from(cachedArticles.values()).toList();
        } else if (cachedArticles == null) {
            cachedArticles = new LinkedHashMap<>();
        }

        Observable<List<Article>> remoteArticles = getAndSaveRemoteArticles();
        if (mCacheIsDirty) {
            return remoteArticles;
        } else {
            Observable<List<Article>> localArticles = getAndCacheLocalArticles();
            return Observable.concat(localArticles, remoteArticles)
                    .filter(new Func1<List<Article>, Boolean>() {
                        @Override
                        public Boolean call(List<Article> articles) {
                            return !articles.isEmpty();
                        }
                    }).first();
        }
    }

    private Observable<List<Article>> getAndCacheLocalArticles() {
        return localDataSource.getArticleList()
                .flatMap(new Func1<List<Article>, Observable<List<Article>>>() {
                    @Override
                    public Observable<List<Article>> call(List<Article> articles) {
                        return Observable.from(articles).doOnNext(new Action1<Article>() {
                            @Override
                            public void call(Article article) {
                                checkNotNull(cachedArticles);
                                cachedArticles.put(article.getArtId(), article);
                            }
                        }).toList();
                    }
                });
    }

    private Observable<List<Article>> getAndSaveRemoteArticles() {
        return remoteDataSource
                .getArticleList()
                .flatMap(new Func1<List<Article>, Observable<List<Article>>>() {
                    @Override
                    public Observable<List<Article>> call(List<Article> articles) {
                        return Observable.from(articles).doOnNext(new Action1<Article>() {
                            @Override
                            public void call(Article article) {
                                localDataSource.saveArticle(article);
                                checkNotNull(cachedArticles);
                                cachedArticles.put(article.getArtId(), article);
                            }
                        }).toList();
                    }
                }).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        mCacheIsDirty = false;
                    }
                });
    }

    @Override
    public Observable<Article> getArticle(@NonNull String artId) {
        return null;
    }

    @Override
    public Observable<String> getSourceArticle(@NonNull String artId) {
        return null;
    }

    @Override
    public void saveArticle(@NonNull Article article) {

    }

    @Override
    public void completeArticle(@NonNull Article article) {

    }

    @Override
    public void completeArticle(@NonNull String artId) {

    }

    @Override
    public void refreshArticle() {

    }

    @Override
    public void deleteAllArticle() {

    }

    @Override
    public void clearAllOfflineArticle() {

    }

    @Override
    public void deleteOfflineArticle(@NonNull String artId) {

    }


}
