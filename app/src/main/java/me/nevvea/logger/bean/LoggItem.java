package me.nevvea.logger.bean;

import android.database.Cursor;

import java.sql.Time;
import java.util.Calendar;

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
    public int mHour;
    public int mMinute;
    public int mSecond;
    public long mTime;
    public String mMsg;

    public LoggItem() {

    }
    public LoggItem(String msg) {
        Calendar c = Calendar.getInstance();
        this.mYear = c.get(Calendar.YEAR);
        this.mMonth = c.get(Calendar.MONTH);
        this.mDay = c.get(Calendar.DAY_OF_MONTH);
        this.mHour = c.get(Calendar.HOUR_OF_DAY);
        this.mMinute = c.get(Calendar.MINUTE);
        this.mSecond = c.get(Calendar.SECOND);
        this.mTime = c.getTimeInMillis();
        this.mMsg = msg;
    }
    public LoggItem(int day, int month, int year, long time, String msg) {
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
        loggItem.mTime = cursor.getLong(cursor.getColumnIndex(LoggDataHelper.LoggDBInfo.COLUMN_LOG_TIME));
        loggItem.mMsg = cursor.getString(cursor.getColumnIndex(LoggDataHelper.LoggDBInfo.COLUMN_LOG_MSG));
        return loggItem;
    }


}
