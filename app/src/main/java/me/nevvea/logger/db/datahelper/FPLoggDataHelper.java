package me.nevvea.logger.db.datahelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import java.util.List;

import me.nevvea.logger.R;
import me.nevvea.logger.app.LoggerApplication;
import me.nevvea.logger.bean.LoggItem;
import me.nevvea.logger.bean.LoggTitle;
import me.nevvea.logger.db.DBInterface;
import me.nevvea.logger.db.DataProvider;
import me.nevvea.logger.db.LoggDBInfo;

/**
 * Created by Anna on 6/15/16.
 */
public class FPLoggDataHelper extends BaseDataHelper implements DBInterface<LoggTitle> {

    public FPLoggDataHelper(Context context) {
        super(context);
    }
    @Override
    protected Uri getContentUri() {
        return DataProvider.TITLE_LOG_URI;
    }

    @Override
    protected String getTableName() {
        return LoggDBInfo.TABLE_NAME_TITLE;
    }

    @Override
    public LoggTitle query(String id) {
        return null;
    }

    @Override
    public int clearAll() {
        return 0;
    }

    public Uri buildUriWithYearMonthDay(int year, int month, int day) {
        return getContentUri()
                .buildUpon()
                .appendEncodedPath(
                LoggerApplication
                        .getContext()
                        .getString(R.string.uri_year_month_day, year, month, day))
                .build();
    }

    public LoggTitle query(int year, int month, int day) {
        LoggTitle loggTitle = null;
        Cursor cursor = query(
                buildUriWithYearMonthDay(year, month, day),
                null,
                null,
                null,
                LoggDBInfo.COLUMN_LOG_TIME + " DESC");
        if (cursor.moveToFirst()) {
            loggTitle = LoggTitle.fromCursor(cursor);
        }
        cursor.close();
        return loggTitle;
    }

    public void updateTitle(LoggItem loggItem) {
        LoggTitle prevLoggTitle = query(loggItem.mYear, loggItem.mMonth, loggItem.mDay);
        LoggTitle newLoggTitle = new LoggTitle(loggItem);

        if (prevLoggTitle == null) {
            insert(newLoggTitle);
        } else if (prevLoggTitle.loggId != -1) {
            int id = update(
                    buildUriWithYearMonthDay(loggItem.mYear, loggItem.mMonth, loggItem.mDay),
                    getContentValues(newLoggTitle),
                    null,
                    null
            );
            Log.d("update check", id + "");
        }
    }

    @Override
    public void bulkInsert(List<LoggTitle> listData) {

    }

    @Override
    public void insert(LoggTitle data) {
        ContentValues value = getContentValues(data);
        String s = insert(value).getLastPathSegment();
        Log.d("id check", s);
    }

    @Override
    public ContentValues getContentValues(LoggTitle data) {
        ContentValues values = new ContentValues();
        values.put(LoggDBInfo.COLUMN_LOG_DAY, data.day);
        values.put(LoggDBInfo.COLUMN_LOG_MONTH, data.month);
        values.put(LoggDBInfo.COLUMN_LOG_YEAR, data.year);
        values.put(LoggDBInfo.COLUMN_LOG_TIME, data.time);
        values.put(LoggDBInfo.COLUMN_LOG_MSG, data.title);
        values.put(LoggDBInfo.COLUMN_LOG_ID, data.loggId);
        return values;
    }

    @Override
    public CursorLoader getCursorLoader() {
        return new CursorLoader(
                getContext(),
                getContentUri(),
                null,
                null,
                null,
                LoggDBInfo.COLUMN_LOG_TIME + " DESC");
    }

}
