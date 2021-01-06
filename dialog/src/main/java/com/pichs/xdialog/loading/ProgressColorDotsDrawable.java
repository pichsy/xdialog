package com.pichs.xdialog.loading;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;

import androidx.core.content.ContextCompat;

import com.pichs.xdialog.R;

import java.util.Objects;

public class ProgressColorDotsDrawable extends AnimationDrawable {

    public ProgressColorDotsDrawable(Context context) {
        this(context, 80);
    }

    public ProgressColorDotsDrawable(Context context, int duration) {
        addFrames(context, duration);
    }

    private void addFrames(Context context, int duration) {
        try {
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_color_dot_loading_1)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_color_dot_loading_2)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_color_dot_loading_3)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_color_dot_loading_4)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_color_dot_loading_5)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_color_dot_loading_6)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_color_dot_loading_7)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_color_dot_loading_8)), duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
