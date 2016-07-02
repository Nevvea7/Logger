package me.nevvea.logger.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.nevvea.logger.R;
import me.nevvea.logger.adapter.actionhelpers.ItemActionHelper;
import me.nevvea.logger.app.LoggerApplication;
import me.nevvea.logger.bean.LoggItem;
import me.nevvea.logger.db.LoggDBInfo;
import me.nevvea.logger.db.datahelper.DailyLoggDataHelper;

/**
 * Created by Anna on 6/27/16.
 */
public class DailyLoggAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder>
    implements ItemActionHelper {

    public enum ItemType {
        YEAR,
        LOGG
    }

    private final LayoutInflater mLayoutInflater;
    private final DailyLoggChangeListener mListener;

    public DailyLoggAdapter(Context context, DailyLoggChangeListener listener) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        LoggItem loggItem = LoggItem.fromCursor(cursor);
        int adapterPosition = holder.getAdapterPosition();
        int topMargin = adapterPosition == 0 || holder instanceof YearLoggViewHolder ? 10 : -5;
        int bottomMargin = cursor.isLast() || getItemViewType(adapterPosition + 1) == ItemType.YEAR.ordinal() ? 10 : -5;
        if (holder instanceof FullLoggViewHolder) {
            ((FullLoggViewHolder) holder).mTVLogg.setText(loggItem.mMsg);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ((FullLoggViewHolder) holder).mCard.getLayoutParams();
            lp.topMargin = topMargin;
            lp.bottomMargin = bottomMargin;
            ((FullLoggViewHolder) holder).mCard.setLayoutParams(lp);
        } else if (holder instanceof YearLoggViewHolder){
            ((YearLoggViewHolder) holder).mTVLogg.setText(loggItem.mMsg);
            ((YearLoggViewHolder) holder).mTVYear.setText(Integer.toString(loggItem.mYear));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ((YearLoggViewHolder) holder).mCard.getLayoutParams();
            lp.topMargin = topMargin;
            lp.bottomMargin = bottomMargin;
            ((YearLoggViewHolder) holder).mCard.setLayoutParams(lp);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ItemType.YEAR.ordinal()) {
            return new YearLoggViewHolder(mLayoutInflater.inflate(R.layout.item_logg_daily_with_year, parent, false), this);
        }
        return new FullLoggViewHolder(mLayoutInflater.inflate(R.layout.item_logg_daily, parent, false), this);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ItemType.YEAR.ordinal();
        }
        Cursor c = getCursor();
        int pos = c.getPosition();
        c.moveToPosition(position);
        int curYear = c.getInt(c.getColumnIndex(LoggDBInfo.COLUMN_LOG_YEAR));
        c.moveToPosition(position - 1);
        int prevYear = c.getInt(c.getColumnIndex(LoggDBInfo.COLUMN_LOG_YEAR));
        c.moveToPosition(pos);
        if (curYear != prevYear) {
            return ItemType.YEAR.ordinal();
        }
        return ItemType.LOGG.ordinal();
    }

    @Override
    public void onItemDismiss(int position) {
        mListener.onLoggDeleted((LoggItem) getItem(position));
    }

    public static class FullLoggViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_daily_logg_textview)
        TextView mTVLogg;
        @BindView(R.id.item_daily_logg_cardview)
        CardView mCard;

        DailyLoggAdapter mAdapter;

        public FullLoggViewHolder(View itemView, DailyLoggAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mAdapter = adapter;
        }
    }

    public static class YearLoggViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_daily_year_logg_textview)
        TextView mTVLogg;
        @BindView(R.id.item_daily_year_textview)
        TextView mTVYear;
        @BindView(R.id.item_daily_logg_cardview)
        CardView mCard;

        DailyLoggAdapter mAdapter;

        public YearLoggViewHolder(View itemView, DailyLoggAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mAdapter = adapter;
        }
    }

    public interface DailyLoggChangeListener {
        void onLoggDeleted(LoggItem loggItem);
    }
}
