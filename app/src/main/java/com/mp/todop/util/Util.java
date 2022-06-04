package com.mp.todop.util;

import static com.mp.todop.model.Priority.*;

import com.mp.todop.model.Priority;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String formatDate(Date date){

        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getInstance();
        format.applyPattern("EEE , MMM d");
        return  format.format(date);
    }
    public static String priortyToString(Priority priority){
            if (priority == Priority.LOW) {
                return "Low";
            }else if(priority == Priority.HIGH) {
                return "Hidh";
            }else if (priority == Priority.MEDIUM) {
                return "Medium";
            }
            return "Priorty";
    }
}
