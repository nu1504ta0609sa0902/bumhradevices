package com.mhra.mdcm.devices.appian.utils.selenium.others;

import java.util.Calendar;
import java.util.Random;

/**
 * @author TPD_Auto
 */
public class RandomDataUtils {

    /**
     *
     * @return
     */
    public static String getEUIdentifier(String euid) {
        if(euid==null || euid.trim().equals("")){
            return String.valueOf((int)getRandomDigits(5));
        }else{
            return euid;
        }
    }

    public static double getRandomDigits(int numberOfDigits){
        Random r = new Random( System.currentTimeMillis() );
        int thousands = (int) Math.pow(10,numberOfDigits-1);
        return thousands + r.nextInt(9 * thousands);
    }

    public static String getECID(String euIdentifier) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        year = year % 2000;
        String productId = getSimpleRandomNumberBetween(10000, 99999);
        return euIdentifier + "-" + year + "-" + productId;
    }

    public static String getDateInFutureMonths(int monthsInFuture) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, monthsInFuture);
        int dom = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);
        return dom + "/" + month + "/" + year;
    }

    public static String getDateInFutureMonthsUS(int monthsInFuture, boolean format) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, monthsInFuture);
        int dom = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);
        return year + "-" + format(month, format) + "-" + format(dom, format);
    }

    private static String format(int month, boolean format) {
        if(format && month < 10){
            return "0" + month;
        }else{
            return String.valueOf(month);
        }
    }

    public static String getRandomTestName(String test) {
        Calendar cal = Calendar.getInstance();
        return test + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + (cal.get(Calendar.MONTH)+1) + "_" + getRandomNumberBetween(100, 1000000);
    }

    public static String getRandomTestNameAdvanced(String test) {
        Calendar cal = Calendar.getInstance();
        return test + "_" + cal.get(Calendar.DAY_OF_MONTH) + "_" + (cal.get(Calendar.MONTH)+1) + "_" + getTimeMinHour(false);
    }

    public  static String getTimeMinHour(boolean includeDate) {
        Calendar instance = Calendar.getInstance();
        String date = (instance.get(Calendar.MONTH)+1) + "M_" + instance.get(Calendar.DAY_OF_MONTH);
        String time =  instance.get(Calendar.HOUR_OF_DAY) + "_" + instance.get(Calendar.MINUTE) + "_" + instance.get(Calendar.SECOND);
        String dateTime = "";
        if(includeDate){
            dateTime = dateTime + date + "D_";
        }
        dateTime = dateTime + time;
        return dateTime;
    }

    public static String getRandomNumberBetween(int min, int max) {
        Random random = new Random( System.currentTimeMillis() );
        int val = random.nextInt(max - min + 1) + min;
        val = new Random().nextInt(max) + min;
        return String.valueOf(val);
    }

    public static String getSimpleRandomNumberBetween(int min, int max) {
        int val = ( int )( Math.random() * max );
        if(val < min){
            val = val + min;
        }
        return String.valueOf(val);
    }

    public static String generateCASNumber() {
        String a = getRandomNumberBetween(11, 1000000);
        String b = getRandomNumberBetween(11, 99);
        String c = getRandomNumberBetween(1, 9);
        return a + "-" + b + "-" + c;
    }

    public static boolean getRandomBooleanValue() {
        String val = getRandomNumberBetween(1, 100);
        int v = Integer.parseInt(val);
        int rem = v % 2;
        return rem == 0;
    }

    public static String getRandomFloatNumberBetween(int from, int to) {
        String a = getRandomNumberBetween(from, to);
        double x = (Integer.parseInt(a) * Math.PI)/ 3;
        double td = ((int)(x * 100))/100.0;
        return String.valueOf(td);
    }

//    public static void main(String[] args){
//        for(int x = 0; x < 99; x++) {
//            String v = getSimpleRandomNumberBetween(10000, 99999);
//            System.out.println(v);
//        }
//    }

}
