package com.pichs.xdialog;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.pichs.common.widget.cardview.XCardLinearLayout;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.xdialog.base.BaseDialogFragment;
import com.pichs.xdialog.base.ViewHolder;
import com.pichs.xdialog.manager.IDialog;
import com.pichs.xdialog.manager.IDismissListener;


/**
 * @Description: 自定义弹窗
 * @Author: WuBo
 * @CreateDate: 2020/10/19$ 13:24$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/10/19$ 13:24$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CustomDialog extends BaseDialogFragment implements IDialog {

    private AppCompatActivity mActivity;
    private Builder builder;
    private TextView dialogTitle;
    private TextView dialogMessage;
    private TextView positiveButton;
    private TextView negativeButton;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_custom_tips_layout;
    }

    private CustomDialog(@NonNull final Builder builder) {
        this.builder = builder;
        mActivity = builder.activity;
        this.setOutCancel(builder.canOutSideTouchable)
                .setDimAmount(builder.dimAmount)
                .setMargin(builder.margin)
                .setHeight(builder.height)
                .setWidth(builder.width)
                .setOnDismissListener(builder.onDismissListener);
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialogFragment dialog) {
        dialogTitle = (TextView) holder.findViewById(R.id.tv_dialog_title);
        dialogMessage = (TextView) holder.findViewById(R.id.tv_dialog_message);
        positiveButton = (TextView) holder.findViewById(R.id.tv_positive_button);
        negativeButton = (TextView) holder.findViewById(R.id.tv_negative_button);
        View dividerVertical = holder.findViewById(R.id.dialog_divider_line_vertical);
        View dividerHorizontal = holder.findViewById(R.id.dialog_divider_line_horizontal);
        // 底部按钮父布局，控制ok，cancel高度
        View btnParent = holder.findViewById(R.id.ll_btn_parent);
        XCardLinearLayout parentLayout = (XCardLinearLayout) holder.findViewById(R.id.ll_parent_layout);
        parentLayout.setRadius(builder.radius);
        if (builder.backgroundColor != 0) {
            parentLayout.setBackgroundColor(builder.backgroundColor);
        }

        if (builder.dividerLineColor != 0) {
            dividerVertical.setBackgroundColor(builder.dividerLineColor);
            dividerHorizontal.setBackgroundColor(builder.dividerLineColor);
        }

        if (builder.buttonHeight >= 0) {
            ViewGroup.LayoutParams layoutParams = btnParent.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -2);
            }
            layoutParams.height = builder.buttonHeight;
        }

        dialogTitle.setGravity(builder.titleGravity);
        dialogMessage.setGravity(builder.messageGravity);

        if (!TextUtils.isEmpty(builder.title)) {
            dialogTitle.setVisibility(View.VISIBLE);
            dialogTitle.setText(builder.title);
            if (builder.titleTextColor != 0) {
                dialogTitle.setTextColor(builder.titleTextColor);
            }
            if (builder.titleTextSize != 0) {
                dialogTitle.setTextSize(builder.titleTextSize);
            }
        }
        if (!TextUtils.isEmpty(builder.message)) {
            dialogMessage.setVisibility(View.VISIBLE);
            dialogMessage.setText(builder.message);
            if (builder.messageTextColor != 0) {
                dialogMessage.setTextColor(builder.messageTextColor);
            }
            if (builder.messageTextSize != 0) {
                dialogMessage.setTextSize(builder.messageTextSize);
            }
        }

        if (builder.confirmTextColor != 0) {
            positiveButton.setTextColor(builder.confirmTextColor);
        }

        if (builder.cancelTextColor != 0) {
            negativeButton.setTextColor(builder.cancelTextColor);
        }

        negativeButton.setVisibility(View.GONE);
        positiveButton.setVisibility(View.GONE);
        dividerVertical.setVisibility(View.GONE);

        if (builder.confirmButtonText != null && builder.cancelButtonText != null) {
            dividerVertical.setVisibility(View.VISIBLE);
            positiveButton.setVisibility(View.VISIBLE);
            negativeButton.setVisibility(View.VISIBLE);
            positiveButton.setText(builder.confirmButtonText);
            if (builder.confirmTextSize != 0) {
                positiveButton.setTextSize(builder.confirmTextSize);
            }

            if (builder.onConfirmClickListener != null) {
                positiveButton.setOnClickListener(v -> builder.onConfirmClickListener.onClick(dialog, v));
            }
            negativeButton.setText(builder.cancelButtonText);

            if (builder.cancelTextSize != 0) {
                negativeButton.setTextSize(builder.cancelTextSize);
            }

            if (builder.onCancelClickListener != null) {
                negativeButton.setOnClickListener(v -> builder.onCancelClickListener.onClick(dialog, v));
            }
        } else if (builder.confirmButtonText != null) {
            positiveButton.setVisibility(View.VISIBLE);
            positiveButton.setText(builder.confirmButtonText);
            if (builder.confirmTextSize != 0) {
                positiveButton.setTextSize(builder.confirmTextSize);
            }
            if (builder.onConfirmClickListener != null) {
                positiveButton.setOnClickListener(v -> builder.onConfirmClickListener.onClick(dialog, v));
            }
        } else {
            if (builder.cancelButtonText == null) {
                builder.cancelButtonText = "Cancel";
            }
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setText(builder.cancelButtonText);
            if (builder.cancelTextSize != 0) {
                negativeButton.setTextSize(builder.cancelTextSize);
            }
            if (builder.onCancelClickListener != null) {
                negativeButton.setOnClickListener(v -> builder.onCancelClickListener.onClick(dialog, v));
            }
        }
    }

    public void setTitle(CharSequence title) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialogTitle != null) {
                    dialogTitle.setText(title);
                }
            }
        });
    }

    public void setMessage(CharSequence message) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialogMessage != null) {
                    dialogMessage.setText(message);
                }
            }
        });
    }

    public void setPositiveButtonText(CharSequence button) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (positiveButton != null) {
                    positiveButton.setText(button);
                }
            }
        });
    }

    public void setNegativeButtonText(CharSequence button) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (negativeButton != null) {
                    negativeButton.setText(button);
                }
            }
        });
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
    public void dismiss() {
        super.dismiss();
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
        // 必须使用具体的px，不能用WRAP_CONTENT, MATCH_PARENT.
        int buttonHeight = -1;
        CharSequence title;
        CharSequence message;
        CharSequence cancelButtonText;
        CharSequence confirmButtonText;

        // 弹窗优先级
        int priority;

        @ColorInt
        int titleTextColor;
        @ColorInt
        int messageTextColor;
        @ColorInt
        int cancelTextColor;
        @ColorInt
        int confirmTextColor;

        /**
         * 字体大小 单位 dp
         */
        int titleTextSize;
        int messageTextSize;
        int cancelTextSize;
        int confirmTextSize;

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
        int dividerLineColor;
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
        boolean canOutSideTouchable = true;
        DialogInterface.OnDismissListener onDismissListener;
        // 背景透明度 0-1
        float dimAmount = 0.5f;

        OnClickListener onCancelClickListener;
        OnClickListener onConfirmClickListener;

        public Builder(AppCompatActivity activity) {
            this.activity = activity;
            this.radius = XDisplayHelper.dp2px(activity, 10);
            this.margin = XDisplayHelper.dp2px(activity, 30);
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

        public Builder setMessageTextSize(int messageTextSize) {
            this.messageTextSize = messageTextSize;
            return this;
        }

        public Builder setCancelTextSize(int cancelTextSize) {
            this.cancelTextSize = cancelTextSize;
            return this;
        }

        public Builder setConfirmTextSize(int confirmTextSize) {
            this.confirmTextSize = confirmTextSize;
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

        public Builder setOnConfirmClickListener(OnClickListener onConfirmClickListener) {
            this.onConfirmClickListener = onConfirmClickListener;
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

        public Builder setConfirmButtonText(CharSequence confirmButtonText) {
            this.confirmButtonText = confirmButtonText;
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

        public Builder setConfirmTextColor(int confirmTextColor) {
            this.confirmTextColor = confirmTextColor;
            return this;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setButtonHeight(int buttonHeight) {
            this.buttonHeight = buttonHeight;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setDividerLineColor(int dividerLineColor) {
            this.dividerLineColor = dividerLineColor;
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

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public CustomDialog build() {
            return new CustomDialog(this);
        }
    }

    public interface OnClickListener {
        void onClick(BaseDialogFragment dialog, View view);
    }
}
