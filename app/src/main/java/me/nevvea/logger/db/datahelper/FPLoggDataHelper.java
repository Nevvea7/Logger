package me.nevvea.logger.db.datahelper;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import java.util.List;

import me.nevvea.logger.bean.LoggItem;
import me.nevvea.logger.db.DBInterface;
import me.nevvea.logger.db.DataProvider;
import me.nevvea.logger.db.LoggDBInfo;
import me.nevvea.logger.db.datahelper.BaseDataHelper;

/**
 * Created by Anna on 6/15/16.
 */
public class FPLoggDataHelper extends BaseDataHelper implements DBInterface<LoggItem> {

    public FPLoggDataHelper(Context context) {
        super(context);
    }
    @Override
    protected Uri getContentUri() {
        return DataProvider.ALL_LOG_URI;
    }

    @Override
    protected String getTableName() {
        return LoggDBInfo.TABLE_NAME_TITLE;
    }

    @Override
    public LoggItem query(String id) {
        return null;
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
        String s = insert(value).getLastPathSegment();
        Log.d("id check", s);
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
                LoggDBInfo.COLUMN_LOG_TIME + " DESC");
    }

}
