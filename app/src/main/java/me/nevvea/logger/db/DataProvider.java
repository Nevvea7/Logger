package me.nevvea.logger.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import me.nevvea.logger.util.Utilities;

/**
 * Created by Anna on 6/13/16.
 */
public class DataProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "me.nevvea.logger";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LOG_TITLE = "log_title";
    public static final String PATH_DAILY_LOG = "daily_log";

    public static final Uri TITLE_LOG_URI =
            BASE_CONTENT_URI
                    .buildUpon()
                    .appendPath(PATH_LOG_TITLE)
                    .build();

    public static final Uri DAILY_LOG_URI =
            BASE_CONTENT_URI
                    .buildUpon()
                    .appendPath(PATH_DAILY_LOG)
                    .build();

    private static final String sTitleYearMonthDaySelection =
            LoggDBInfo.TABLE_NAME_TITLE + '.' +
                    LoggDBInfo.COLUMN_LOG_YEAR + " = ? AND " +
                    LoggDBInfo.COLUMN_LOG_MONTH + " = ? AND " +
                    LoggDBInfo.COLUMN_LOG_DAY + " = ? ";

    private static final String sLoggYearMonthDaySelection =
            LoggDBInfo.TABLE_NAME_ALL + '.' +
                    LoggDBInfo.COLUMN_LOG_YEAR + " = ? AND " +
                    LoggDBInfo.COLUMN_LOG_MONTH + " = ? AND " +
                    LoggDBInfo.COLUMN_LOG_DAY + " = ? ";


    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper mOpenHelper;

    static final int LOG = 100;
    static final int LOG_WITH_ID = 101;
    static final int LOG_WITH_YEAR_MONTH_DAY = 102;
    static final int ALL_LOG_TITLE = 200;
    static final int LOG_TITLE_YEAR_MONTH_DAY = 201;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, PATH_DAILY_LOG, LOG);
        matcher.addURI(authority, PATH_LOG_TITLE, ALL_LOG_TITLE);
        matcher.addURI(authority, PATH_LOG_TITLE + "/year/#/month/#/day/#", LOG_TITLE_YEAR_MONTH_DAY);
        matcher.addURI(authority, PATH_DAILY_LOG + "/year/#/month/#/day/#", LOG_WITH_YEAR_MONTH_DAY);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(matchTable(uri));

        Logger.d(uri);
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        retCursor = queryBuilder.query(
                db,
                projection,
                matchSelection(uri),
                matchSelectionArgs(uri),
                null,
                null,
                sortOrder
        );

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        Logger.d(retCursor.getCount());
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case LOG:
                return LoggDBInfo.CONTENT_TYPE_DAILY_LOG;
            case LOG_WITH_ID:
                return LoggDBInfo.CONTENT_ITEM_TYPE_DAILY_LOG;
            case LOG_WITH_YEAR_MONTH_DAY:
                return LoggDBInfo.CONTENT_TYPE_DAILY_LOG;
            case ALL_LOG_TITLE:
                return LoggDBInfo.CONTENT_TYPE_LOG_TITLE;
            case LOG_TITLE_YEAR_MONTH_DAY:
                return LoggDBInfo.CONTENT_ITEM_TYPE_LOG_TITLE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = 0;
        db.beginTransaction();
        try {
            rowId = db.insert(matchTable(uri), null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        if (rowId > 0) {
            Uri returnUri = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(uri, null);
            return returnUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = 0;
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            rowsUpdated = db.update(matchTable(uri), values, selection, selectionArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    private String matchTable(Uri uri) {
        String table;
        Logger.d(sUriMatcher.match(uri));
        switch (sUriMatcher.match(uri)) {
            case LOG:
                table = LoggDBInfo.TABLE_NAME_ALL;
                break;
            case LOG_WITH_ID:
                table = LoggDBInfo.TABLE_NAME_ALL;
                break;
            case LOG_WITH_YEAR_MONTH_DAY:
                table = LoggDBInfo.TABLE_NAME_ALL;
                break;
            case ALL_LOG_TITLE:
                table = LoggDBInfo.TABLE_NAME_TITLE;
                break;
            case LOG_TITLE_YEAR_MONTH_DAY:
                table = LoggDBInfo.TABLE_NAME_TITLE;
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }
        return table;
    }

    private String matchSelection(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case LOG:
                return null;
            case LOG_WITH_ID:
                return null;
            case LOG_WITH_YEAR_MONTH_DAY:
                return sLoggYearMonthDaySelection;
            case ALL_LOG_TITLE:
                return null;
            case LOG_TITLE_YEAR_MONTH_DAY:
                return sTitleYearMonthDaySelection;
            default:
                throw new IllegalArgumentException("Unknown Uri" + uri);
        }
    }

    private String[] matchSelectionArgs(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case LOG:
                return null;
            case LOG_WITH_ID:
                return null;
            case LOG_WITH_YEAR_MONTH_DAY:
                return Utilities.getYearMonthDayFromUri(uri);
            case ALL_LOG_TITLE:
                return null;
            case LOG_TITLE_YEAR_MONTH_DAY:
                return Utilities.getYearMonthDayFromUri(uri);
            default:
                throw new IllegalArgumentException("Unknown Uri" + uri);
        }
    }

}
