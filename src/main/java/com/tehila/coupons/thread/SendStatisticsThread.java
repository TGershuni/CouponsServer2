package com.tehila.coupons.thread;

public class SendStatisticsThread extends Thread {

    private String username;

    private String actionType;

    public SendStatisticsThread(String username, String actionType) {
        this.username = username;
        this.actionType = actionType;
    }


    public void run() {
        System.out.println("User name is - " + this.username + ", type of action is - " + this.actionType);
    }

    public String getUsername() {
        return username;
    }

    public String getActionType() {
        return actionType;
    }

}
