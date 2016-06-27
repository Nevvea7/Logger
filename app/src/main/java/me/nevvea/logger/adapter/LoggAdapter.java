package me.nevvea.logger.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import me.nevvea.logger.R;
import me.nevvea.logger.bean.LoggItem;

/**
 * Created by Anna on 6/20/16.
 */
public class LoggAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {
    private final LayoutInflater mLayoutInflater;

    public LoggAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        LoggItem loggItem = LoggItem.fromCursor(cursor);
        ((FullLoggViewHolder) holder).mTVLoggMonth.setText(getMonth(loggItem.mMonth));
        ((FullLoggViewHolder) holder).mTVLoggDay.setText(loggItem.mDay + "");
        ((FullLoggViewHolder) holder).mTVLoggSum.setText(loggItem.mMsg);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FullLoggViewHolder(mLayoutInflater.inflate(R.layout.item_logg_titles, parent, false), this);
    }

    public static class FullLoggViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_logg_month)
        TextView mTVLoggMonth;
        @BindView(R.id.item_logg_day)
        TextView mTVLoggDay;
        @BindView(R.id.item_logg_summary)
        TextView mTVLoggSum;

        LoggAdapter mAdapter;

        public FullLoggViewHolder(View itemView, LoggAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mAdapter = adapter;
        }
    }

    private String getMonth(int month) {
        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "???";
        }
    }
}
