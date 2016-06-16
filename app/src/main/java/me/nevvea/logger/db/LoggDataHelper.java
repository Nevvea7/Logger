package me.nevvea.logger.db;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.CursorLoader;

import java.util.List;

import me.nevvea.logger.bean.LoggItem;
import me.nevvea.logger.db.database.Column;
import me.nevvea.logger.db.database.SQLiteTable;

/**
 * Created by Anna on 6/15/16.
 */
public class LoggDataHelper extends BaseDataHelper implements DBInterface<LoggItem> {

    public LoggDataHelper(Context context) {
        super(context);
    }
    @Override
    protected Uri getContentUri() {
        return DataProvider.ALL_LOG_URI;
    }

    @Override
    protected String getTableName() {
        return LoggDBInfo.TABLE_NAME;
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
    public ContentValues getContentValues(LoggItem data) {
        return null;
    }

    @Override
    public CursorLoader getCursorLoader() {
        return null;
    }


    public static final class LoggDBInfo implements BaseColumns {
        private LoggDBInfo(){

        }

        public static final String TABLE_NAME = "log";

        public static final String COLUMN_LOG_ID = "log_id";
        public static final String COLUMN_LOG_DAY = "log_day";
        public static final String COLUMN_LOG_MONTH = "log_month";
        public static final String COLUMN_LOG_YEAR = "log_year";
        public static final String COLUMN_LOG_TIME = "log_time";
        public static final String COLUMN_LOG_MSG = "log_msg";

        public static final SQLiteTable TABLE =
                new SQLiteTable(TABLE_NAME)
                .addColumn(COLUMN_LOG_ID, Column.DataType.INTEGER)
                .addColumn(COLUMN_LOG_DAY, Column.DataType.INTEGER)
                .addColumn(COLUMN_LOG_MONTH, Column.DataType.INTEGER)
                .addColumn(COLUMN_LOG_YEAR, Column.DataType.INTEGER)
                .addColumn(COLUMN_LOG_TIME, Column.DataType.INTEGER)
                .addColumn(COLUMN_LOG_MSG, Column.DataType.TEXT);

    }
}
