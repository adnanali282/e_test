package com.workouts.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.workouts.R;
import com.workouts.activity.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.workouts.constant.AppConstant.mContext;


public class DailyNotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        showNotification(context);
    }

    private void showNotification(Context context) {
        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra("home_fragment", true);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_notify)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(mContext.getString(R.string.app_name))
                .setContentText(mContext.getString(R.string.notification_detail))
                .setAutoCancel(true)
                .setContentIntent(contentIntent);
        Notification notification = mBuilder.build();
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, notification); // remoteViews.setTextViewText(R.id.notify_tv, "It's time for Fat Burn Workout");
          }

}