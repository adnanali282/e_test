package com.workouts.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.workouts.R;
import com.workouts.constant.AppConstant;
import com.workouts.model.LightModel;

import static com.workouts.constant.AppConstant.START_FILTER_ACTION;
import static com.workouts.constant.AppConstant.STOP_FILTER_ACTION;
import static com.workouts.constant.AppConstant.UPDATE_FILTER_ACTION;

public class ScreenFilterService extends Service {
    @SuppressLint("StaticFieldLeak")
    LinearLayout linearLayout;
    WindowManager.LayoutParams layoutParams;
    WindowManager windowManager;

    @Override
    public void onCreate() {
        super.onCreate();
        createScreenFilter();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            LightModel lightModel = intent.getParcelableExtra("object");
            switch (intent.getAction()) {
                case START_FILTER_ACTION:
                    activateScreenFilter(lightModel);
                    break;
                case UPDATE_FILTER_ACTION:
                    updateScreenFilter(lightModel);
                    break;
                case STOP_FILTER_ACTION:
                    removeScreenFilter();
                    break;

            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            windowManager.removeViewImmediate(linearLayout);
        } catch (Exception ignored) {
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createScreenFilter() {
        Log.d("", "createScreenFilter: ");
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        layoutParams = new WindowManager.LayoutParams();
        windowManager = (WindowManager) AppConstant.mContext.getSystemService(Context.WINDOW_SERVICE);
        layoutParams.type = LAYOUT_FLAG;
        //layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        layoutParams.width = windowManager.getDefaultDisplay().getHeight();
        layoutParams.height = windowManager.getDefaultDisplay().getHeight();
        layoutParams.format = PixelFormat.TRANSLUCENT;
        LayoutInflater layoutInflater = LayoutInflater.from(AppConstant.mContext);
        linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.screen_filter, null);

    }

    public void updateScreenFilter(LightModel darkerSettings) {

            updateThisSettings(darkerSettings);
            windowManager.updateViewLayout(linearLayout, layoutParams);

    }

    public void activateScreenFilter(LightModel darkerSettings) {
        updateThisSettings(darkerSettings);
        windowManager.addView(linearLayout, layoutParams);
    }

    private void updateThisSettings(LightModel darkerSettings) {
        layoutParams.screenBrightness = darkerSettings.getBrightness();

        layoutParams.alpha = darkerSettings.getAlpha();

        if (darkerSettings.isUseCustomColor() || darkerSettings.isUseDefaultColor())
            linearLayout.setBackgroundColor(darkerSettings.getColor());
        else
            linearLayout.setBackgroundColor(Color.BLACK);

        layoutParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  // this window won't ever get key input focus, so the user can not send key or other button events to it.
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE //this window can never receive touch events.
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL //even when this window is focusable (its FLAG_NOT_FOCUSABLE is not set), allow any pointer events outside of the window to be sent to the windows behind it
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN // place the window within the entire screen, ignoring decorations around the border (such as the status bar).
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN //hide all screen decorations (such as the status bar) while this window is displayed
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; //allow window to extend outside of the screen.
    }

    public void removeScreenFilter() {
        try {
            windowManager.removeViewImmediate(linearLayout);
        } catch (Exception ignored) {

        }
    }
}
