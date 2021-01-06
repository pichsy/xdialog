package com.pichs.xdialog.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.xdialog.R;


public abstract class BaseDialogFragment extends DialogFragment {
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String GRAVITY = "gravity";
    private static final String CANCEL = "out_cancel";
    private static final String THEME = "theme";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";

    // 单位 pixel  px
    private int margin;//左右边距
    private int width = ViewGroup.LayoutParams.MATCH_PARENT;//宽度 单位 pixel  px
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;//高度 单位 pixel  px
    private float dimAmount = 0.5f;//灰度深浅
    private int gravity = Gravity.CENTER;//显示的位置
    private boolean outCancel = true;//是否点击外部取消
    @StyleRes
    protected int theme = R.style.XBaseDialogStyle; // dialog主题
    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;

    public abstract int intLayoutId();

    public abstract void convertView(ViewHolder holder, BaseDialogFragment dialog);

    public int initTheme() {
        return theme;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, initTheme());

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            gravity = savedInstanceState.getInt(GRAVITY);
            outCancel = savedInstanceState.getBoolean(CANCEL);
            theme = savedInstanceState.getInt(THEME);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutId = intLayoutId();
        View view = inflater.inflate(layoutId, container, false);
        convertView(ViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putInt(GRAVITY, gravity);
        outState.putBoolean(CANCEL, outCancel);
        outState.putInt(THEME, theme);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount;
            if (gravity != 0) {
                lp.gravity = gravity;
            }
            switch (gravity) {
                case Gravity.LEFT:
                case Gravity.START:
                case (Gravity.LEFT | Gravity.BOTTOM):
                case (Gravity.START | Gravity.BOTTOM):
                case (Gravity.START | Gravity.TOP):
                case (Gravity.LEFT | Gravity.TOP):
                    if (animStyle == 0) {
                        animStyle = R.style.LeftAnimation;
                    }
                    break;
                case Gravity.TOP:
                    if (animStyle == 0) {
                        animStyle = R.style.TopAnimation;
                    }
                    break;
                case Gravity.RIGHT:
                case Gravity.END:
                case (Gravity.END | Gravity.BOTTOM):
                case (Gravity.RIGHT | Gravity.BOTTOM):
                case (Gravity.END | Gravity.TOP):
                case (Gravity.RIGHT | Gravity.TOP):
                    if (animStyle == 0) {
                        animStyle = R.style.RightAnimation;
                    }
                    break;
                case Gravity.BOTTOM:
                    if (animStyle == 0) {
                        animStyle = R.style.BottomAnimation;
                    }
                    break;
                default:
                    if (animStyle == 0) {
                        animStyle = R.style.ScaleAnimation;
                    }
                    break;

            }

            //设置dialog宽度
            if (width == ViewGroup.LayoutParams.MATCH_PARENT) {
                lp.width = XDisplayHelper.getScreenWidth(getContext()) - margin * 2;
            } else if (width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.width = width;
            }

            //设置dialog高度
            if (height > 0) {
                lp.height = height;
            }
            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle);
            window.setAttributes(lp);
        }
        setCancelable(outCancel);
    }

    public BaseDialogFragment setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseDialogFragment setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialogFragment setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseDialogFragment setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseDialogFragment setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public BaseDialogFragment setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public BaseDialogFragment setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public BaseDialogFragment show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        if (!this.isAdded() && !isRemoving() && !isVisible() && null == getTag()) {
            ft.add(this, String.valueOf(SystemClock.uptimeMillis()));
        }
        ft.show(this).commitAllowingStateLoss();
        return this;
    }

    private DialogInterface.OnDismissListener dismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }
}
