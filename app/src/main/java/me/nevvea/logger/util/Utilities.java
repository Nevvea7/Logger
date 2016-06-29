package me.nevvea.logger.util;

import android.net.Uri;

import java.util.List;

/**
 * Created by Anna on 6/27/16.
 */
public class Utilities {

    public static String getMonth(int month) {
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

    public static String[] getYearMonthDayFromUri(Uri uri) {
        String[] res = new String[3];
        List<String> segs = uri.getPathSegments();
        if (segs == null || segs.size() < 7) return null;
        res[0] = segs.get(2);
        res[1] = segs.get(4);
        res[2] = segs.get(6);
        return res;
    }
}
