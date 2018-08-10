package com.workouts.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

public class LightModel implements Parcelable {
    public static final String NIGHT = "night";
    public static final String CANDLE = "candle";
    public static final String DAWN = "dawn";
    public static final String BULB = "bulb";
    public static final String SAVER = "saver";
    private static final String BRIGHTNESS = "BRIGHTNESS";
    private static final String ALPHA = "ALPHA";
    private static final String COLOR_BAR_POSITION = "COLOR_BAR_POSITION";
    private static final String USE_CUSTOM_COLOR = "USE_CUSTOM_COLOR";
    private static final String USE_DEFAULT_COLOR = "USE_DEFAULT_COLOR";
    private static final String USE_DEFAULT_COLOR_TYPE = "USE_DEFAULT_COLOR_TYPE";
    private static final String USE_BRIGHTNESS = "USE_BRIGHTNESS";
    private static final String USE_NOTIFICATION = "USE_NOTIFICATION";
    private static final String USE_OVERLAY = "USE_OVERLAY";
    private static final float ALPHA_DEFAULT = 0.4F;
    private static final float BRIGHT_DEFAULT = 0.4F;
    private static final float COLOR_BAR_POSITION_DEFAULT = 0.0F;
    private static final boolean USE_STH_DEFAULT = false;
    @SuppressLint("StaticFieldLeak")
    private static Context context = null;
    private static SharedPreferences sharedPreferences_default = null;
    private static SharedPreferences sharedPreferences_current = null;
    private float brightness;
    private float alpha;
    private boolean useCustomColor;
    private boolean useDefaultColor;
    private String useDefaultColorType;
    private boolean useBrightness;
    private boolean useNotification;
    private boolean useOverlay;
    private float colorBarPosition;
    private int color;

    public LightModel() {
    }

    protected LightModel(Parcel in) {
        brightness = in.readFloat();
        alpha = in.readFloat();
        useCustomColor = in.readByte() != 0;
        useDefaultColor = in.readByte() != 0;
        useDefaultColorType = in.readString();
        useBrightness = in.readByte() != 0;
        useNotification = in.readByte() != 0;
        useOverlay = in.readByte() != 0;
        colorBarPosition = in.readFloat();
        color = in.readInt();
    }

    public static final Creator<LightModel> CREATOR = new Creator<LightModel>() {
        @Override
        public LightModel createFromParcel(Parcel in) {
            return new LightModel(in);
        }

        @Override
        public LightModel[] newArray(int size) {
            return new LightModel[size];
        }
    };

    public static void initializeContext(Context context) {
        LightModel.context = context;
        initializeSharedPreferences();
    }

    private static void initializeSharedPreferences() {
        sharedPreferences_default = LightModel.context.getSharedPreferences("user_settings_default", Context.MODE_PRIVATE);
        sharedPreferences_current = LightModel.context.getSharedPreferences("user_settings_current", Context.MODE_PRIVATE);
    }

    public static LightModel getDefaultSettings() {
        return getSettings(sharedPreferences_default);
    }

    public static LightModel getCurrentSettings() {
        return getSettings(sharedPreferences_current);
    }

    private static LightModel getSettings(SharedPreferences sharedPreferences) {
        LightModel thisDarkerSettings = new LightModel();

        float thisBrightness = sharedPreferences.getFloat(BRIGHTNESS, BRIGHT_DEFAULT);
        float thisAlpha = sharedPreferences.getFloat(ALPHA, ALPHA_DEFAULT);
        float thisColorBarPosition = sharedPreferences.getFloat(COLOR_BAR_POSITION, COLOR_BAR_POSITION_DEFAULT);
        boolean thisUseColor = sharedPreferences.getBoolean(USE_CUSTOM_COLOR, USE_STH_DEFAULT);
        boolean thisUseBrightness = sharedPreferences.getBoolean(USE_BRIGHTNESS, USE_STH_DEFAULT);
        String colorType = sharedPreferences.getString(USE_DEFAULT_COLOR_TYPE, DAWN);
        boolean thisUseNotification = sharedPreferences.getBoolean(USE_NOTIFICATION, true);
        boolean thisUseOverlay = sharedPreferences.getBoolean(USE_OVERLAY, false);

        thisDarkerSettings.setBrightness(thisBrightness);
        thisDarkerSettings.setAlpha(thisAlpha);
        thisDarkerSettings.setColorBarPosition(thisColorBarPosition);
        thisDarkerSettings.setUseCustomColor(thisUseColor);
        thisDarkerSettings.setUseDefaultColorType(colorType);
        thisDarkerSettings.setUseBrightness(thisUseBrightness);
        thisDarkerSettings.setUseNotification(thisUseNotification);
        thisDarkerSettings.setUseOverlay(thisUseOverlay);

        return thisDarkerSettings;
    }

    public String getUseDefaultColorType() {
        return useDefaultColorType;
    }

    public void setUseDefaultColorType(String useDefaultColorType) {
        this.useDefaultColorType = useDefaultColorType;
    }

    public boolean isUseOverlay() {
        return useOverlay;
    }

    public void setUseOverlay(boolean useOverly) {
        this.useOverlay = useOverly;
    }

    public boolean isUseDefaultColor() {
        return useDefaultColor;
    }

    public void setUseDefaultColor(boolean useDefaultColor) {
        this.useDefaultColor = useDefaultColor;
    }

    public boolean isUseNotification() {
        return useNotification;
    }

    public void setUseNotification(boolean useNotification) {
        this.useNotification = useNotification;
    }

    public float getColorBarPosition() {
        return colorBarPosition;
    }

    public void setColorBarPosition(float colorBarPosition) {
        this.colorBarPosition = colorBarPosition;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public boolean isUseCustomColor() {
        return useCustomColor;
    }

    public void setUseCustomColor(boolean useCustomColor) {
        this.useCustomColor = useCustomColor;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isUseBrightness() {
        return useBrightness;
    }

    public void setUseBrightness(boolean useBrightness) {
        this.useBrightness = useBrightness;
    }

    public void saveCurrentSettings() {
        SharedPreferences.Editor editor = sharedPreferences_current.edit();
        editor.putFloat(BRIGHTNESS, brightness);
        editor.putFloat(ALPHA, alpha);
        editor.putFloat(COLOR_BAR_POSITION, colorBarPosition);
        editor.putString(USE_DEFAULT_COLOR_TYPE, useDefaultColorType);
        editor.putBoolean(USE_BRIGHTNESS, useBrightness);
        editor.putBoolean(USE_CUSTOM_COLOR, useCustomColor);
        editor.putBoolean(USE_DEFAULT_COLOR, useDefaultColor);
        editor.putBoolean(USE_OVERLAY, useOverlay);
        editor.putBoolean(USE_NOTIFICATION, useNotification);
        editor.apply();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(brightness);
        dest.writeFloat(alpha);
        dest.writeByte((byte) (useCustomColor ? 1 : 0));
        dest.writeByte((byte) (useDefaultColor ? 1 : 0));
        dest.writeString(useDefaultColorType);
        dest.writeByte((byte) (useBrightness ? 1 : 0));
        dest.writeByte((byte) (useNotification ? 1 : 0));
        dest.writeByte((byte) (useOverlay ? 1 : 0));
        dest.writeFloat(colorBarPosition);
        dest.writeInt(color);
    }
}
