package weddingplanner.managers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date toDate(String dateStr) throws Exception{
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
    }

    public static Date toDate(long timestamp){
        return new Date(timestamp);
    }

    public static long toTimestamp(String dateStr) throws Exception{
        return toDate(dateStr).getTime();
    }

    //Date is converted to string
    public static String toString(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static String toString(long timestamp){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(timestamp));
    }
}
