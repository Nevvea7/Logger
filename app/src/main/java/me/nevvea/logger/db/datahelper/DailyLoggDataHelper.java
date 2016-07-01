package me.nevvea.logger.db.datahelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.List;

import me.nevvea.logger.R;
import me.nevvea.logger.app.LoggerApplication;
import me.nevvea.logger.bean.LoggItem;
import me.nevvea.logger.bean.LoggTitle;
import me.nevvea.logger.db.DBInterface;
import me.nevvea.logger.db.DataProvider;
import me.nevvea.logger.db.LoggDBInfo;

/**
 * Created by Anna on 6/23/16.
 */
public class DailyLoggDataHelper extends BaseDataHelper implements DBInterface<LoggItem> {

    public DailyLoggDataHelper(Context context) {
        super(context);
    }
    @Override
    protected Uri getContentUri() {
        return DataProvider.DAILY_LOG_URI;
    }

    @Override
    protected String getTableName() {
        return LoggDBInfo.TABLE_NAME_ALL;
    }

    @Override
    public LoggItem query(String id) {
        return null;
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

    public Uri buildUriWithId(int id) {
        return getContentUri()
                .buildUpon()
                .appendPath(Integer.toString(id))
                .build();
    }

    @Override
    public int clearAll() {
        return 0;
    }

    @Override
    public void bulkInsert(List<LoggItem> listData) {

    }

    @Override
    public void insert(LoggItem data) {
        ContentValues value = getContentValues(data);
        insert(value);
    }

    public void delete(LoggItem data) {
        delete(buildUriWithId(data.mId));
    }

    @Override
    public ContentValues getContentValues(LoggItem data) {
        ContentValues values = new ContentValues();
        values.put(LoggDBInfo.COLUMN_LOG_DAY, data.mDay);
        values.put(LoggDBInfo.COLUMN_LOG_MONTH, data.mMonth);
        values.put(LoggDBInfo.COLUMN_LOG_YEAR, data.mYear);
        values.put(LoggDBInfo.COLUMN_LOG_HOUR, data.mHour);
        values.put(LoggDBInfo.COLUMN_LOG_MINUTE, data.mMinute);
        values.put(LoggDBInfo.COLUMN_LOG_SECOND, data.mSecond);
        values.put(LoggDBInfo.COLUMN_LOG_TIME, data.mTime);
        values.put(LoggDBInfo.COLUMN_LOG_MSG, data.mMsg);
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
                LoggDBInfo.COLUMN_LOG_TIME + " ASC"
        );
    }

    public CursorLoader getCursorLoader(LoggTitle loggTitle) {
        return new CursorLoader(
                getContext(),
                buildUriWithYearMonthDay(loggTitle.year, loggTitle.month, loggTitle.day),
                null,
                null,
                null,
                LoggDBInfo.COLUMN_LOG_TIME + " ASC"
        );
    }
}
