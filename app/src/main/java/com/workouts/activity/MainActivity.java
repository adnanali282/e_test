package com.workouts.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.workouts.R;
import com.workouts.model.LightModel;
import com.workouts.service.NotificationService;
import com.workouts.service.ScreenFilterService;
import com.workouts.utils.AdUtility;
import com.workouts.utils.ColorSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.workouts.constant.AppConstant.FLASH_ACTION_OFF;
import static com.workouts.constant.AppConstant.FLASH_ACTION_ON;
import static com.workouts.constant.AppConstant.START_FILTER_ACTION;
import static com.workouts.constant.AppConstant.START_FOREGROUND_ACTION;
import static com.workouts.constant.AppConstant.STOP_FILTER_ACTION;
import static com.workouts.constant.AppConstant.UPDATE_FILTER_ACTION;
import static com.workouts.model.LightModel.BULB;
import static com.workouts.model.LightModel.CANDLE;
import static com.workouts.model.LightModel.DAWN;
import static com.workouts.model.LightModel.NIGHT;
import static com.workouts.model.LightModel.SAVER;

public class MainActivity extends AppCompatActivity {
    // LightNotification lightNotification;
    public static String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=";
    LightModel lightModel = new LightModel();
    Intent intent;
    BroadcastReceiver broadcastReceiver;
    boolean isServiceRunning = false;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.iv_5)
    ImageView iv5;
    int adsCounter = 0;


    /*@BindView(R.id.cp_useColor_switch)
    Switch cpUseColorSwitch;*/

    @BindView(R.id.cp_colorSeekBar)
    ColorSeekBar cpColorSeekBar;

    @BindView(R.id.cp_alpha_indicator)
    TextView cpAlphaIndicator;

    @BindView(R.id.cp_alpha_circleSeekBar)
    SeekBar cpAlphaCircleSeekBar;


    @BindView(R.id.mute_notification_switch)
    Switch muteNotification;

    @BindView(R.id.cp_brightness_circleSeekBar)
    SeekBar cpBrightnessCircleSeekBar;

    @BindView(R.id.cp_brightness_indicator)
    TextView cpBrightnessIndicator;

    @BindView(R.id.cm_toggle_button)
    AppCompatButton cmToggleButton;
    @BindView(R.id.info_help)
    ImageView infoHelp;
    @BindView(R.id.light_frequency)
    LinearLayout lightFrequency;
    @BindView(R.id.daily_notify_detail)
    TextView dailyNotifyDetail;
    @BindView(R.id.cv_setting_notification_on_off)
    CardView cvSettingNotificationOnOff;
    private int notifyCounter = 0;
    private int alphaCounter = 0;
    //  private int colorCounter = 0;
    // private int colorSwitch = 0;

   /* @Override
    protected void onDestroy() {
        doCleanBeforeExit();
        super.onDestroy();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AdUtility.loadAdmobBannerActivity(this);
        AdUtility.loadInterstitialAdsExit(this);
        AdUtility.loadInterstitialAds(this);
        LightModel.initializeContext(getApplicationContext());
        checkPermissions();

   /*     Intent intent = getIntent();

        if (intent != null) {
            if (lightModel.isUseNotification()) {
                notifyCounter = 2;
            }

        }*/
/*        cpUseColorSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                  //  if (colorSwitch > 1) {
                        if (isChecked) {
                            AlphaAnimation alphaAnimation_0 = new AlphaAnimation(0, 1);
                            alphaAnimation_0.setDuration(300);
                            cpColorSeekBar.startAnimation(alphaAnimation_0);
                            cpColorSeekBar.setVisibility(View.VISIBLE);
                            lightModel.setUseCustomColor(true);
                        } else {
                            AlphaAnimation alphaAnimation_1 = new AlphaAnimation(1, 0);
                            alphaAnimation_1.setDuration(300);
                            cpColorSeekBar.startAnimation(alphaAnimation_1);
                            cpColorSeekBar.setVisibility(View.INVISIBLE);
                            lightModel.setUseCustomColor(false);
                        }
                        if (isServiceRunning)
                            collectCurrentDarkerSettings();
                   *//* } else {
                        colorSwitch++;
                    }*//*
                }
        );*/

        cpBrightnessCircleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cpBrightnessIndicator.setText(String.valueOf((int) progress));
                if (isServiceRunning)
                    collectCurrentDarkerSettings();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        cpAlphaCircleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cpAlphaIndicator.setText(String.valueOf((int) progress));
                if (alphaCounter > 1) {
                    if (isServiceRunning)
                        collectCurrentDarkerSettings();
                } else {
                    alphaCounter++;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        cpColorSeekBar.setOnColorChangeListener(
                (int i, int i1, int i2) -> {
                    // if (colorCounter > 1) {
                    if (isServiceRunning) {
                        lightModel.setUseCustomColor(true);
                        collectCurrentDarkerSettings();
                    }
                   /* } else {
                        colorCounter++;
                    }*/
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        lightModel = LightModel.getCurrentSettings();
        notifyCounter = lightModel.isUseNotification() ? 0 : 1;
        restoreLatestSettings();
        // colorSwitch=lightModel.isUseCustomColor()?0:2;
        isServiceRunning = lightModel.isUseOverlay();
        setButtonState(lightModel.isUseOverlay());

    }

    @Override
    protected void onPause() {
        super.onPause();
        alphaCounter = 0;
        // colorCounter = 0;

        // colorSwitch = 0;
    }

   /* @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!isServiceRunning) {
            collectCurrentDarkerSettings();
            setButtonState(true);
            isServiceRunning = true;
        } else {
            OverDrawManager.getOverDrawManager().removeScreenFilter();
            setButtonState(false);
            isServiceRunning = false;
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_restoreDefaultSettings) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Reset screen Filter")
                    .setMessage("Would you like to reset screen filter ?")
                    .setPositiveButton("Yes", (DialogInterface dialogInterface, int i) -> {
                                lightModel = LightModel.getDefaultSettings();
                                if (isServiceRunning) {
                                    doRestore();
                                    collectCurrentDarkerSettings();
                                } else
                                    doRestore();
                            }
                    )
                    .setNegativeButton("No", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setButtonState(boolean isChecked) {
        if (isChecked) {
            final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),
                    ContextCompat.getColor(this, R.color.toggle_button_on),
                    ContextCompat.getColor(this, R.color.toggle_button_off));
            valueAnimator.setDuration(500);
            valueAnimator.addUpdateListener((ValueAnimator animation) -> {
                        int trans_color = (int) valueAnimator.getAnimatedValue();
                        cmToggleButton.setSupportBackgroundTintList(
                                ColorStateList.valueOf(trans_color));
                    }
            );
            valueAnimator.start();
            cmToggleButton.setText(getResources().getString(R.string.disable));
            cmToggleButton.setTextColor(Color.WHITE);
        } else {
            final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), ContextCompat.getColor(this, R.color.toggle_button_off),
                    ContextCompat.getColor(this, R.color.toggle_button_on));
            valueAnimator.setDuration(500);
            valueAnimator.addUpdateListener((ValueAnimator animation) -> {
                        int trans_color = (int) valueAnimator.getAnimatedValue();
                        cmToggleButton.setSupportBackgroundTintList(ColorStateList.valueOf(trans_color));
                    }
            );
            valueAnimator.start();
            cmToggleButton.setText(getResources().getString(R.string.enable));
        }
    }

    @OnCheckedChanged(R.id.mute_notification_switch)
    public void onNotificationSwitch(CompoundButton buttonView, boolean isChecked) {

        if (notifyCounter > 0) {
            if (isChecked) {
                // lightModel.setUseNotification(true);
                // prepareForService(FLASH_ACTION_ON);
            } else {

                // StopForService(FLASH_ACTION_OFF);
            }
        } else {
            notifyCounter++;
            muteNotification.setChecked(lightModel.isUseNotification());

        }
    }

    private void doRestore() {

        boolean isCurrentServiceRunning = false;
        if (isServiceRunning) {
            isCurrentServiceRunning = true;
            isServiceRunning = false;
        }
        cpBrightnessCircleSeekBar.setProgress((int) lightModel.getBrightness() * 100);
        cpAlphaCircleSeekBar.setProgress((int) lightModel.getAlpha() * 100);
        if (isCurrentServiceRunning)
            isServiceRunning = true;

   /*     if (cpUseColorSwitch.isChecked()) {
            cpUseColorSwitch.setChecked(false);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            alphaAnimation.setDuration(300);
            cpColorSeekBar.startAnimation(alphaAnimation);
            cpColorSeekBar.setVisibility(View.INVISIBLE);
        }*/

        invalidateOptionsMenu();
    }

    public void startFilterService(String action, LightModel lightModel) {
        Intent intent = new Intent(this, ScreenFilterService.class);
        intent.putExtra("object", lightModel);
        intent.setAction(action);
        startService(intent);
    }

    public void stopFilterService(String action) {
        Intent intent = new Intent(this, ScreenFilterService.class);
        intent.setAction(action);
        stopService(intent);
    }

    private void collectCurrentDarkerSettings() {
        lightModel.setBrightness((float) cpBrightnessCircleSeekBar.getProgress() / 100); //--------->/100 Todo removed
        lightModel.setAlpha((float) cpAlphaCircleSeekBar.getProgress() / 100);
        // lightModel.setUseBrightness(cpUseBrightnessSwitch.isChecked());
        lightModel.setColorBarPosition(cpColorSeekBar.getColorPosition());
        //lightModel.setUseCustomColor(cpUseColorSwitch.isChecked());
        lightModel.setUseOverlay(isServiceRunning);
        if (lightModel.isUseCustomColor()) {
            lightModel.setColor(cpColorSeekBar.getColor());
            lightModel.setUseDefaultColor(false);
        } else {
            lightModel.setUseDefaultColor(true);
            if (lightModel.getUseDefaultColorType().equals(NIGHT))
                lightModel.setColor(Color.rgb(210, 197, 155));
            if (lightModel.getUseDefaultColorType().equals(CANDLE))
                lightModel.setColor(Color.rgb(228, 120, 115));
            if (lightModel.getUseDefaultColorType().equals(DAWN))
                lightModel.setColor(Color.rgb(237, 169, 13));
            if (lightModel.getUseDefaultColorType().equals(BULB))
                lightModel.setColor(Color.rgb(255, 185, 48));
            if (lightModel.getUseDefaultColorType().equals(SAVER))
                lightModel.setColor(Color.rgb(252, 244, 156));
        }

        lightModel.saveCurrentSettings();
        if (isServiceRunning)
            startFilterService(UPDATE_FILTER_ACTION, lightModel);
            //OverDrawManager.getOverDrawManager().updateScreenFilter(lightModel);
        else {
            try {
                startFilterService(START_FILTER_ACTION, lightModel);
                // OverDrawManager.getOverDrawManager().activateScreenFilter(lightModel);
            } catch (IllegalStateException ignored) {
            }
        }
        /*  Html code: #D2C59B
            RGB code: 210  197  155

        	HTML code:	#E47873
            RGB code:	R: 228 G: 120 B: 115
            HSV:	2.65° 49.56% 89.41%

            HTML code:	#E99570
            RGB code:	R: 233 G: 149 B: 112
            HSV:	18.35° 51.93% 91.37%

            HTML code:	#F1C56E
            RGB code:	R: 241 G: 197 B: 110
            HSV:	39.85° 54.36% 94.51%

            HTML code:	#FCF49C
            RGB code:	R: 252 G: 244 B: 156
            HSV:	55° 38.1% 98.82%

            Code #FFB2E2F9
            RGB; 178 226  249  sunlight

        * */
    }

    private void restoreLatestSettings() {
        lightModel = LightModel.getCurrentSettings();
        Log.d("brightness", "collectCurrentDarkerSettings: " + (int) (lightModel.getBrightness() * 100));
        Log.d("alpha", "collectCurrentDarkerSettings: " + (int) (lightModel.getAlpha() * 100));
        cpBrightnessCircleSeekBar.setProgress((int) (lightModel.getBrightness() * 100));
        cpAlphaCircleSeekBar.setProgress((int) (lightModel.getAlpha() * 100));  //---------> Todo removed *100
        cpBrightnessIndicator.post(new Runnable() {
            @Override
            public void run() {
                cpBrightnessIndicator.setText(String.valueOf(cpBrightnessCircleSeekBar.getProgress()));
                cpAlphaIndicator.setText(String.valueOf(cpAlphaCircleSeekBar.getProgress()));
            }
        });
        muteNotification.setChecked(lightModel.isUseNotification());

       /* if (lightModel.isUseCustomColor()) {
            cpUseColorSwitch.setChecked(true);
            cpColorSeekBar.setVisibility(View.VISIBLE);
        } else {
            cpUseColorSwitch.setChecked(false);
            cpColorSeekBar.setVisibility(View.INVISIBLE);
        }*/

        if (lightModel.isUseCustomColor())
            cpColorSeekBar.setColorBarValue((int) lightModel.getColorBarPosition());
    }

    @Override
    public void onBackPressed() {
        AdUtility.showInterstitialAdExit();
        if (isServiceRunning)
            finish();
        else
            showExitDialog(this);
    }

    public void showExitDialog(final Context context) {
        AlertDialog.Builder rateUsDialogBuilder = new AlertDialog.Builder(context);
        rateUsDialogBuilder.setCancelable(false);
        rateUsDialogBuilder.setTitle(context.getString(R.string.app_name));
        rateUsDialogBuilder.setMessage(getString(R.string.exit_dialog_title));
        rateUsDialogBuilder.setIcon(R.mipmap.ic_launcher);
        rateUsDialogBuilder.setPositiveButton(getString(R.string.text_yes),
                (DialogInterface dialog, int which) -> {
                    dialog.dismiss();
                    ((AppCompatActivity) context).finish();

                });
        rateUsDialogBuilder.setNeutralButton(getString(R.string.rate_us),
                (DialogInterface dialog, int which) -> {
                    String url = GOOGLE_PLAY_URL
                            + context.getPackageName();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);

                }).setNegativeButton(getString(R.string.text_no),
                (DialogInterface dialog, int which) -> {
                    dialog.dismiss();
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // prepareForService(START_FOREGROUND_ACTION);
                } else
                    finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0);
            } else if (isServiceRunning)
                prepareForService(START_FOREGROUND_ACTION);
        } else if (isServiceRunning)
            prepareForService(START_FOREGROUND_ACTION);
    }

    private void prepareForService(String action) {
        if (lightModel.isUseNotification()) {
            lightModel.saveCurrentSettings();
            Intent intent = new Intent(getApplicationContext(), NotificationService.class);
            intent.setAction(action);
            startService(intent);
        }
    }

    private void stopForService(String action) {
        lightModel.setUseNotification(false);
        lightModel.saveCurrentSettings();
        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        intent.setAction(action);
        startService(intent);
    }


    @OnClick({R.id.iv_1, R.id.iv_2, R.id.iv_3, R.id.iv_4, R.id.iv_5})
    public void onViewClicked(View view) {
        if (isServiceRunning) {
            switch (view.getId()) {
                case R.id.iv_1:
                    lightModel.setUseDefaultColorType(NIGHT);
                    lightModel.setColor(Color.rgb(210, 197, 155));
                    break;
                case R.id.iv_2:
                    lightModel.setUseDefaultColorType(CANDLE);
                    lightModel.setColor(Color.rgb(228, 120, 115));
                    break;
                case R.id.iv_3:
                    lightModel.setUseDefaultColorType(DAWN);
                    lightModel.setColor(Color.rgb(237, 169, 13));
                    break;
                case R.id.iv_4:
                    lightModel.setUseDefaultColorType(BULB);
                    lightModel.setColor(Color.rgb(255, 185, 48));
                    break;
                case R.id.iv_5:
                    lightModel.setUseDefaultColorType(SAVER);
                    lightModel.setColor(Color.rgb(252, 244, 156));
                    break;
            }
            lightModel.setUseCustomColor(false);
            collectCurrentDarkerSettings();
            adsCounter++;
            if (adsCounter % 2 == 0) AdUtility.showInterstitialAd();
        }

    }

    @OnClick(R.id.info_help)
    public void onInfoClicked() {
        Intent intent = new Intent(this, IntroActivity.class);
        intent.setAction("info");
        startActivity(intent);
    }

    @OnClick(R.id.cm_toggle_button)
    public void onToggleOverlay() {
        if (!isServiceRunning) {
            isServiceRunning = true;
            lightModel.setUseNotification(true);
            prepareForService(FLASH_ACTION_ON);
            startFilterService(START_FILTER_ACTION,lightModel);
            collectCurrentDarkerSettings();
            setButtonState(isServiceRunning);
        } else {
            lightModel.setUseNotification(false);
            stopForService(FLASH_ACTION_OFF);
            isServiceRunning = false;
            lightModel.setUseOverlay(false);
            lightModel.saveCurrentSettings();
            setButtonState(isServiceRunning);
            stopFilterService(STOP_FILTER_ACTION);
            // OverDrawManager.getOverDrawManager().removeScreenFilter();
            AdUtility.showInterstitialAd();
        }
    }

}
