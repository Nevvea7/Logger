package me.nevvea.logger.db;

import android.content.ContentResolver;
import android.provider.BaseColumns;

import me.nevvea.logger.db.database.Column;
import me.nevvea.logger.db.database.SQLiteTable;

/**
 * Created by Anna on 6/23/16.
 */
public final class LoggDBInfo implements BaseColumns {
    private LoggDBInfo(){

    }

    public static final String TABLE_NAME_TITLE = "log_title";
    public static final String TABLE_NAME_ALL = "log_all";

    public static final String COLUMN_LOG_DAY = "log_day";
    public static final String COLUMN_LOG_MONTH = "log_month";
    public static final String COLUMN_LOG_YEAR = "log_year";
    public static final String COLUMN_LOG_HOUR = "log_hour";
    public static final String COLUMN_LOG_MINUTE = "log_minute";
    public static final String COLUMN_LOG_SECOND = "log_second";
    public static final String COLUMN_LOG_TIME = "log_time";
    public static final String COLUMN_LOG_MSG = "log_msg";
    public static final String COLUMN_LOG_ID = "log_id";


    public static final String CONTENT_TYPE_LOG_TITLE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + DataProvider.CONTENT_AUTHORITY + "/" + DataProvider.PATH_LOG_TITLE;
    public static final String CONTENT_ITEM_TYPE_LOG_TITLE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + DataProvider.CONTENT_AUTHORITY + "/" + DataProvider.PATH_LOG_TITLE;

    public static final String CONTENT_TYPE_DAILY_LOG =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + DataProvider.CONTENT_AUTHORITY + "/" + DataProvider.PATH_DAILY_LOG;
    public static final String CONTENT_ITEM_TYPE_DAILY_LOG =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + DataProvider.CONTENT_AUTHORITY + "/" + DataProvider.PATH_DAILY_LOG;

    public static final SQLiteTable TABLE_TITLE =
            new SQLiteTable(TABLE_NAME_TITLE)
                    .addColumn(COLUMN_LOG_ID, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_DAY, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_MONTH, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_YEAR, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_TIME, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_MSG, Column.DataType.TEXT);

    public static final SQLiteTable TABLE_ALL =
            new SQLiteTable(TABLE_NAME_ALL)
                    .addColumn(COLUMN_LOG_DAY, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_MONTH, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_YEAR, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_HOUR, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_MINUTE, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_SECOND, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_TIME, Column.DataType.INTEGER)
                    .addColumn(COLUMN_LOG_MSG, Column.DataType.TEXT);

}