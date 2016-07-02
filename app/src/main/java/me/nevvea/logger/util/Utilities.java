package me.nevvea.logger.util;

import android.net.Uri;

import java.util.Calendar;
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

    public static String getLongMonth(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "???";
        }
    }

    public static String getWeekday(int day) {
        switch (day) {
            case 1:
                return "SUN";
            case 2:
                return "MON";
            case 3:
                return "TUE";
            case 4:
                return "WED";
            case 5:
                return "THU";
            case 6:
                return "FRI";
            case 7:
                return "SAT";
            default:
                return "???";
        }
    }

    public static String getFormatedDate(Calendar calendar) {
        Calendar c = calendar == null ? Calendar.getInstance() : calendar;
        StringBuilder sb = new StringBuilder();
        sb
            .append(getLongMonth(c.get(Calendar.MONTH) + 1))
            .append(" ")
            .append(c.get(Calendar.DAY_OF_MONTH))
            .append(", ")
            .append(c.get(Calendar.YEAR));
        return sb.toString();
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

    public static String[] getMonthDayfromUri(Uri uri) {
        String[] res = new String[2];
        List<String> segs = uri.getPathSegments();
        if (segs == null || segs.size() < 5) return null;
        res[0] = segs.get(2);
        res[1] = segs.get(4);
        return res;
    }

    public static String[] getIdFromUri(Uri uri) {
        return new String[]{uri.getLastPathSegment()};
    }
}
