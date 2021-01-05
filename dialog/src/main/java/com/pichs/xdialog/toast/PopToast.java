package com.pichs.xdialog.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.pichs.xdialog.R;
import com.pichs.xdialog.utils.DialogTimer;

/**
 * @Description: 提示会自动消失: 3s，可设置
 * @Author: WuBo
 * @CreateDate: 2020/10/15$ 11:59$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/10/15$ 11:59$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PopToast extends PopupWindow {

    private CharSequence title;
    private CharSequence message;
    // 自定义倒计时次数 单位 s
    private int cutDownTimes = 0;
    // 默认几秒关闭 单位 s
    private int defaultDismissDelay = 3;
    // 优先iconResId
    @DrawableRes
    private int iconResId;
    // 图片网址
    private String iconUrl;

    private TextView mTvTitle;
    private ImageView mIvIcon;
    private TextView mTvCutDown;
    private TextView mTvContent;
    private DialogTimer mTimerManager;
    // 颜色
    private int titleColor = 0;
    private int messageColor = 0;
    private int cutDownTextColor = 0;

    public PopToast(Context context) {
        this(context, null);
    }

    public PopToast(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopToast(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PopToast(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.toast_popup_layout, null);
        initView(rootView);
        setBackgroundDrawable(new ColorDrawable(0));
        setAnimationStyle(R.style.ScaleAnimation);
        setWidth(-1);
        setHeight(-2);
        setFocusable(false);
        setOutsideTouchable(false);
        setContentView(rootView);
    }

    public void initView(View rootView) {
        mTvTitle = rootView.findViewById(R.id.tv_title);
        mIvIcon = rootView.findViewById(R.id.iv_icon);
        mTvCutDown = rootView.findViewById(R.id.tv_cutdown);
        mTvContent = rootView.findViewById(R.id.tv_content);
        setTvCutDownVisible(false);
        mTvContent.setVisibility(View.GONE);
        mTvTitle.setVisibility(View.GONE);
        mIvIcon.setVisibility(View.GONE);
    }

    public PopToast setIconResId(int iconResId) {
        this.iconResId = iconResId;
        if (this.iconResId != 0) {
            if (mIvIcon != null) {
                mIvIcon.setVisibility(View.VISIBLE);
                mIvIcon.setImageResource(iconResId);
            }
        } else {
            if (mIvIcon != null) {
                mIvIcon.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public PopToast setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        if (TextUtils.isEmpty(iconUrl)) {
            mIvIcon.setVisibility(View.VISIBLE);
            if (mIvIcon != null) {
                Glide.with(mIvIcon)
                        .load(iconUrl)
                        .into(mIvIcon);
            }
        } else {
            if (mIvIcon != null) {
                mIvIcon.setVisibility(View.GONE);
            }
        }
        return this;
    }

    public PopToast setIconSize(int widthPx, int heightPx) {
        if (mIvIcon != null) {
            ViewGroup.LayoutParams layoutParams = mIvIcon.getLayoutParams();
            layoutParams.height = heightPx;
            layoutParams.width = widthPx;
            mIvIcon.setLayoutParams(layoutParams);
        }
        return this;
    }

    public PopToast setTitle(CharSequence title) {
        this.title = title;
        if (title != null) {
            if (mTvTitle != null) {
                mTvTitle.setVisibility(View.VISIBLE);
                mTvTitle.setText(title);
            }
        } else {
            if (mTvTitle != null) mTvTitle.setVisibility(View.GONE);
        }
        return this;
    }

    public PopToast setTitleTextSize(@ColorInt int textSizeSp) {
        if (mTvTitle != null) {
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        }
        return this;
    }

    public PopToast setTitleColor(@ColorInt int titleColor) {
        this.titleColor = titleColor;
        if (mTvTitle != null) {
            mTvTitle.setTextColor(this.titleColor);
        }
        return this;
    }

    public PopToast setMessage(CharSequence message) {
        this.message = message;
        if (message != null) {
            if (mTvContent != null) {
                mTvContent.setVisibility(View.VISIBLE);
                mTvContent.setText(message);
            }
        } else {
            if (mTvContent != null) mTvContent.setVisibility(View.GONE);
        }
        return this;
    }

    public PopToast setMessageColor(int messageColor) {
        this.messageColor = messageColor;
        if (mTvContent != null) mTvContent.setTextColor(messageColor);
        return this;
    }

    public PopToast setMessageTextSize(@ColorInt int textSizeSp) {
        if (mTvContent != null) {
            mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        }
        return this;
    }


    public PopToast setCutDownText(CharSequence cutDownText) {
        if (cutDownText != null) {
            if (mTvCutDown != null) {
                mTvCutDown.setText(cutDownText);
            }
        }
        return this;
    }

    public PopToast setCutDownTextSize(@ColorInt int textSizeSp) {
        if (mTvCutDown != null) {
            mTvCutDown.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        }
        return this;
    }

    public PopToast setTvCutDownVisible(boolean visible) {
        if (mTvCutDown != null) {
            mTvCutDown.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public PopToast setCutDownTextColor(int cutDownTextColor) {
        this.cutDownTextColor = cutDownTextColor;
        if (mTvCutDown != null) {
            mTvCutDown.setTextColor(cutDownTextColor);
        }
        return this;
    }

    public PopToast setCutDownTotalTimes(@IntRange(from = 1) int cutDownTimes) {
        this.cutDownTimes = cutDownTimes;
        if (cutDownTimes > 0) {
            // 初始化倒计时插件
            mTimerManager = new DialogTimer(mHandler, 1000, 1000, cutDownTimes);
        }
        return this;
    }

    public PopToast startTimer() {
        if (mTimerManager != null) {
            mTimerManager.startTimer();
        }
        return this;
    }

    public PopToast cancelTimer() {
        if (mTimerManager != null) {
            mTimerManager.cancelTimer();
        }
        return this;
    }

    public PopToast setDefaultDismissDelay(int defaultDismissDelay) {
        this.defaultDismissDelay = defaultDismissDelay;
        return this;
    }

    public void show(View anchor) {
        showAtLocation(anchor, Gravity.CENTER, 0, 0);
        if (cutDownTimes == 0) {
            mTimerManager = new DialogTimer(mHandler, 0, 1000, defaultDismissDelay);
        }
        startTimer();
        if (mOnCutDownListener != null) {
            mOnCutDownListener.onStart(this);
        }
    }

    @Override
    public void dismiss() {
        cancelTimer();
        super.dismiss();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case DialogTimer.TIMER_CANCELED:
                    if (mOnCutDownListener != null) {
                        mOnCutDownListener.onCancel(PopToast.this);
                    }
                    break;
                case DialogTimer.TIMER_FINISHED:
                    if (mOnCutDownListener != null) {
                        mOnCutDownListener.onCutDownFinish(PopToast.this);
                    }
                    if (cutDownTimes == 0) {
                        dismiss();
                    }
                    break;
                case DialogTimer.TIMER_PROGRESS:
                    if (mOnCutDownListener != null) {
                        int cutdownTime = cutDownTimes - msg.arg1;
                        mOnCutDownListener.onCutDown(PopToast.this, cutdownTime);
                    }
                    break;
            }
        }
    };

    private OnCutDownListener mOnCutDownListener;

    public PopToast setOnCutDownListener(OnCutDownListener onCutDownListener) {
        mOnCutDownListener = onCutDownListener;
        return this;
    }

    /**
     * 倒计时监听事件
     */
    public abstract static class OnCutDownListener {

        public void onStart(PopToast popup) {
        }

        public abstract void onCutDown(PopToast popup, int count);

        public void onCutDownFinish(PopToast popup) {
        }

        public void onCancel(PopToast popup) {
        }
    }
}
