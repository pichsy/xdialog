package com.pichs.xdialog.privacy;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.cardview.XCardImageView;
import com.pichs.common.widget.cardview.XCardLinearLayout;
import com.pichs.xdialog.R;
import com.pichs.xdialog.base.BaseDialogFragment;
import com.pichs.xdialog.base.ViewHolder;
import com.pichs.xdialog.manager.IDialog;
import com.pichs.xdialog.manager.IDismissListener;

/**
 * 隐私协议的弹窗。
 * |----------------|
 * |     title      |
 * |                |
 * |   message      |
 * |   xxxxxxx      |
 * |   xxxxxxx      |
 * |   xxxxxxx      |
 * |   xxxxxxx      |
 * |                |
 * |    Agree       |
 * |    exit        |
 * |----------------|
 */
public class
PrivacyDialog extends BaseDialogFragment implements IDialog {
    private final AppCompatActivity mActivity;
    private final PrivacyDialog.Builder builder;

    private PrivacyDialog(@NonNull final PrivacyDialog.Builder builder) {
        this.builder = builder;
        mActivity = builder.activity;
        this.setOutCancel(builder.canOutSideTouchable)
                .setDimAmount(builder.dimAmount)
                .setMargin(builder.margin)
                .setHeight(builder.height)
                .setWidth(builder.width)
                .setOnDismissListener(builder.onDismissListener);
        this.setCancelable(builder.isCancelable);
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_privacy_asso_layout;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialogFragment dialog) {
        if (builder == null) {
            return;
        }
        XCardLinearLayout root = holder.findViewById(R.id.ll_root);
        XCardImageView ivImage = holder.findViewById(R.id.iv_image);
        TextView tvTitle = holder.findViewById(R.id.tv_title);
        TextView tvMessage = holder.findViewById(R.id.tv_message);
        TextView tvPolicy = holder.findViewById(R.id.policy_read_policy);
        TextView tvAsso = holder.findViewById(R.id.policy_read_aso);
        XCardButton btnAgree = holder.findViewById(R.id.btn_agree);
        TextView tvRefuseExit = holder.findViewById(R.id.tv_refuse_exit);
        tvMessage.setClickable(true);
        if (builder.imageSrc != 0) {
            ivImage.setImageResource(builder.imageSrc);
            ivImage.setVisibility(View.VISIBLE);
        } else if (builder.imageDrawable != null) {
            ivImage.setImageDrawable(builder.imageDrawable);
            ivImage.setVisibility(View.VISIBLE);
        } else if (builder.imageBitmap != null) {
            ivImage.setVisibility(View.VISIBLE);
            ivImage.setImageBitmap(builder.imageBitmap);
        }
        if (builder.imageRadius != -1) {
            ivImage.setRadius(builder.imageRadius);
        }

        if (builder.imageWidth > 0 && builder.imageHeight > 0) {
            ViewGroup.LayoutParams layoutParams = ivImage.getLayoutParams();
            layoutParams.height = builder.imageHeight;
            layoutParams.width = builder.imageWidth;
            ivImage.setLayoutParams(layoutParams);
        }

        if (this.builder.bgRadius >= 0) {
            root.setRadius(this.builder.bgRadius);
        }

        if (this.builder.title != null) {
            tvTitle.setText(this.builder.title);
            if (this.builder.titleColor != 0) {
                tvTitle.setTextColor(this.builder.titleColor);
            }
            if (this.builder.titleSize > 0) {
                tvTitle.setTextSize(this.builder.titleSize);
            }
        }

        if (this.builder.message != null) {
            tvMessage.setText(this.builder.message);
            if (this.builder.messageColor != 0) {
                tvMessage.setTextColor(this.builder.messageColor);
            }
            if (this.builder.messageSize > 0) {
                tvMessage.setTextSize(this.builder.messageSize);
            }
        }
        if (this.builder.agreeText != null) {
            btnAgree.setText(this.builder.agreeText);
        }
        if (builder.agreeBtnRadius >= 0) {
            btnAgree.setRadius(builder.agreeBtnRadius);
        }

        if (this.builder.agreeSize > 0) {
            btnAgree.setTextSize(this.builder.agreeSize);
        }
        if (this.builder.agreeColor != 0) {
            btnAgree.setTextColor(this.builder.agreeColor);
        }
        if (this.builder.agreeBgColor != 0) {
            btnAgree.setBackgroundColor(this.builder.agreeBgColor);
        }

        if (this.builder.cancelText != null) {
            tvRefuseExit.setText(this.builder.cancelText);
        }

        if (this.builder.cancelColor != 0) {
            tvRefuseExit.setTextColor(this.builder.cancelColor);
        }

        if (this.builder.cancelSize > 0) {
            tvRefuseExit.setTextSize(this.builder.cancelSize);
        }

        if (this.builder.privacyText != null) {
            tvPolicy.setText(builder.privacyText);
        }

        if (this.builder.associationText != null) {
            tvAsso.setText(this.builder.associationText);
        }

        if (this.builder.privacyColor != 0) {
            tvPolicy.setTextColor(this.builder.privacyColor);
        }

        if (this.builder.associationColor != 0) {
            tvAsso.setTextColor(this.builder.associationColor);
        }

        tvPolicy.setOnClickListener(v -> {
            if (this.builder.privacyClickListener != null) {
                builder.privacyClickListener.onItemClick(this, v);
            }
        });

        tvAsso.setOnClickListener(v -> {
            if (builder.associationClickListener != null) {
                builder.associationClickListener.onItemClick(this, v);
            }
        });
        btnAgree.setOnClickListener(v -> {
            if (builder.agreeClickListener != null) {
                builder.agreeClickListener.onItemClick(this, v);
            }
        });
        tvRefuseExit.setOnClickListener(v -> {
            if (builder.cancelClickListener != null) {
                builder.cancelClickListener.onItemClick(this, v);
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
        // 弹窗优先级
        int priority;
        // 窗口圆角
        private int bgRadius = -1;
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
        private boolean isCancelable;

        private CharSequence title;

        private CharSequence message;

        private CharSequence agreeText;

        private CharSequence cancelText;

        private CharSequence associationText;

        private CharSequence privacyText;

        private int privacyColor;
        private int associationColor;


        private int titleColor;
        private int titleSize;

        private int messageColor;
        private int messageSize;

        private int agreeColor;
        private int agreeSize;
        private int agreeBgColor;
        private int agreeBtnRadius = -1;

        private int cancelColor;
        private int cancelSize;

        private int imageSrc;
        private Drawable imageDrawable;
        private Bitmap imageBitmap;
        private int imageRadius = -1;
        private int imageWidth;
        private int imageHeight;

        private OnItemClickListener agreeClickListener;

        private OnItemClickListener cancelClickListener;

        private OnItemClickListener privacyClickListener;

        private OnItemClickListener associationClickListener;

        public Builder(AppCompatActivity activity) {
            this.activity = activity;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setMargin(int margin) {
            this.margin = margin;
            return this;
        }

        public Builder setCanOutSideTouchable(boolean canOutSideTouchable) {
            this.canOutSideTouchable = canOutSideTouchable;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setImageBitmap(Bitmap imageBitmap) {
            this.imageBitmap = imageBitmap;
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

        public Builder setAgreeText(CharSequence agreeText) {
            this.agreeText = agreeText;
            return this;
        }

        public Builder setCancelText(CharSequence cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public Builder setAgreeClickListener(OnItemClickListener agreeClickListener) {
            this.agreeClickListener = agreeClickListener;
            return this;
        }

        public Builder setCancelClickListener(OnItemClickListener cancelClickListener) {
            this.cancelClickListener = cancelClickListener;
            return this;
        }

        public Builder setPrivacyClickListener(OnItemClickListener privacyClickListener) {
            this.privacyClickListener = privacyClickListener;
            return this;
        }

        public Builder setAssociationClickListener(OnItemClickListener associationClickListener) {
            this.associationClickListener = associationClickListener;
            return this;
        }

        public Builder setBgRadius(int bgRadius) {
            this.bgRadius = bgRadius;
            return this;
        }

        public Builder setAgreeBtnRadius(int agreeBtnRadius) {
            this.agreeBtnRadius = agreeBtnRadius;
            return this;
        }

        public Builder setAssociationText(CharSequence associationText) {
            this.associationText = associationText;
            return this;
        }

        public Builder setPrivacyText(CharSequence privacyText) {
            this.privacyText = privacyText;
            return this;
        }

        public Builder setPrivacyColor(int privacyColor) {
            this.privacyColor = privacyColor;
            return this;
        }

        public Builder setAssociationColor(int associationColor) {
            this.associationColor = associationColor;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setTitleSize(int titleSize) {
            this.titleSize = titleSize;
            return this;
        }

        public Builder setMessageColor(int messageColor) {
            this.messageColor = messageColor;
            return this;
        }

        public Builder setMessageSize(int messageSize) {
            this.messageSize = messageSize;
            return this;
        }

        public Builder setAgreeColor(int agreeColor) {
            this.agreeColor = agreeColor;
            return this;
        }

        public Builder setAgreeSize(int agreeSize) {
            this.agreeSize = agreeSize;
            return this;
        }

        public Builder setAgreeBgColor(int agreeBgColor) {
            this.agreeBgColor = agreeBgColor;
            return this;
        }

        public Builder setCancelColor(int cancelColor) {
            this.cancelColor = cancelColor;
            return this;
        }

        public Builder setCancelSize(int cancelSize) {
            this.cancelSize = cancelSize;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        public Builder setImageSrc(int imageSrc) {
            this.imageSrc = imageSrc;
            return this;
        }

        public Builder setImageDrawable(Drawable imageDrawable) {
            this.imageDrawable = imageDrawable;
            return this;
        }

        public Builder setImageRadius(int imageRadius) {
            this.imageRadius = imageRadius;
            return this;
        }

        public Builder setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public Builder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public PrivacyDialog build() {
            return new PrivacyDialog(this);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(PrivacyDialog dialog, View view);
    }
}
