package com.pichs.xdialog;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.cardview.XCardLinearLayout;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.xdialog.base.BaseDialogFragment;
import com.pichs.xdialog.base.BaseDialog;
import com.pichs.xdialog.base.ViewConvertListener;
import com.pichs.xdialog.base.ViewHolder;
import com.pichs.xdialog.manager.IDialog;
import com.pichs.xdialog.manager.IDismissListener;

/**
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/10/19$ 13:24$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/10/19$ 13:24$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class VerticalButtonDialog extends BaseDialogFragment implements IDialog {

    private AppCompatActivity mActivity;
    private Builder builder;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_vertical_button_layout;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialogFragment dialog) {
        TextView tvTitle = holder.findViewById(R.id.tv_dialog_title);
        TextView tvMessage = holder.findViewById(R.id.tv_dialog_message);

        // 按钮
        XCardButton btn1 = holder.findViewById(R.id.btn_dialog_o1);
        XCardButton btn2 = holder.findViewById(R.id.btn_dialog_o2);
        XCardButton btn3 = holder.findViewById(R.id.btn_dialog_o3);
        TextView tvCancel = holder.findViewById(R.id.tv_dialog_cancel);

        XCardLinearLayout parentLayout = (XCardLinearLayout) holder.findViewById(R.id.ll_parent_layout);
        parentLayout.setRadius(builder.radius);
        if (builder.backgroundColor != 0) {
            parentLayout.setBackgroundColor(builder.backgroundColor);
        }

        tvTitle.setGravity(builder.titleGravity);
        tvMessage.setGravity(builder.messageGravity);

        if (!TextUtils.isEmpty(builder.title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(builder.title);
            if (builder.titleTextColor != 0) {
                tvTitle.setTextColor(builder.titleTextColor);
            }
            if (builder.titleTextSize != 0) {
                tvTitle.setTextSize(builder.titleTextSize);
            }
            if (builder.titleTypeface != null) {
                tvTitle.setTypeface(builder.titleTypeface);
            }
        }

        if (!TextUtils.isEmpty(builder.message)) {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(builder.message);
            if (builder.messageTextColor != 0) {
                tvMessage.setTextColor(builder.messageTextColor);
            }
            if (builder.messageTextSize != 0) {
                tvMessage.setTextSize(builder.messageTextSize);
            }
        }

        // 按钮设置

        if (builder.buttonTextSize != 0) {
            btn1.setTextSize(builder.buttonTextSize);
            btn2.setTextSize(builder.buttonTextSize);
            btn3.setTextSize(builder.buttonTextSize);
        }

        if (builder.cancelTextColor != 0) {
            tvCancel.setTextColor(builder.cancelTextColor);
        }

        if (builder.cancelButtonText != null) {
            tvCancel.setText(builder.cancelButtonText);
            if (builder.cancelTextSize != 0) {
                tvCancel.setTextSize(builder.cancelTextSize);
            }
            tvCancel.setVisibility(View.VISIBLE);
            if (builder.onCancelClickListener != null) {
                tvCancel.setOnClickListener(v -> builder.onCancelClickListener.onClick(dialog, v));
            }
        } else {
            tvCancel.setVisibility(View.GONE);
        }

        if (builder.button1Text != null) {
            btn1.setVisibility(View.VISIBLE);
            btn1.setText(builder.button1Text);
            if (builder.button1TextColor != 0) {
                btn1.setTextColor(builder.button1TextColor);
            }
            if (builder.button1BackgroundStartColor != 0 && builder.button1BackgroundEndColor != 0) {
                btn1.setBackgroundGradient(builder.button1BackgroundStartColor, builder.button1BackgroundEndColor, GradientOrientation.HORIZONTAL);
            }

            if (builder.button1BorderColor != 0) {
                btn1.setBorderColor(builder.button1BorderColor);
            }
            if (builder.button1BorderWidth >= 0) {
                btn1.setBorderWidth(builder.button1BorderWidth);
            }
            if (builder.onButton1ClickListener != null) {
                btn1.setOnClickListener(v -> builder.onButton1ClickListener.onClick(dialog, v));
            }
        } else {
            btn1.setVisibility(View.GONE);
        }

        if (builder.button2Text != null) {
            btn2.setVisibility(View.VISIBLE);
            btn2.setText(builder.button2Text);
            if (builder.button2TextColor != 0) {
                btn2.setTextColor(builder.button2TextColor);
            }
            if (builder.button2BackgroundStartColor != 0 && builder.button2BackgroundEndColor != 0) {
                btn2.setBackgroundGradient(builder.button2BackgroundStartColor, builder.button2BackgroundEndColor, GradientOrientation.HORIZONTAL);
            }

            if (builder.button2BorderColor != 0) {
                btn2.setBorderColor(builder.button2BorderColor);
            }
            if (builder.button2BorderWidth >= 0) {
                btn2.setBorderWidth(builder.button2BorderWidth);
            }
            if (builder.onButton2ClickListener != null) {
                btn2.setOnClickListener(v -> builder.onButton2ClickListener.onClick(dialog, v));
            }
        } else {
            btn2.setVisibility(View.GONE);
        }

        if (builder.button3Text != null) {
            btn3.setVisibility(View.VISIBLE);
            btn3.setText(builder.button3Text);
            if (builder.button3TextColor != 0) {
                btn3.setTextColor(builder.button3TextColor);
            }
            if (builder.button3BackgroundStartColor != 0 && builder.button3BackgroundEndColor != 0) {
                btn3.setBackgroundGradient(builder.button3BackgroundStartColor, builder.button3BackgroundEndColor, GradientOrientation.HORIZONTAL);
            }

            if (builder.button3BorderColor != 0) {
                btn3.setBorderColor(builder.button3BorderColor);
            }
            if (builder.button3BorderWidth >= 0) {
                btn3.setBorderWidth(builder.button3BorderWidth);
            }
            if (builder.onButton3ClickListener != null) {
                btn3.setOnClickListener(v -> builder.onButton3ClickListener.onClick(dialog, v));
            }
        } else {
            btn3.setVisibility(View.GONE);
        }
    }

    private VerticalButtonDialog(@NonNull final Builder builder) {
        mActivity = builder.activity;
        this.builder = builder;
        this.setOutCancel(builder.canOutSideTouchable)
                .setDimAmount(builder.dimAmount)
                .setMargin(builder.margin)
                .setHeight(builder.height)
                .setWidth(builder.width)
                .setOnDismissListener(builder.onDismissListener);
    }

    @Override
    public void show() {
        if (!isAdded() && !isVisible()) {
            show(mActivity.getSupportFragmentManager());
        } else {
            FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            ft.show(this).commit();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(this);
        }
    }

    private IDismissListener dismissListener;

    @Override
    public void setIDismissListener(IDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    @Override
    public long getPriority() {
        if (builder == null) return 0;
        return builder.priority;
    }

    @Override
    public boolean isShowing() {
        return super.isAdded() && isVisible() && isResumed();
    }

    public static class Builder {
        private final AppCompatActivity activity;
        CharSequence title;
        Typeface titleTypeface = Typeface.DEFAULT_BOLD;
        CharSequence message;
        // 弹窗优先级, 配合弹窗创建的
        int priority;

        CharSequence button1Text;
        CharSequence button2Text;
        CharSequence button3Text;

        CharSequence cancelButtonText;

        @ColorInt
        int titleTextColor;
        @ColorInt
        int messageTextColor;
        @ColorInt
        int cancelTextColor;
        @ColorInt
        int button1TextColor;
        @ColorInt
        int button2TextColor;
        @ColorInt
        int button3TextColor;

        int button1BorderColor;
        int button1BorderWidth = 0;
        int button1BackgroundStartColor;
        int button1BackgroundEndColor;

        int button2BorderColor;
        int button2BorderWidth = -1;
        int button2BackgroundStartColor;
        int button2BackgroundEndColor;

        int button3BorderColor;
        int button3BorderWidth = -1;
        int button3BackgroundStartColor;
        int button3BackgroundEndColor;

        /**
         * 字体大小 单位 dp
         */
        int titleTextSize;
        int messageTextSize;
        int cancelTextSize;
        /**
         * 3个button字体大小一样
         */
        int buttonTextSize;

        /**
         * see {@link Gravity}
         */
        int titleGravity = Gravity.CENTER_HORIZONTAL;
        int messageGravity = Gravity.CENTER_HORIZONTAL;

        /**
         * 单位 px
         */
        int radius;
        @ColorInt
        int backgroundColor;

        /**
         * 单位 px
         */
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        /**
         * 单位 px
         */
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        /**
         * 单位 px
         */
        int margin;
        // 外部点击是否消失
        boolean canOutSideTouchable = false;
        DialogInterface.OnDismissListener onDismissListener;
        // 背景透明度 0-1
        float dimAmount = 0.5f;

        OnClickListener onCancelClickListener;
        OnClickListener onButton1ClickListener;
        OnClickListener onButton2ClickListener;
        OnClickListener onButton3ClickListener;

        public Builder(AppCompatActivity activity) {
            this.activity = activity;
            this.radius = XDisplayHelper.dp2px(activity, 10);
            this.margin = XDisplayHelper.dp2px(activity, 30);
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setMargin(int margin) {
            this.margin = margin;
            return this;
        }

        public Builder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder setTitleTextSize(int titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public Builder setTitleTypeface(Typeface titleTypeface) {
            this.titleTypeface = titleTypeface;
            return this;
        }

        public Builder setMessageTextSize(int messageTextSize) {
            this.messageTextSize = messageTextSize;
            return this;
        }

        public Builder setCancelTextSize(int cancelTextSize) {
            this.cancelTextSize = cancelTextSize;
            return this;
        }

        public Builder setTitleGravity(int titleGravity) {
            this.titleGravity = titleGravity;
            return this;
        }

        public Builder setMessageGravity(int messageGravity) {
            this.messageGravity = messageGravity;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnCancelClickListener(OnClickListener onCancelClickListener) {
            this.onCancelClickListener = onCancelClickListener;
            return this;
        }


        public Builder setCanOutSideTouchable(boolean canOutSideTouchable) {
            this.canOutSideTouchable = canOutSideTouchable;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public Builder setCancelButtonText(CharSequence cancelButtonText) {
            this.cancelButtonText = cancelButtonText;
            return this;
        }


        public Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder setMessageTextColor(int messageTextColor) {
            this.messageTextColor = messageTextColor;
            return this;
        }

        public Builder setCancelTextColor(int cancelTextColor) {
            this.cancelTextColor = cancelTextColor;
            return this;
        }


        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setButton1Text(CharSequence button1Text) {
            this.button1Text = button1Text;
            return this;
        }

        public Builder setButton2Text(CharSequence button2Text) {
            this.button2Text = button2Text;
            return this;
        }

        public Builder setButton3Text(CharSequence button3Text) {
            this.button3Text = button3Text;
            return this;
        }

        public Builder setButton1BorderColor(int button1BorderColor) {
            this.button1BorderColor = button1BorderColor;
            return this;
        }

        public Builder setButton1BorderWidth(int button1BorderWidth) {
            this.button1BorderWidth = button1BorderWidth;
            return this;
        }

        public Builder setButton2BackgroundStartColor(int button2BackgroundStartColor) {
            this.button2BackgroundStartColor = button2BackgroundStartColor;
            return this;
        }

        public Builder setButton2BackgroundEndColor(int button2BackgroundEndColor) {
            this.button2BackgroundEndColor = button2BackgroundEndColor;
            return this;
        }

        public Builder setButton3BackgroundStartColor(int button3BackgroundStartColor) {
            this.button3BackgroundStartColor = button3BackgroundStartColor;
            return this;
        }

        public Builder setButton3BackgroundEndColor(int button3BackgroundEndColor) {
            this.button3BackgroundEndColor = button3BackgroundEndColor;
            return this;
        }

        public Builder setButton1TextColor(int button1TextColor) {
            this.button1TextColor = button1TextColor;
            return this;
        }

        public Builder setButton2TextColor(int button2TextColor) {
            this.button2TextColor = button2TextColor;
            return this;
        }

        public Builder setButton3TextColor(int button3TextColor) {
            this.button3TextColor = button3TextColor;
            return this;
        }

        public Builder setButton1BackgroundStartColor(int button1BackgroundStartColor) {
            this.button1BackgroundStartColor = button1BackgroundStartColor;
            return this;
        }

        public Builder setButton1BackgroundEndColor(int button1BackgroundEndColor) {
            this.button1BackgroundEndColor = button1BackgroundEndColor;
            return this;
        }

        public Builder setButton2BorderColor(int button2BorderColor) {
            this.button2BorderColor = button2BorderColor;
            return this;
        }

        public Builder setButton2BorderWidth(int button2BorderWidth) {
            this.button2BorderWidth = button2BorderWidth;
            return this;
        }

        public Builder setButton3BorderColor(int button3BorderColor) {
            this.button3BorderColor = button3BorderColor;
            return this;
        }

        public Builder setButton3BorderWidth(int button3BorderWidth) {
            this.button3BorderWidth = button3BorderWidth;
            return this;
        }

        public Builder setButtonTextSize(int buttonTextSize) {
            this.buttonTextSize = buttonTextSize;
            return this;
        }

        public Builder setOnButton1ClickListener(OnClickListener onButton1ClickListener) {
            this.onButton1ClickListener = onButton1ClickListener;
            return this;
        }

        public Builder setOnButton2ClickListener(OnClickListener onButton2ClickListener) {
            this.onButton2ClickListener = onButton2ClickListener;
            return this;
        }

        public Builder setOnButton3ClickListener(OnClickListener onButton3ClickListener) {
            this.onButton3ClickListener = onButton3ClickListener;
            return this;
        }

        public VerticalButtonDialog build() {
            return new VerticalButtonDialog(this);
        }
    }

    public interface OnClickListener {
        void onClick(BaseDialogFragment dialog, View view);
    }
}
