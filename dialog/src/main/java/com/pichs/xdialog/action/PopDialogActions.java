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
    //pop消失回调
    private PopDialogActions.popWindowDismissListener popWindowDismissListener;

    public PopDialogActions(Context context, int contentWidth, int contentHeight) {
        mContext = context;
        this.mWidth = contentWidth;
        this.mArrowHeight = context.getResources().getDimensionPixelSize(R.dimen.pop_action_menu_arrow_height);
        this.mArrowWidth = context.getResources().getDimensionPixelSize(R.dimen.pop_action_menu_arrow_width);
        this.mSideMargin = XDisplayHelper.dp2px( context,10);
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
        mPopupWindow.setWidth(mWidth == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : mWidth);
        mPopupWindow.setHeight(mHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mHeight);
        setBackgroundColor(ContextCompat.getColor(mContext, R.color.pop_action_background_color));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popWindowDismissListener != null) {
                    popWindowDismissListener.popDismiss();
                }
            }
        });
    }

    public PopDialogActions addView(View view) {
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
    public PopDialogActions setRadius(int radius) {
        if (mContainerView != null) {
            mContainerView.setRadius(radius);
        }
        return this;
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
     * 设置动画放
     *
     * @param style
     */
    public PopDialogActions setAnimationStyle(int style) {
        if (mPopupWindow != null) {
            mPopupWindow.setAnimationStyle(style);
        }
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
            showOrHideArrow(anchorInfo.isShowInBottom());
            setArrowPosition(anchorInfo);
            if (anchorInfo.getWindowGravity() == WINDOW_GRAVITY_LEFT) {
                mPopupWindow.showAtLocation(anchor, Gravity.TOP | Gravity.START, anchorInfo.getDialogOffsetX(), anchorInfo.getDialogOffsetY());
            } else if (anchorInfo.getWindowGravity() == WINDOW_GRAVITY_RIGHT) {
                mPopupWindow.showAtLocation(anchor, Gravity.TOP | Gravity.END, anchorInfo.getDialogOffsetX(), anchorInfo.getDialogOffsetY());
            } else {
                mPopupWindow.showAtLocation(anchor, Gravity.CENTER_HORIZONTAL | Gravity.TOP, anchorInfo.getDialogOffsetX(), anchorInfo.getDialogOffsetY());
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
        if (isShowInBottom) {
            mIvArrowUp.setVisibility(View.VISIBLE);
            mIvArrowDown.setVisibility(View.GONE);
        } else {
            mIvArrowUp.setVisibility(View.GONE);
            mIvArrowDown.setVisibility(View.VISIBLE);
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

    public void setPopWindowDismissListener(PopDialogActions.popWindowDismissListener popWindowDismissListener) {
        this.popWindowDismissListener = popWindowDismissListener;
    }

    public interface popWindowDismissListener {
        void popDismiss();
    }

}
