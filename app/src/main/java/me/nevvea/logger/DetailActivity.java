package me.nevvea.logger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import me.nevvea.logger.bean.LoggTitle;
import me.nevvea.logger.fragment.DailyFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DailyFragment fragment = DailyFragment.newInstance();
        Bundle args = new Bundle();
        LoggTitle title = getIntent().getParcelableExtra(LoggTitle.TAG);
        assert title != null;
        setTitle(title.title);
        args.putParcelable(LoggTitle.TAG, title);

        if (savedInstanceState == null) {
            Logger.d("beginTransaction");
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.daily_logg_container, fragment)
                    .commit();

        }

    }
}
