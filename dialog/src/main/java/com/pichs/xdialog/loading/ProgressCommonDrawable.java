package com.pichs.xdialog.loading;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;

import androidx.core.content.ContextCompat;

import com.pichs.xdialog.R;

import java.util.Objects;

public class ProgressCommonDrawable extends AnimationDrawable {

    public ProgressCommonDrawable(Context context) {
        this(context, 100);
    }

    public ProgressCommonDrawable(Context context, int duration) {
        addFrames(context, duration);
    }

    private void addFrames(Context context, int duration) {
        try {
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_game_loading_1)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_game_loading_2)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_game_loading_3)), duration);
            addFrame(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.ic_game_loading_4)), duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
