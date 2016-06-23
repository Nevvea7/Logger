package me.nevvea.logger;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import me.nevvea.logger.bean.LoggItem;
import me.nevvea.logger.db.LoggDataHelper;

/**
 * Created by Anna on 6/21/16.
 */
public class DialogBuilder {

    public static AlertDialog buildSingleLoggDialog(final Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_single_logg_main, null);
        final EditText logMsg = (EditText) promptView.findViewById(R.id.dialog_main_logg_edittext);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptView)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoggItem loggItem = new LoggItem(logMsg.getText().toString());
                        LoggDataHelper loggDataHelper = new LoggDataHelper(context);
                        loggDataHelper.insert(loggItem);
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
