package com.mhra.mdcm.devices.appian.utils.selenium.others;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * @author TPD_Auto
 */
public class RandomDataUtils {

    public static double getRandomDigits(int numberOfDigits){
        Random r = new Random( System.currentTimeMillis() );
        int thousands = (int) Math.pow(10,numberOfDigits-1);
        return thousands + r.nextInt(9 * thousands);
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

    public static String generateTestNameStartingWith(String test, int lengthOfString) {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < lengthOfString) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            sb.append(SALTCHARS.charAt(index));
        }
        String x = sb.toString();
        return test + x;
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


    public static int getNumberBetween(int min, int max) {
        if(max >= 0) {
            Random random = new Random(System.currentTimeMillis());
            int val = random.nextInt(max - min + 1) + min;
            //val = new Random().nextInt(max) + min;
            return val;
        }else{
            return 0;
        }
    }

    public static String getSimpleRandomNumberBetween(int min, int max) {
        int val = ( int )( Math.random() * max );
        if(val < min){
            val = val + min;
        }
        return String.valueOf(val);
    }

    public static int getSimpleRandomNumberBetween(int min, int max, boolean even) {
        boolean found = false;
        int foundNumber = 0;
        do{
            String number = getSimpleRandomNumberBetween(min, max);
            int xx = Integer.parseInt(number);
            int m = xx % 2;
            if(even){
                if(m == 0){
                    found = true;
                    foundNumber = xx;
                }
            }else{
                if(m == 1){
                    found = true;
                    foundNumber = xx;
                }
            }
        }while (!found);

        return foundNumber;
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

    public static String getRandomEUCountryName() {
        List<String> list = new ArrayList<>();
        list.add("Austria");
        list.add("Belgium");
        list.add("Bulgaria");
        list.add("Croatia");
        list.add("Cyprus");
        list.add("Czech Republic");
        list.add("Denmark");
        list.add("Estonia");
        list.add("Finland");
        list.add("France");
        list.add("Germany");
        list.add("Greece");
        list.add("Hungary");
        list.add("Iceland");
        list.add("Ireland");
        list.add("Italy");
        list.add("Latvia");
        list.add("Lichtenstein");
        list.add("Lithuania");
        list.add("Luxembourg");
        list.add("Malta");
        list.add("Netherlands");
        list.add("Norway");
        list.add("Poland");
        list.add("Portugal");
        list.add("Romania");
        list.add("Slovakia");
        list.add("Slovenia");
        list.add("Spain");
        list.add("Sweden");
        list.add("Switzerland");
        list.add("Turkey");

        String n = getSimpleRandomNumberBetween(0, list.size()-1);
        String euCountry = list.get(Integer.parseInt(n));
        return euCountry;
    }
}
