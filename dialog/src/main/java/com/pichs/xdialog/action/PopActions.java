package com.pichs.xdialog.action;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.core.content.ContextCompat;

import com.pichs.common.widget.cardview.XCardFrameLayout;
import com.pichs.common.widget.utils.XColorHelper;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.xdialog.R;


/**
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/10/9$ 13:12$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/10/9$ 13:12$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PopActions {

    public final static int WINDOW_GRAVITY_CENTER = 0;
    public final static int WINDOW_GRAVITY_LEFT = 1;
    public final static int WINDOW_GRAVITY_RIGHT = 2;

    private PopupWindow mPopupWindow;
    private Context mContext;
    private View mRootView;
    private ImageView mIvArrowUp;
    private ImageView mIvArrowDown;
    private XCardFrameLayout mContainerView;

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
    private boolean mArrowVisible = true;

    // 距离两边的边距 10dp
    private int mSideMargin = 0;

    // 背景透明度
    private boolean dimAmountEnable = false;

    //pop消失回调
    private OnPopupWindowDismissListener onPopupWindowDismissListener;

    public PopActions(Context context, int contentWidth, int contentHeight, int arrowWidth, int arrowHeight) {
        this.mContext = context;
        this.mWidth = contentWidth;
        this.mArrowHeight = arrowHeight;
        this.mArrowWidth = arrowWidth;
        // 只要有一个为0 就不会显示箭头
        if (this.mArrowWidth <= 0 || mArrowHeight <= 0) {
            this.mArrowWidth = 0;
            this.mArrowWidth = 0;
            this.mArrowVisible = false;
        }
        this.mSideMargin = XDisplayHelper.dp2px(context, 10);
        this.mContentHeight = contentHeight;
        this.mHeight = mContentHeight + mArrowHeight;
        this.mStatusBarHeight = XDisplayHelper.getStatusBarHeight(mContext);
        this.mScreenWidth = XDisplayHelper.getScreenWidth(mContext);
        this.mScreenHeight = XDisplayHelper.getScreenHeight(mContext);
        init();
    }

    public PopActions(Context context, int contentWidth, int contentHeight) {
        this.mContext = context;
        this.mWidth = contentWidth;
        this.mArrowHeight = context.getResources().getDimensionPixelSize(R.dimen.pop_action_menu_arrow_height);
        this.mArrowWidth = context.getResources().getDimensionPixelSize(R.dimen.pop_action_menu_arrow_width);
        this.mSideMargin = XDisplayHelper.dp2px(context, 10);
        this.mContentHeight = contentHeight;
        this.mHeight = mContentHeight + mArrowHeight;
        this.mStatusBarHeight = XDisplayHelper.getStatusBarHeight(mContext);
        this.mScreenWidth = XDisplayHelper.getScreenWidth(mContext);
        this.mScreenHeight = XDisplayHelper.getScreenHeight(mContext);
        init();
    }

    private void init() {
        mPopupWindow = new PopupWindow(mContext);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        mPopupWindow.setAnimationStyle(R.style.ScaleAnimation);
        mRootView = View.inflate(mContext, R.layout.pop_action_layout, null);
        initView(mRootView);
        mPopupWindow.setContentView(mRootView);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
    }

    private void initView(View rootView) {
        mContainerView = rootView.findViewById(R.id.fl_container);
        ViewGroup.LayoutParams layoutParams = mContainerView.getLayoutParams();
        layoutParams.height = mContentHeight;
        mContainerView.setLayoutParams(layoutParams);
        mIvArrowDown = rootView.findViewById(R.id.iv_arrow_down);
        mIvArrowUp = rootView.findViewById(R.id.iv_arrow_up);
        if (!this.mArrowVisible) {
            mIvArrowDown.setVisibility(View.GONE);
            mIvArrowUp.setVisibility(View.GONE);
        }
        mPopupWindow.setWidth(mWidth == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : mWidth);
        mPopupWindow.setHeight(mHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mHeight);
        setBackgroundColor(ContextCompat.getColor(mContext, R.color.pop_action_background_color));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dimAmountEnable) {
                    dimAmountBackground(mContext, 0f);
                }
                if (onPopupWindowDismissListener != null) {
                    onPopupWindowDismissListener.popDismiss();
                }
            }
        });
    }

    public PopActions addView(View view) {
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
     * 布局圆角 px
     *
     * @param radius 单位 px
     */
    public PopActions setRadius(int radius) {
        if (mContainerView != null) {
            mContainerView.setRadius(radius);
        }
        return this;
    }

    /**
     * 设置背景黑色透明度
     *
     * @param dimAmountEnable 是否进行透明度展示，true 会半透明，false 背景不变化
     */
    public PopActions setDimAmountEnable(boolean dimAmountEnable) {
        this.dimAmountEnable = this.dimAmountEnable;
        return this;
    }

    /**
     * 如果有透明度就传带透明度的颜色值
     * eg: "#80000000"
     *
     * @param color int color颜色值
     * @return PopActions
     */
    public PopActions setBackgroundColor(int color) {
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
     * 设置动画放
     *
     * @param style
     */
    public PopActions setAnimationStyle(int style) {
        if (mPopupWindow != null) {
            mPopupWindow.setAnimationStyle(style);
        }
        return this;
    }


    /**
     * @param anchor 锚
     */
    public void show(View anchor) {
        if (mPopupWindow != null) {
            AnchorInfo anchorInfo = new AnchorInfo(anchor);
            setArrowPosition(anchorInfo);
            showOrHideArrow(anchorInfo.isShowInBottom());
            if (anchorInfo.getWindowGravity() == WINDOW_GRAVITY_LEFT) {
                mPopupWindow.showAtLocation(anchor, Gravity.TOP | Gravity.START, anchorInfo.getOffsetX(), anchorInfo.getOffsetY());
            } else if (anchorInfo.getWindowGravity() == WINDOW_GRAVITY_RIGHT) {
                mPopupWindow.showAtLocation(anchor, Gravity.TOP | Gravity.END, anchorInfo.getOffsetX(), anchorInfo.getOffsetY());
            } else {
                mPopupWindow.showAtLocation(anchor, Gravity.CENTER_HORIZONTAL | Gravity.TOP, anchorInfo.getOffsetX(), anchorInfo.getOffsetY());
            }
            if (dimAmountEnable) {
                dimAmountBackground(mContext, 0.5f);
            }
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
        private int anchorCenterX, anchorY;
        // 锚的宽高
        int anchorWidth, anchorHeight;


        /// 最终需要计算出来的数值
        // 箭头应向右便宜的距离
        int arrowMarinStart = 0;
        // y轴便宜，x 偏移
        int offsetY = 0, offsetX = 0;
        // 是否显示在底部
        boolean isShowInBottom = false;

        // 弹窗的位置，0 center，1 left，2 right
        int windowGravity = WINDOW_GRAVITY_CENTER;


        public AnchorInfo(View anchor) {
            ///// 需要一开始就得到的数值
            // 获取中心坐标
            int[] anchorLocation = new int[2];
            anchor.getLocationOnScreen(anchorLocation);
            anchorWidth = anchor.getMeasuredWidth();
            anchorHeight = anchor.getMeasuredHeight();

            anchorCenterX = anchorLocation[0] + anchorWidth / 2;
            anchorY = anchorLocation[1];

            if (getAnchorY() <= mHeight + mStatusBarHeight) {
                isShowInBottom = true;
            } else {
                isShowInBottom = false;
            }

            // 计算位置。
            if (getAnchorCenterX() > mScreenWidth / 2) {
                windowGravity = WINDOW_GRAVITY_RIGHT;
            } else if (getAnchorCenterX() < mScreenWidth / 2) {
                windowGravity = WINDOW_GRAVITY_LEFT;
            } else {
                windowGravity = WINDOW_GRAVITY_CENTER;
            }

            calculateXY();
        }


        private void calculateXY() {
            if (windowGravity == WINDOW_GRAVITY_LEFT) {
                // left
                // 1，如果中心点距离屏幕边缘 大于窗口一半+边距大小，则居中显示
                if (anchorCenterX - mSideMargin >= mWidth / 2) {
                    offsetX = anchorCenterX - mWidth / 2;
                    arrowMarinStart = (mWidth - mArrowWidth) / 2;
                } else {
                    offsetX = mSideMargin;
                    arrowMarinStart = anchorCenterX - mSideMargin - mArrowWidth / 2;
                }
            } else if (windowGravity == WINDOW_GRAVITY_RIGHT) {
                // right
                // 1，如果中心点距离屏幕边缘大于窗口一半+边距大小，则居中显示
                if (mScreenWidth - anchorCenterX - mSideMargin >= mWidth / 2) {
                    offsetX = mScreenWidth - anchorCenterX - mWidth / 2;
                    arrowMarinStart = (mWidth - mArrowWidth) / 2;
                } else {
                    offsetX = mSideMargin;
                    arrowMarinStart = mWidth - (mScreenWidth - anchorCenterX - mSideMargin) - mArrowWidth / 2;
                }
            } else {
                // center
                offsetX = 0;
                arrowMarinStart = (mWidth - mArrowWidth) / 2;
            }

            if (isShowInBottom) {
                offsetY = anchorY + anchorHeight;
            } else {
                offsetY = anchorY - mHeight;
            }
        }

        public int getArrowMarinStart() {
            return arrowMarinStart;
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

        int getOffsetX() {
            return offsetX;
        }

        int getOffsetY() {
            return offsetY;
        }

        boolean isShowInBottom() {
            return isShowInBottom;
        }

        int getAnchorCenterX() {
            return anchorCenterX;
        }

        int getAnchorY() {
            return anchorY;
        }
    }

    /**
     * 设置背景半透明
     *
     * @param context   Context
     * @param dimAmount 透明度 0.5f 默认
     */
    private void dimAmountBackground(Context context, float dimAmount) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            Window window = activity.getWindow();
            if (window != null) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.dimAmount = dimAmount;
                lp.alpha = 1 - dimAmount;
                if (lp.alpha == 1f) {
                    //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                } else {
                    //此行代码主要是解决在华为手机上半透明效果无效的bug
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                }
                window.setAttributes(lp);
            }
        }
    }


    public void setOnPopupWindowDismissListener(OnPopupWindowDismissListener listener) {
        this.onPopupWindowDismissListener = listener;
    }

    public interface OnPopupWindowDismissListener {
        void popDismiss();
    }

}
