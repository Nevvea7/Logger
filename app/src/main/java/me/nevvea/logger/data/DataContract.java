package me.nevvea.logger.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Anna on 6/13/16.
 */
public class DataContract {

    public static final String CONTENT_AUTHORITY = "app.nevvea.nomnom";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LOG = "log";


    public static final class LogEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOG).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOG;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOG;

        // Table name
        public static final String TABLE_NAME = "log";

        public static final String COLUMN_LOG_ID = "log_id";
        public static final String COLUMN_LOG_DAY = "log_day";
        public static final String COLUMN_LOG_MONTH = "log_month";
        public static final String COLUMN_LOG_YEAR = "log_year";
        public static final String COLUMN_LOG_TIME = "log_time";
        public static final String COLUMN_LOG_MSG = "log_msg";

        public static Uri buildLogUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildLogWithID(String logid) {
            return CONTENT_URI.buildUpon().appendPath(logid).build();
        }
    }

    public static String getIDFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }
}
