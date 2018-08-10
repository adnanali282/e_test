/*
package com.amastigote.darker.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.amastigote.darker.R;
import MainActivity;
import AppConstant;

public class LightNotification {
    public final static String PRESS_BUTTON = "PRESS_BUTTON";
    private final static String IS_ON = "The filter is activated";
    private final static String IS_OFF = "The filter is disable";
    private final static String CLICK_TO_ACTIVATE = "Tap to enable";
    private final static String CLICK_TO_DEACTIVATE = "Tap to disable";
    private final static String OPEN_PANEL = "Settings panel";
    private static NotificationManager notificationManager;
    private static NotificationCompat.Builder builder;

    public LightNotification(Context context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notify)
                .setAutoCancel(false);
        //PendingIntent settingsPendingIntent = PendingIntent.getActivity(context,0, new Intent(context, MainActivity.class),0);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("hello", true);
        //  notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        PendingIntent settingsPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        builder.addAction(R.mipmap.ic_settings_white_24dp, OPEN_PANEL, settingsPendingIntent);
        */
/*PendingIntent changeStatusPendingIntent = PendingIntent.getBroadcast(context, 0,
                new Intent(context, NotificationReceiver.class),
                0);*//*

        builder.setContentIntent(settingsPendingIntent);
    }

    public void updateStatus(boolean isRunning) {
        if (isRunning) {
            builder.setContentTitle(IS_ON)
                    .setContentText(CLICK_TO_DEACTIVATE);
        } else {
            builder.setContentTitle(IS_OFF)
                    .setContentText(CLICK_TO_ACTIVATE);
        }
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notificationManager.notify(R.string.app_name, notification);
        //AppConstant.mContext.startForeground(FOREGROUND_SERVICE, notification);
    }

    public void removeNotification() {
        notificationManager.cancel(R.string.app_name);
    }
}
*/
