package com.pichs.xdialog.loading;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pichs.common.widget.roundview.CircularProgressDrawable;
import com.pichs.xdialog.R;


/**
 * 通用 加载进度条，仿淘宝样式,qq样式，各种样式
 */
public class ProgressCommonDialog extends Dialog {

    private ImageView mProgressIv;
    private TextView mProgressTv;
    private ProgressBar mProgressBar;
    private Context mContext;
    private CircularProgressDrawable circularDrawable;
    private AnimationDrawable animationDrawable;

    public ProgressCommonDialog(Context context) {
        this(context, R.style.XBaseDialogStyle_Transparent);
    }

    public ProgressCommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.common_dialog_progress, null);
        addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initView();
        normalStyle();
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    public ProgressCommonDialog setText(String text) {
        mProgressTv.setText(text);
        return this;
    }

    public ProgressCommonDialog setColorFilter(int color, PorterDuff.Mode mode) {
        mProgressIv.setColorFilter(color, mode);
        return this;
    }

    public ProgressCommonDialog setColorFilter(int color) {
        mProgressIv.setColorFilter(color);
        return this;
    }

    /**
     * 正常样式....
     *
     * @return ProgressCommonDialog
     */
    public ProgressCommonDialog normalStyle() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressIv.setVisibility(View.GONE);
        return this;
    }

    /**
     * Circular样式：Material Design 风格
     *
     * @return ProgressCommonDialog
     */
    public ProgressCommonDialog circularStyle() {
        mProgressBar.setVisibility(View.GONE);
        mProgressIv.setVisibility(View.VISIBLE);
        int pixelSizeStrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.5f, mContext.getResources().getDisplayMetrics());
        int pixelSizeRadius = mProgressIv.getWidth() - pixelSizeStrokeWidth;
        if (circularDrawable == null) {
            circularDrawable = new CircularProgressDrawable(mContext);
            circularDrawable.setColorSchemeColors(Color.parseColor("#ccC9C9C9"));
            circularDrawable.setStyle(CircularProgressDrawable.DEFAULT);
            circularDrawable.setStrokeWidth(pixelSizeStrokeWidth);
            circularDrawable.setCenterRadius(pixelSizeRadius);
        }
        mProgressIv.setImageDrawable(circularDrawable);
        circularDrawable.start();
        return this;
    }

    public ProgressCommonDialog commonStyle() {
        mProgressBar.setVisibility(View.GONE);
        mProgressIv.setVisibility(View.VISIBLE);
        animationDrawable = new ProgressCommonDrawable(mContext);
        mProgressIv.setImageDrawable(animationDrawable);
        animationDrawable.start();
        return this;
    }

    public ProgressCommonDialog rabbitStyle() {
        mProgressBar.setVisibility(View.GONE);
        mProgressIv.setVisibility(View.VISIBLE);
        animationDrawable = new ProgressRabbitDrawable(mContext);
        mProgressIv.setImageDrawable(animationDrawable);
        animationDrawable.start();
        return this;
    }

    public ProgressCommonDialog colorDotsStyle() {
        mProgressBar.setVisibility(View.GONE);
        mProgressIv.setVisibility(View.VISIBLE);
        animationDrawable = new ProgressColorDotsDrawable(mContext);
        mProgressIv.setImageDrawable(animationDrawable);
        animationDrawable.start();
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            View decorView = window.getDecorView();
            decorView.setPadding(0, 0, 0, 0);
        }
    }

    private void initView() {
        mProgressIv = findViewById(R.id.progress_iv);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressTv = findViewById(R.id.progress_tv);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
            if (circularDrawable != null) {
                circularDrawable.stop();
                circularDrawable = null;
            }
            if (animationDrawable != null) {
                animationDrawable.stop();
                animationDrawable = null;
            }
        }
    }
}
