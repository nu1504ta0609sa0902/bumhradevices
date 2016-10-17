package com.mhra.mdcm.devices.appian.utils.selenium.page;

/**
 * Created by TPD_Auto on 15/07/2016.
 */
public class AssertUtils {

    public static String getExpectedName(String name){
        String val = "";
        switch (name){
            case "rdt1": val = "rdt 1"; break;
            case "ipu1": val = "ipu 1"; break;
            case "vrmm1": val = "vrmm 1"; break;
            case "fin1": val = "finance 1"; break;
            case "comm1": val = "comms 1"; break;
            case "super1": val = "super 1"; break;
            case "super2": val = "super 2"; break;
        }
        return val;
    }

//    public static boolean isBritishFormat(String orderDate) {
//        boolean isBritish = false;
//        System.out.println(orderDate);
//        if(orderDate!=null && !orderDate.equals("")){
//            String[] data = orderDate.split("/");
//            Calendar instance = Calendar.getInstance();
//            instance.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data[0]));
//            instance.set(Calendar.MONTH, Integer.parseInt(data[1])-1);
//            instance.set(Calendar.YEAR, Integer.parseInt(data[2]));
//
//            int style = DateFormat.DEFAULT;
//            //Also try with style = DateFormat.FULL and DateFormat.SHORT
//            Date date = instance.getTime();
//            DateFormat df = DateFormat.getDateInstance(style, Locale.UK);
//            df = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
//            System.out.println("United Kingdom: " + df.format(date));
//            df = DateFormat.getDateInstance(style, Locale.US);
//            System.out.println("USA: " + df.format(date));
//        }
//
//
//        return isBritish;
//    }
//
//    public static void main(String[] args){
//        boolean isBritish = AssertUtils.isBritishFormat("11/30/2016");
//        System.out.println(isBritish);
//    }
}

