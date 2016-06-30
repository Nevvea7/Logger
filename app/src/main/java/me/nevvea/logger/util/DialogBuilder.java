package me.nevvea.logger.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import me.nevvea.logger.R;
import me.nevvea.logger.adapter.DailyLoggAdapter;
import me.nevvea.logger.bean.LoggItem;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptView)
                .setCancelable(true)
                .setPositiveButton("Log", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoggItem loggItem = new LoggItem(
                                datePicker.getDayOfMonth(),
                                datePicker.getMonth() + 1,
                                datePicker.getYear(),
                                logMsg.getText().toString());
                        Logger.d("month %d, time %d", loggItem.mMonth, loggItem.mTime);
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

}
