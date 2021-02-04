package com.pichs.xdialog.action;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.pichs.common.widget.cardview.XCardFrameLayout;
import com.pichs.common.widget.utils.XColorHelper;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.xdialog.R;


/**
 * @Description: 这是在dialog中显示的弹出层。
 * 如果在activity页面中请使用{@link PopActions}
 * @Author: WuBo
 * @CreateDate: 2020/10/9$ 13:12$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/10/9$ 13:12$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PopDialogActions {

    public final static int WINDOW_GRAVITY_CENTER = 0;
    public final static int WINDOW_GRAVITY_LEFT = 1;
    public final static int WINDOW_GRAVITY_RIGHT = 2;

    private PopupWindow mPopupWindow;
    private Context mContext;
    private View mRootView;

    private ImageView mIvArrowUp;
    private ImageView mIvArrowDown;
    private XCardFrameLayout mContainerView;
    // 添加进去的View
    private View mContentView;
    // 显示的⚓锚⚓
    private View mAnchor;

    private int mWidth = 0, mHeight = 0;
    // 内容的高度
    private int mContentHeight = 0;

    // 状态栏的高度
    private int mStatusBarHeight = 0;
    // 屏幕的宽，高
    private int mScreenWidth = 0, mScreenHeight = 0;

    // 箭头的宽高 20dp
    private int mArrowHeight = 0;
    private int mArrowWidth = 0;

    // 距离两边的边距 10dp
    private int mSideMargin = 0;

    private boolean mArrowVisible = true;
    //pop消失回调
    private OnPopupWindowDismissListener onPopupWindowDismissListener;

    // 用户设置的额外的offset
    private int xOffset;
    private int yOffset;

    // 背景
    private int backgroundRadius;
    private int backgroundColor;
    private int animationStyle;

    public static class Builder {
        private Context mContext;
        private int contentWidth;
        private int contentHeight;
        private int arrowHeight;
        private int arrowWidth;
        private int sideMargin;
        private int offsetX;
        private int offsetY;
        @ColorInt
        private int backgroundColor;
        private int radius;
        private OnPopupWindowDismissListener onPopupWindowDismissListener;
        private View contentView;
        private View anchor;
        private int animationStyle;

        public Builder(Context context) {
            mContext = context;
            radius = XDisplayHelper.dp2px(context, 8f);
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setAnchor(View anchor) {
            this.anchor = anchor;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public Builder setOnPopupWindowDismissListener(OnPopupWindowDismissListener onPopupWindowDismissListener) {
            this.onPopupWindowDismissListener = onPopupWindowDismissListener;
            return this;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setContentWidth(int contentWidth) {
            this.contentWidth = contentWidth;
            return this;
        }

        public Builder setContentHeight(int contentHeight) {
            this.contentHeight = contentHeight;
            return this;
        }

        public Builder setArrowHeight(int arrowHeight) {
            this.arrowHeight = arrowHeight;
            return this;
        }

        public Builder setArrowWidth(int arrowWidth) {
            this.arrowWidth = arrowWidth;
            return this;
        }

        public Builder setSideMargin(int sideMargin) {
            this.sideMargin = sideMargin;
            return this;
        }

        public Builder setOffsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public Builder setOffsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public PopDialogActions build() {
            return new PopDialogActions(this);
        }
    }

    private PopDialogActions(Builder builder) {
        this.mContext = builder.mContext;
        this.mWidth = builder.contentWidth;
        this.mContentHeight = builder.contentHeight;
        this.mArrowHeight = builder.arrowHeight;
        this.mArrowWidth = builder.arrowWidth;
        // 只要有一个为0 就不会显示箭头
        if (this.mArrowWidth <= 0 || mArrowHeight <= 0) {
            this.mArrowWidth = 0;
            this.mArrowWidth = 0;
            this.mArrowVisible = false;
        }
        this.mHeight = mContentHeight + mArrowHeight;
        this.mStatusBarHeight = XDisplayHelper.getStatusBarHeight(mContext);
        this.mScreenWidth = XDisplayHelper.getScreenWidth(mContext);
        this.mScreenHeight = XDisplayHelper.getScreenHeight(mContext);
        if (builder.sideMargin < 0) {
            this.mSideMargin = XDisplayHelper.dp2px(mContext, 8f);
        } else {
            this.mSideMargin = builder.sideMargin;
        }
        this.xOffset = builder.offsetX;
        this.yOffset = builder.offsetY;
        this.backgroundColor = builder.backgroundColor;
        this.backgroundRadius = builder.radius;
        this.mContentView = builder.contentView;
        this.mAnchor = builder.anchor;
        this.animationStyle = builder.animationStyle;
        this.onPopupWindowDismissListener = builder.onPopupWindowDismissListener;
        init();
    }

    private void init() {
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        if (animationStyle == 0) {
            animationStyle = R.style.ScaleAnimation;
        }
        mPopupWindow.setAnimationStyle(animationStyle);
        mRootView = View.inflate(mContext, R.layout.pop_action_layout, null);
        initView(mRootView);
        mPopupWindow.setContentView(mRootView);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
    }

    private void initView(View rootView) {
        if (mContentView == null) {
            return;
        }
        mContainerView = rootView.findViewById(R.id.fl_container);
        ViewGroup.LayoutParams layoutParams = mContainerView.getLayoutParams();
        layoutParams.height = mContentHeight;
        mContainerView.setLayoutParams(layoutParams);
        addView(mContentView);
        mIvArrowDown = rootView.findViewById(R.id.iv_arrow_down);
        mIvArrowUp = rootView.findViewById(R.id.iv_arrow_up);
        if (!this.mArrowVisible) {
            mIvArrowDown.setVisibility(View.GONE);
            mIvArrowUp.setVisibility(View.GONE);
        }
        // 设置箭头的大小
        if (mArrowWidth > 0 && mArrowHeight > 0) {
            ViewGroup.LayoutParams arrowDownLp = mIvArrowDown.getLayoutParams();
            if (arrowDownLp != null) {
                arrowDownLp.height = mArrowHeight;
                arrowDownLp.width = mArrowWidth;
                mIvArrowDown.setLayoutParams(arrowDownLp);
            }
            ViewGroup.LayoutParams arrowUpLp = mIvArrowUp.getLayoutParams();
            if (arrowUpLp != null) {
                arrowUpLp.height = mArrowHeight;
                arrowUpLp.width = mArrowWidth;
                mIvArrowUp.setLayoutParams(arrowUpLp);
            }
        }
        mPopupWindow.setWidth(mWidth == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : mWidth);
        mPopupWindow.setHeight(mHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mHeight);
        setBackgroundColor(backgroundColor == 0 ? ContextCompat.getColor(mContext, R.color.pop_action_background_color) : backgroundColor);
        if (mContainerView != null) {
            mContainerView.setRadius(backgroundRadius);
        }
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onPopupWindowDismissListener != null) {
                    onPopupWindowDismissListener.onPopDismiss();
                }
            }
        });
    }

    private PopDialogActions addView(View view) {
        if (view == null) {
            return this;
        }
        ViewParent parent = view.getParent();
        if (parent != null) {
            try {
                ((ViewGroup) parent).removeView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mContainerView != null) {
            mContainerView.removeAllViews();
            mContainerView.addView(view);
            mContainerView.invalidate();
        }
        return this;
    }

    private int getHeight() {
        if (mHeight == 0) {
            mHeight = mArrowHeight + mContainerView.getMeasuredHeight();
        }
        return mHeight;
    }


    /**
     * 如果有透明度就传带透明度的颜色值
     * eg: "#80000000"
     *
     * @param color int color颜色值
     * @return PopActions
     */
    public PopDialogActions setBackgroundColor(int color) {
        float alpha = (float) Color.alpha(color) / 255;
        int colorNoAlpha = XColorHelper.setColorAlpha(color, 1f);
        mContainerView.setBackgroundColor(color);
        mIvArrowUp.setColorFilter(colorNoAlpha);
        mIvArrowDown.setColorFilter(colorNoAlpha);
        mIvArrowUp.setAlpha(alpha);
        mIvArrowDown.setAlpha(alpha);
        return this;
    }


    /**
     * 在Dialog上显示时请使用此方法
     * 如果在activity页面中请使用{@link PopActions}
     *
     * @param anchor 锚
     */
    public void show(View anchor) {
        if (mPopupWindow != null) {
            AnchorInfo anchorInfo = new AnchorInfo(anchor);
            setArrowPosition(anchorInfo);
            showOrHideArrow(anchorInfo.isShowInBottom());
            if (anchorInfo.getWindowGravity() == WINDOW_GRAVITY_LEFT) {
                mPopupWindow.showAtLocation(anchor, Gravity.TOP | Gravity.START, anchorInfo.getDialogOffsetX(), anchorInfo.getDialogOffsetY());
            } else if (anchorInfo.getWindowGravity() == WINDOW_GRAVITY_RIGHT) {
                mPopupWindow.showAtLocation(anchor, Gravity.TOP | Gravity.END, anchorInfo.getDialogOffsetX(), anchorInfo.getDialogOffsetY());
            } else {
                mPopupWindow.showAtLocation(anchor, Gravity.CENTER_HORIZONTAL | Gravity.TOP, anchorInfo.getDialogOffsetX(), anchorInfo.getDialogOffsetY());
            }
        }
    }

    public void show() {
        if (mAnchor != null) {
            show(mAnchor);
        }
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private void setArrowPosition(AnchorInfo anchorInfo) {
        ViewGroup.MarginLayoutParams layoutParamsDown = (ViewGroup.MarginLayoutParams) mIvArrowDown.getLayoutParams();
        ViewGroup.MarginLayoutParams layoutParamsUp = (ViewGroup.MarginLayoutParams) mIvArrowUp.getLayoutParams();
        layoutParamsDown.setMarginStart(anchorInfo.getArrowMarinStart());
        layoutParamsUp.setMarginStart(anchorInfo.getArrowMarinStart());
    }

    private void showOrHideArrow(boolean isShowInBottom) {
        if (this.mArrowVisible) {
            if (isShowInBottom) {
                mIvArrowUp.setVisibility(View.VISIBLE);
                mIvArrowDown.setVisibility(View.GONE);
            } else {
                mIvArrowUp.setVisibility(View.GONE);
                mIvArrowDown.setVisibility(View.VISIBLE);
            }
        }
    }


    class AnchorInfo {
        // 中心坐标，x, y
        private int anchorCenterX;
        // 锚的宽高
        int anchorWidth, anchorHeight;
        /// 最终需要计算出来的数值
        // 是否显示在底部
        boolean isShowInBottom = false;
        // 弹窗的位置，0 center，1 left，2 right
        int windowGravity = WINDOW_GRAVITY_CENTER;
        // 在弹窗上展示时的便宜量，此偏移量为dialog左上角顶点为起始坐标，
        private int dialogOffsetY = 0;
        private int dialogOffsetX = 0;

        // 弹窗的Y坐标
        private int dialogY = 0;
        // 弹窗的x坐标
        private int dialogX = 0;
        // 箭头应向右便宜的距离
        private int dialogArrowMarginStart = 0;

        // anchor在自己窗口中的相对坐标
        int anchorWindowX = 0;
        int anchorWindowY = 0;

        int dialogWidth = 0;
        int dialogHeight = 0;

        public AnchorInfo(View anchor) {
            ///// 需要一开始就得到的数值
            // 获取中心坐标
            int[] anchorLocation = new int[2];
            anchor.getLocationOnScreen(anchorLocation);

            int[] dialogWindowLocation = new int[2];
            View anchorRootView = anchor.getRootView();
            anchorRootView.getLocationOnScreen(dialogWindowLocation);

            // 弹窗的宽，高获取
            dialogWidth = anchorRootView.getMeasuredWidth();
            dialogHeight = anchorRootView.getMeasuredHeight();

            dialogX = dialogWindowLocation[0];
            dialogY = dialogWindowLocation[1];

            int[] anchorWindowLocation = new int[2];
            anchor.getLocationInWindow(anchorWindowLocation);

            anchorWindowX = anchorWindowLocation[0];
            anchorWindowY = anchorWindowLocation[1];
            anchorWidth = anchor.getMeasuredWidth();
            anchorHeight = anchor.getMeasuredHeight();

            anchorCenterX = anchorWindowX + anchorWidth / 2;

            if (anchorWindowY >= getHeight()) {
                isShowInBottom = false;
            } else {
                isShowInBottom = true;
            }

            // 计算位置。
            if (anchorCenterX < dialogWidth / 2) {
                windowGravity = WINDOW_GRAVITY_LEFT;
            } else if (anchorCenterX > dialogWidth / 2) {
                windowGravity = WINDOW_GRAVITY_RIGHT;
            } else {
                windowGravity = WINDOW_GRAVITY_CENTER;
            }
            calculateDialogXY();
        }

        public int getDialogOffsetY() {
            return dialogOffsetY;
        }

        public int getDialogOffsetX() {
            return dialogOffsetX;
        }

        private void calculateDialogXY() {
            if (windowGravity == WINDOW_GRAVITY_LEFT) {
                // left
                // 1，如果中心点距离屏幕边缘 大于窗口一半+边距大小，则居中显示
                if (anchorCenterX - mSideMargin >= mWidth / 2) {
                    dialogOffsetX = anchorCenterX - mWidth / 2;
                    dialogArrowMarginStart = (mWidth - mArrowWidth) / 2;
                } else {
                    dialogOffsetX = mSideMargin;
                    dialogArrowMarginStart = anchorCenterX - mSideMargin - mArrowWidth / 2;
                }
            } else if (windowGravity == WINDOW_GRAVITY_RIGHT) {
                // right
                // 1，如果中心点距离屏幕边缘大于窗口一半+边距大小，则居中显示
                if (dialogWidth - anchorCenterX - mSideMargin >= mWidth / 2) {
                    dialogOffsetX = dialogWidth - anchorCenterX - mWidth / 2;
                    dialogArrowMarginStart = (mWidth - mArrowWidth) / 2;
                } else {
                    dialogOffsetX = mSideMargin;
                    dialogArrowMarginStart = mWidth - (dialogWidth - anchorCenterX - mSideMargin) - mArrowWidth / 2;
                }
            } else {
                // center
                dialogOffsetX = 0;
                dialogArrowMarginStart = (mWidth - mArrowWidth) / 2;
            }

            if (isShowInBottom) {
                dialogOffsetY = anchorWindowY + anchorHeight;
            } else {
                dialogOffsetY = anchorWindowY - getHeight();
            }

            // 加上用户的偏移量
            dialogOffsetX = dialogOffsetX + xOffset;
            dialogOffsetY = dialogOffsetY + yOffset;
        }

        public int getArrowMarinStart() {
            return dialogArrowMarginStart;
        }

        public int getAnchorHeight() {
            return anchorHeight;
        }

        public int getAnchorWidth() {
            return anchorWidth;
        }

        int getWindowGravity() {
            return windowGravity;
        }

        boolean isShowInBottom() {
            return isShowInBottom;
        }

        int getAnchorCenterX() {
            return anchorCenterX;
        }

    }

}
