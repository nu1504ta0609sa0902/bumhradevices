package com.mhra.mdcm.devices.appian.utils.network;

import org.slf4j.Logger;

import java.net.Socket;

/**
 * Created by TPD_Auto on 14/07/2016.
 */
public class NetworkUtils {

    static int networkConnectionDownCount = 0;

    public static String getTestUrl(String baseUrl) {

        String testUrl = "mhratest.appiancloud.com";
        if (baseUrl != null) {
            testUrl = baseUrl;
        }
        return testUrl;
    }

    public static boolean verifyConnectedToNetwork(String testUrl, int time) {
        Socket socket = null;
        try {
            socket = new Socket(testUrl, 80);
            socket.setSoTimeout(1000 * time);
            boolean connected = socket.isConnected();
            socket.close();
            return connected;
        } catch (Exception e) {
            return false;
        }
    }

    public static void shutdownIfRequired(boolean connected, String testUrl, Logger log) {

        if(networkConnectionDownCount > 5) {
            if (!connected) {
                try {
                    log.info("\n==========================================");
                    log.info("Not able to connect to : " + testUrl);
                    throw new RuntimeException("Network may be down");
                } catch (Exception e) {
                    log.info(e.getMessage());
                    log.info("\n==========================================\n");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    Runtime.getRuntime().exit(0);
                }
            }
        }else {
            log.info("\n==========================================");
            log.info("NO INTERNET CONNECTION");
            log.info("\n==========================================\n");
            networkConnectionDownCount++;
        }
    }
}
