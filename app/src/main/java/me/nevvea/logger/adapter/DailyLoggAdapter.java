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
import me.nevvea.logger.db.datahelper.DailyLoggDataHelper;

/**
 * Created by Anna on 6/27/16.
 */
public class DailyLoggAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;

    public DailyLoggAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        LoggItem loggItem = LoggItem.fromCursor(cursor);
        ((FullLoggViewHolder) holder).mTVLogg.setText(loggItem.mMsg);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FullLoggViewHolder(mLayoutInflater.inflate(R.layout.item_logg_daily, parent, false), this);
    }

    public static class FullLoggViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_daily_logg_textview)
        TextView mTVLogg;

        DailyLoggAdapter mAdapter;

        public FullLoggViewHolder(View itemView, DailyLoggAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mAdapter = adapter;
        }
    }
}
