package me.nevvea.logger.bean;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import me.nevvea.logger.db.LoggDBInfo;

/**
 * Created by Anna on 6/26/16.
 */
public class LoggTitle implements Parcelable {
    public int year;
    public int month;
    public int day;
    public int id;
    public int loggId;
    public long time;
    public String title;

    public static final String TAG = "LOGGTITLE_TAG";

    public LoggTitle() {

    }

    public LoggTitle(LoggTitle oldTitle, String newTitle) {
        this.year = oldTitle.year;
        this.month = oldTitle.month;
        this.day = oldTitle.day;
        this.id = oldTitle.id;
        this.loggId = oldTitle.loggId;
        this.time = oldTitle.time;
        this.title = newTitle;
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

    protected LoggTitle(Parcel in) {
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        loggId = in.readInt();
        time = in.readLong();
        title = in.readString();
    }

    public static final Creator<LoggTitle> CREATOR = new Creator<LoggTitle>() {
        @Override
        public LoggTitle createFromParcel(Parcel in) {
            return new LoggTitle(in);
        }

        @Override
        public LoggTitle[] newArray(int size) {
            return new LoggTitle[size];
        }
    };

    public static LoggTitle fromCursor(Cursor cursor) {
        LoggTitle title = new LoggTitle();
        title.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        title.year = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_YEAR));
        title.month = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_MONTH));
        title.day = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_DAY));
        title.loggId = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_ID));
        title.time = cursor.getInt(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_TIME));
        title.title = cursor.getString(cursor.getColumnIndex(LoggDBInfo.COLUMN_LOG_MSG));
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(loggId);
        dest.writeLong(time);
        dest.writeString(title);
    }
}
