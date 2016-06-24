package me.nevvea.logger.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anna on 6/13/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    static final String DATABASE_NAME = "logger.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        LoggDBInfo.TABLE_TITLE.create(sqLiteDatabase);
        LoggDBInfo.TABLE_ALL.create(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LoggDBInfo.TABLE_NAME_TITLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LoggDBInfo.TABLE_NAME_ALL);

        onCreate(sqLiteDatabase);
    }
}
