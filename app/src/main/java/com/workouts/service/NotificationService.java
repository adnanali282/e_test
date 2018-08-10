package com.workouts.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.workouts.R;
import com.workouts.activity.MainActivity;

import static com.workouts.constant.AppConstant.DESTROY_ACTION;
import static com.workouts.constant.AppConstant.FLASH_ACTION_OFF;
import static com.workouts.constant.AppConstant.FLASH_ACTION_ON;
import static com.workouts.constant.AppConstant.START_FOREGROUND_ACTION;

public class NotificationService extends Service {

    private final static String TAG = NotificationService.class.getSimpleName();
    private final static String CLICK_TO_DEACTIVATE = "Tap to disable";
    private final static String OPEN_PANEL = "Settings panel";
    private static final int FOREGROUND_SERVICE = 543;
    private static NotificationCompat.Builder builder;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    public void showNotification(Context context) {
        builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notify)
                .setAutoCancel(false);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("hello", true);
        notificationIntent.putExtra("counter", 1);
        PendingIntent settingsPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        builder.addAction(R.mipmap.ic_settings_white_24dp, OPEN_PANEL, settingsPendingIntent);
        builder.setContentTitle(getString(R.string.is_on))
                .setContentText(CLICK_TO_DEACTIVATE);
        builder.setContentIntent(settingsPendingIntent);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(FOREGROUND_SERVICE, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        if (intent != null) {
            switch (intent.getAction()) {
                case FLASH_ACTION_ON:
                    showNotification(this);
                    break;
                case FLASH_ACTION_OFF:
                    stopForeground(true);
                    // removeNotification();
                    break;
                case START_FOREGROUND_ACTION:
                    showNotification(this);
                    break;
                case DESTROY_ACTION:
                    // torchOffOnDestroy();
                    break;
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }


}
