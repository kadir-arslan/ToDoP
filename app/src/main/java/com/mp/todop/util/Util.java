package com.mp.todop.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String formatDate(Date date){

        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getInstance();
        format.applyPattern("EEE , MMM d");
        return  format.format(date);
    }
}
