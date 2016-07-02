package me.nevvea.logger.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.nevvea.logger.R;
import me.nevvea.logger.adapter.DailyLoggAdapter;
import me.nevvea.logger.adapter.LoggTitleAdapter;
import me.nevvea.logger.adapter.actionhelpers.ItemActionHelper;
import me.nevvea.logger.adapter.actionhelpers.ItemTouchHelperCallback;
import me.nevvea.logger.bean.LoggItem;
import me.nevvea.logger.bean.LoggTitle;
import me.nevvea.logger.db.datahelper.DailyLoggDataHelper;


public class DailyFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>,
        ItemActionHelper

{

    Unbinder mUnbinder;
    @BindView(R.id.recycler_view_daily_frag)
    RecyclerView mRecyclerView;

    DailyLoggDataHelper mDailyLoggDataHelper;
    DailyLoggAdapter mDailyLoggAdapter;
    LoggTitle mTitle;

    public DailyFragment() {

    }

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        mTitle = args.getParcelable(LoggTitle.TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDailyLoggDataHelper = new DailyLoggDataHelper(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_daily, container, false);
        mUnbinder = ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDailyLoggAdapter = new DailyLoggAdapter(getActivity());
        mRecyclerView.setAdapter(mDailyLoggAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(this));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mDailyLoggDataHelper.getCursorLoader(mTitle);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() == 0) {
            Logger.d("null cursor");
        } else {
            mDailyLoggAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDailyLoggAdapter.changeCursor(null);
    }

    @Override
    public void onItemDismiss(int position) {
        LoggItem loggItem = LoggItem.fromCursor((Cursor) mDailyLoggAdapter.getItem(position));
        mDailyLoggDataHelper.delete(loggItem);
    }
}
