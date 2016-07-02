package me.nevvea.logger;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.nevvea.logger.adapter.LoggTitleAdapter;
import me.nevvea.logger.bean.LoggTitle;
import me.nevvea.logger.db.datahelper.FPLoggDataHelper;
import me.nevvea.logger.fragment.DailyFragment;
import me.nevvea.logger.fragment.MainActivityFragment;
import me.nevvea.logger.util.DialogBuilder;

public class MainActivity extends AppCompatActivity
    implements LoggTitleAdapter.OnLoggTitleClickListener{

    Unbinder mUnbinder;
    private FPLoggDataHelper mFPLoggDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFPLoggDataHelper = new FPLoggDataHelper(this);

        MainActivityFragment fragment = MainActivityFragment.newInstance();
        fragment.setOnLoggTitleClickListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_main_content, fragment)
                .commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBuilder.buildSingleLoggDialog(MainActivity.this, mFPLoggDataHelper).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTitleClick(LoggTitle loggTitle) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(LoggTitle.TAG, loggTitle);
        startActivity(intent);
    }

}
