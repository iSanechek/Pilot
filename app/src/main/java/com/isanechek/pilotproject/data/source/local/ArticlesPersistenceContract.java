package com.isanechek.pilotproject.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by isanechek on 11/15/16.
 */

public class ArticlesPersistenceContract {
    public ArticlesPersistenceContract() {}

    public static abstract class ArticleEntry implements BaseColumns {
        public static final String TABLE_NAME = "article";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_IMG_URL ="imgurl";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_COMPLETED = "completed";
    }
}
