package com.pichs.xdialog.loading;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;

import androidx.core.content.ContextCompat;

import com.pichs.xdialog.R;

import java.util.Objects;

public class ProgressRabbitDrawable extends AnimationDrawable {

    public ProgressRabbitDrawable(Context context) {
        this(context, 80);
    }

    public ProgressRabbitDrawable(Context context, int duration) {
        addFrames(context, duration);
    }

    private void addFrames(Context context, int duration) {
        try {
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_rabbit_01)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_rabbit_02)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_rabbit_03)), duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
