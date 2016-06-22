package me.nevvea.logger.bean;

import android.database.Cursor;

import java.sql.Time;

import me.nevvea.logger.db.LoggDataHelper;

/**
 * An object that holds a single log message
 *
 * Created by Anna on 6/14/16.
 */
public class LoggItem {
    public int mDay;
    public int mMonth;
    public int mYear;
    public Time mTime;
    public String mMsg;

    public LoggItem() {

    }

    public LoggItem(int day, int month, int year, Time time, String msg) {
        this.mDay = day;
        this.mMonth = month;
        this.mYear = year;
        this.mTime = time;
        this.mMsg = msg;
    }

    public static LoggItem fromCursor(Cursor cursor) {
        LoggItem loggItem = new LoggItem();
        loggItem.mDay = cursor.getInt(cursor.getColumnIndex(LoggDataHelper.LoggDBInfo.COLUMN_LOG_DAY));
        loggItem.mMonth = cursor.getInt(cursor.getColumnIndex(LoggDataHelper.LoggDBInfo.COLUMN_LOG_MONTH));
        loggItem.mYear = cursor.getInt(cursor.getColumnIndex(LoggDataHelper.LoggDBInfo.COLUMN_LOG_YEAR));
        long secs = cursor.getLong(cursor.getColumnIndex(LoggDataHelper.LoggDBInfo.COLUMN_LOG_TIME));
        loggItem.mTime = new Time(secs);
        loggItem.mMsg = cursor.getString(cursor.getColumnIndex(LoggDataHelper.LoggDBInfo.COLUMN_LOG_MSG));
        return loggItem;
    }


}
