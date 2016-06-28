package me.nevvea.logger.bean;

import android.database.Cursor;

import me.nevvea.logger.db.LoggDBInfo;

/**
 * Created by Anna on 6/26/16.
 */
public class LoggTitle {
    public int year;
    public int month;
    public int day;
    public int loggId;
    public long time;
    public String title;

    public LoggTitle() {

    }

    public LoggTitle(int loggId) {

    }

    public LoggTitle(LoggItem loggItem) {
        this.year = loggItem.mYear;
        this.month = loggItem.mMonth;
        this.day = loggItem.mDay;
        this.loggId = 1;
        this.time = loggItem.mTime;
        this.title = loggItem.mMsg;
    }

    public static LoggTitle fromCursor(Cursor cursor) {
        LoggTitle title = new LoggTitle();
        title.year = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_YEAR));
        title.month = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_MONTH));
        title.day = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_DAY));
        title.loggId = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_ID));
        title.time = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_TIME));
        title.title = cursor.getString(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_MSG));
        return title;
    }
}
