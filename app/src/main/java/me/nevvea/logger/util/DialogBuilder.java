package me.nevvea.logger.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.Calendar;

import me.nevvea.logger.R;
import me.nevvea.logger.adapter.DailyLoggAdapter;
import me.nevvea.logger.bean.LoggItem;
import me.nevvea.logger.bean.LoggTitle;
import me.nevvea.logger.db.datahelper.DailyLoggDataHelper;
import me.nevvea.logger.db.datahelper.FPLoggDataHelper;

/**
 * Created by Anna on 6/21/16.
 */
public class DialogBuilder {

    public static AlertDialog buildSingleLoggDialog(final Context context, final FPLoggDataHelper dataHelper) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_single_logg_main, null);
        final EditText logMsg = (EditText) promptView.findViewById(R.id.dialog_main_logg_edittext);
        final DatePicker datePicker = (DatePicker) promptView.findViewById(R.id.dialog_date_picker);
        final TextView dateTV = (TextView) promptView.findViewById(R.id.dialog_main_date);
        final TextView changeDateTV = (TextView) promptView.findViewById(R.id.dialog_change_date);
        final TextView setDateTV = (TextView) promptView.findViewById(R.id.dialog_set_date);
        final Calendar c = Calendar.getInstance();
        changeDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
                changeDateTV.setVisibility(View.GONE);
                setDateTV.setVisibility(View.VISIBLE);
            }
        });

        setDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.GONE);
                changeDateTV.setVisibility(View.VISIBLE);
                setDateTV.setVisibility(View.GONE);
                c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                dateTV.setText(Utilities.getFormatedDate(c));
            }
        });
        dateTV.setText(Utilities.getFormatedDate(null));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptView)
                .setCancelable(true)
                .setPositiveButton("Log", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoggItem loggItem = new LoggItem(
                                c.get(Calendar.DAY_OF_MONTH),
                                c.get(Calendar.MONTH) + 1,
                                c.get(Calendar.YEAR),
                                logMsg.getText().toString());
                        DailyLoggDataHelper dailyLoggDataHelper = new DailyLoggDataHelper(context);
                        dailyLoggDataHelper.insert(loggItem);
                        dataHelper.updateTitle(loggItem);
                    }
                })
                .setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public static AlertDialog buildChangeTitleDialog(final Context context, final LoggTitle loggTitle) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_change_title, null);
        final EditText changedTitle =
                (EditText) promptView.findViewById(R.id.dialog_change_title_edittext);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptView)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FPLoggDataHelper helper = new FPLoggDataHelper(context);
                        helper.update(changedTitle.getText().toString(), loggTitle);
                    }
                })
                .setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

}
