package com.tehila.coupons.utils;

import com.tehila.coupons.thread.SendStatisticsThread;

public class StatisticsUtils {

    public static void sendStatistics(String username, String actionType) {
        SendStatisticsThread sendStatisticsThread = new SendStatisticsThread(username, actionType);
        sendStatisticsThread.start();
    }
}
