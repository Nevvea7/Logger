package me.nevvea.logger.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import me.nevvea.logger.db.DataContract.LogEntry;

/**
 * Created by Anna on 6/13/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "logger.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + LogEntry.TABLE_NAME + " (" +
                LogEntry._ID + " INTEGER PRIMARY KEY, " +
                LogEntry.COLUMN_LOG_ID + " TEXT NOT NULL, " +
                LogEntry.COLUMN_LOG_DAY + " INTEGER NOT NULL " +
                LogEntry.COLUMN_LOG_MONTH + " INTEGER NOT NULL " +
                LogEntry.COLUMN_LOG_YEAR + " INTEGER NOT NULL " +
                LogEntry.COLUMN_LOG_TIME + " INTEGER NOT NULL " +
                LogEntry.COLUMN_LOG_MSG + " TEXT NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LogEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
