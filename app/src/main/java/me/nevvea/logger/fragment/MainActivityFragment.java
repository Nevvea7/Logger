package me.nevvea.logger.fragment;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.nevvea.logger.R;
import me.nevvea.logger.adapter.LoggTitleAdapter;
import me.nevvea.logger.db.datahelper.FPLoggDataHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    Unbinder mUnbinder;
    @BindView(R.id.recycler_view_main_frag)
    RecyclerView mRecyclerView;
    private FPLoggDataHelper mFPLoggDataHelper;
    private LoggTitleAdapter mLoggTitleAdapter;
    private LoggTitleAdapter.OnLoggTitleClickListener mListener;

    public MainActivityFragment() {

    }

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    public void setOnLoggTitleClickListener(LoggTitleAdapter.OnLoggTitleClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFPLoggDataHelper = new FPLoggDataHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
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
        mLoggTitleAdapter = new LoggTitleAdapter(getActivity(), mListener);
        mRecyclerView.setAdapter(mLoggTitleAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Logger.d("onCreateLoader");
        return mFPLoggDataHelper.getCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() == 0) {

        } else {
            mLoggTitleAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mLoggTitleAdapter.changeCursor(null);
    }
}
