package com.isanechek.pilotproject.data;

import android.support.annotation.Nullable;

import com.google.common.base.Objects;


/**
 * Created by isanechek on 11/15/16.
 */

public final class Article {

    private final String artId;

    @Nullable
    private final String artTitle;

    @Nullable
    private final String artDescription;

    @Nullable
    private final String artUrl;

    @Nullable
    private final String artImgUrl;

    @Nullable
    private final String artData;

    private final boolean artCompleted;

    public Article(String artId, @Nullable String artTitle,
                   @Nullable String artDescription,
                   @Nullable String artUrl,
                   @Nullable String artImgUrl,
                   @Nullable String artData, boolean artCompleted) {
        this.artId = artId;
        this.artTitle = artTitle;
        this.artDescription = artDescription;
        this.artUrl = artUrl;
        this.artImgUrl = artImgUrl;
        this.artData = artData;
        this.artCompleted = artCompleted;
    }

    public String getArtId() {
        return artId;
    }

    @Nullable
    public String getArtTitle() {
        return artTitle;
    }

    @Nullable
    public String getArtDescription() {
        return artDescription;
    }

    @Nullable
    public String getArtUrl() {
        return artUrl;
    }

    @Nullable
    public String getArtImgUrl() {
        return artImgUrl;
    }

    @Nullable
    public String getArtDate() {
        return artData;
    }

    public boolean isArtCompleted() {
        return artCompleted;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Article article = (Article) obj;
        return Objects.equal(artId, article.artId) &&
                Objects.equal(artTitle, article.artTitle) &&
                Objects.equal(artDescription, article.artDescription) &&
                Objects.equal(artUrl, article.artUrl) &&
                Objects.equal(artImgUrl, article.artImgUrl) &&
                Objects.equal(artData, article.artData);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(artId, artTitle, artDescription, artUrl, artImgUrl, artData);
    }

    @Override
    public String toString() {
        return "Article with title: " + artTitle;
    }
}
